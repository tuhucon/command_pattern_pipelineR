package com.example.command_pipeliner.appcore.appservice.order;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import com.example.command_pipeliner.appcore.domain.model.order.CreateOrderEvent;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.domain.model.order.OrderRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements Command.Handler<CreateOrderCommand, Order> {

    private final OrderRepostiory orderRepostiory;

    @Override
    @Transactional
    public Order handle(CreateOrderCommand command) {
        CreateOrderEvent event = new CreateOrderEvent();
        event.setUserId(command.getUserId());
        event.setDiscount(new PrecisionMoney(command.getDiscount().toString()));
        event.setPaidAmount(new PrecisionMoney(command.getPaidAmount().toString()));

        List<CreateOrderEvent.OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderCommand.OrderItem cmdOrderItem: command.getOrderItems()) {
            CreateOrderEvent.OrderItem orderItem = new CreateOrderEvent.OrderItem();
            orderItem.setProductId(cmdOrderItem.getProductId());
            orderItem.setAmount(new PrecisionMoney(cmdOrderItem.getAmount().toString()));
            orderItem.setCount(cmdOrderItem.getCount());
            orderItems.add(orderItem);
        }
        event.setOrderItems(orderItems);

        Order order = new Order(event);
        orderRepostiory.save(order);
        return order;
    }
}
