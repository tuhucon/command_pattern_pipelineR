package com.example.command_pipeliner.appcore.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddOrderItemEvent extends DomainEvent {

    Long productId;
    Integer count;
    PrecisionMoney amount;
}
