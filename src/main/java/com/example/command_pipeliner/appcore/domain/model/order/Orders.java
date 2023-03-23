package com.example.command_pipeliner.appcore.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.BaseAggregateRoot;
import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class Orders extends BaseAggregateRoot {

    Long id;

    List<OrderItems> items;

    Long userId;

    PrecisionMoney discount;

    PrecisionMoney totalAmount;

    PrecisionMoney paidAmount;

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(PrecisionMoney discount, PrecisionMoney paidAmount, List<OrderItems> orderItems) {
        CreateOrderEvent  event = new CreateOrderEvent(discount, paidAmount, orderItems);
        handle(event);
    }

    public PrecisionMoney calculateRemainingAmount() {
        return totalAmount.subtract(discount).subtract(paidAmount);
    }

    public void addOrderItem(Long productId, Integer count, PrecisionMoney amount) {
        AddOrderItemEvent event = new AddOrderItemEvent(productId, count, amount);
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
                discount = e.getDiscount();
                paidAmount = e.getPaidAmount();
//                e.getOrderItems().forEach();
                break;
            case AddOrderItemEvent e:
                OrderItems item = new OrderItems(e.getProductId(), e.getCount(), e.getAmount());
                items.add(item);
                item.setOrder(this);
                totalAmount = totalAmount.add(item.getAmount());
                break;
            default:
                throw new RuntimeException("Event not supported: " + event);
        }
    }



}
