package com.example.command_pipeliner.appcore.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateOrderEvent extends DomainEvent {

    PrecisionMoney discount;
    PrecisionMoney paidAmount;
    List<OrderItems> orderItems;
}
