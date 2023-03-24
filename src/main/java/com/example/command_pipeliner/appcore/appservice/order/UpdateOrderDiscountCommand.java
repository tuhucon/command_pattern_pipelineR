package com.example.command_pipeliner.appcore.appservice.order;

import com.example.command_pipeliner.appcore.appservice.common.BaseCommand;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.appservice.common.IdempotentCommand;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateOrderDiscountCommand extends IdempotentCommand<Order> {

    Long orderId;

    BigDecimal discount;

    public UpdateOrderDiscountCommand(String commandId) {
        super(commandId);
    }
}
