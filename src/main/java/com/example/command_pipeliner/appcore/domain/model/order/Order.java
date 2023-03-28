package com.example.command_pipeliner.appcore.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.ApplyEvent;
import com.example.command_pipeliner.appcore.domain.model.common.BaseAggregateRoot;
import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Order extends BaseAggregateRoot {

    Long id;

    List<OrderItem> orderItems = new ArrayList<>();

    Long userId;

    PrecisionMoney discount = PrecisionMoney.ZERO;

    PrecisionMoney totalAmount = PrecisionMoney.ZERO;

    PrecisionMoney paidAmount = PrecisionMoney.ZERO;

    public Order(Long id, List<DomainEvent> historyEvents) {
        this.id = id;
        historyEvents.forEach(this::addEvent);
        init();
    }

    public Order(CreateOrderEvent event) {
        handle(event);
    }

    public PrecisionMoney calculateRemainingAmount() {
        return totalAmount.subtract(discount).subtract(paidAmount);
    }

    public void newOrderItem(Long productId, Integer count, PrecisionMoney amount) {
        AddOrderItemEvent event = new AddOrderItemEvent(productId, count, amount);
        handle(event);
    }

    public void updateDiscount(UpdateOrderDiscountEvent event) {
        handle(event);
    }
    @Override
    protected void validate() {
        if (calculateRemainingAmount().compareTo(PrecisionMoney.ZERO) < 0) {
            throw new RuntimeException("remaining amount is less than Zero");
        }
    }

    @Override
    protected SnapshotOrderEvent generateSnapshotEvent() {
        SnapshotOrderEvent snapshotEvent = new SnapshotOrderEvent();
        snapshotEvent.setDiscount(this.getDiscount());
        snapshotEvent.setPaidAmount(this.getPaidAmount());
        snapshotEvent.setUserId(this.getUserId());
        List<CreateOrderEvent.OrderItem> orderItems = new ArrayList<>();
        for (OrderItem item: this.getOrderItems()) {
            CreateOrderEvent.OrderItem newItem = new CreateOrderEvent.OrderItem();
            newItem.setCount(item.getCount());
            newItem.setAmount(item.getAmount());
            newItem.setProductId(item.getProductId());
            orderItems.add(newItem);
        }
        snapshotEvent.setOrderItems(orderItems);
        return snapshotEvent;
    }

    @ApplyEvent(eventType = CreateOrderEvent.class)
    private void applyCreateOrderEvent(CreateOrderEvent event) {
        userId = event.getUserId();
        discount = event.getDiscount();
        paidAmount = event.getPaidAmount();
        for (CreateOrderEvent.OrderItem i: event.getOrderItems()) {
            OrderItem item = new OrderItem(i.getProductId(), i.getCount(), i.getAmount());
            addOrderItem(item);
        }
    }

    @ApplyEvent(eventType = AddOrderItemEvent.class)
    private void applyAddOrderItemEvent(AddOrderItemEvent event) {
        OrderItem item = new OrderItem(event.getProductId(), event.getCount(), event.getAmount());
        addOrderItem(item);
    }

    @ApplyEvent(eventType = UpdateOrderDiscountEvent.class)
    private void applyUpdateOrderDiscountEvent(UpdateOrderDiscountEvent event) {
        discount = event.getDiscount();
    }

    @ApplyEvent(eventType = SnapshotOrderEvent.class)
    private void applySnapshotOrderEvent(SnapshotOrderEvent event) {
        applyCreateOrderEvent(event);
    }

    private void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        totalAmount = totalAmount.add(orderItem.getAmount());
    }



}
