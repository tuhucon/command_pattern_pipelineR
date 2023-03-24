package com.example.command_pipeliner.presentation.order;

import com.example.command_pipeliner.presentation.common.IdempotentKeyBody;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateOrderDiscountBody extends IdempotentKeyBody {

    BigDecimal discount;
}
