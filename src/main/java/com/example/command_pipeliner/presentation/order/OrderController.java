package com.example.command_pipeliner.presentation.order;

import an.awesome.pipelinr.Pipeline;
import com.example.command_pipeliner.appcore.appservice.order.CreateOrderCommand;
import com.example.command_pipeliner.appcore.appservice.order.UpdateOrderDiscountCommand;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.domain.model.order.OrderRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final Pipeline pipeline;

    private final OrderRepostiory orderRepostiory;

    @PostMapping("/orders")
    public Order createOrder(@RequestBody CreateOrderBody body) {
        CreateOrderCommand command = new CreateOrderCommand(body.getIdempotentKey());
        command.setUserId(body.getUserId());
        command.setDiscount(body.getDiscount());
        command.setPaidAmount(body.getPaidAmount());

        List<CreateOrderCommand.OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderBody.OrderItem item: body.getOrderItems()) {
            CreateOrderCommand.OrderItem orderItem = new CreateOrderCommand.OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setCount(item.getCount());
            orderItem.setAmount(item.getAmount());
            orderItems.add(orderItem);
        }

        command.setOrderItems(orderItems);
        Order order = command.execute(pipeline);
        return order;
    }

    @PostMapping("/orders/{orderId}/updateDiscount")
    public Order updateOrderDiscount(@PathVariable Long orderId, @RequestBody UpdateOrderDiscountBody body) {
        UpdateOrderDiscountCommand command = new UpdateOrderDiscountCommand(body.getIdempotentKey());
        command.setOrderId(orderId);
        command.setDiscount(body.getDiscount());
        Order order = command.execute(pipeline);
        return order;
    }

    @GetMapping("/orders")
    public List<Order> getOrders(@RequestParam Long userId) {
        return orderRepostiory.FindAllByUserId(userId);
    }
}
