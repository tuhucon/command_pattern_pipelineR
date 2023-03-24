package com.example.command_pipeliner.appcore.appservice.order;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.appservice.common.BaseCommand;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.appservice.common.IdempotentCommand;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateOrderCommand extends IdempotentCommand<Order> {

    public CreateOrderCommand(String commandId) {
        super(commandId);
    }

    @Data
    public static class OrderItem {

        Long productId;
        Integer count;
        BigDecimal amount;
    }

    Long userId;
    BigDecimal discount;
    BigDecimal paidAmount;
    List<OrderItem> orderItems;
}
