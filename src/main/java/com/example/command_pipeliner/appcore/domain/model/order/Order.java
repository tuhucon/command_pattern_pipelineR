package com.example.command_pipeliner.appcore.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.BaseAggregateRoot;
import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Order extends BaseAggregateRoot {

    @JsonIgnore
    Long id;

    List<OrderItem> items = new ArrayList<>();

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
    protected void apply(DomainEvent event) {
        switch (event) {
            case CreateOrderEvent e:
                userId = e.getUserId();
                discount = e.getDiscount();
                paidAmount = e.getPaidAmount();
                for (CreateOrderEvent.OrderItem i: e.getOrderItems()) {
                    OrderItem item = new OrderItem(i.getProductId(), i.getCount(), i.getAmount());
                    addOrderItem(item);
                }
                break;
            case AddOrderItemEvent e:
                OrderItem item = new OrderItem(e.getProductId(), e.getCount(), e.getAmount());
                addOrderItem(item);
                break;
            case UpdateOrderDiscountEvent e:
                discount = e.getDiscount();
                break;
            default:
                throw new RuntimeException("Event not supported: " + event);
        }
    }

    private void addOrderItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);
        totalAmount = totalAmount.add(orderItem.getAmount());
    }



}
