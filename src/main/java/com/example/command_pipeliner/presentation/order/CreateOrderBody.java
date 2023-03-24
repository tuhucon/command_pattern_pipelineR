package com.example.command_pipeliner.presentation.order;

import com.example.command_pipeliner.presentation.common.IdempotentKeyBody;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderBody extends IdempotentKeyBody {

    @Data
    public static class OrderItem {

        Long productId;
        Integer count;
        BigDecimal amount;
    }

    Long UserId;
    BigDecimal discount;
    BigDecimal paidAmount;
    List<OrderItem> orderItems;
}
