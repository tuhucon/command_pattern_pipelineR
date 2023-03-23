package com.example.command_pipeliner.appcore.domain.model.order;


import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import lombok.Data;

@Data
public class OrderItems {

    Long productId;

    Integer count;

    PrecisionMoney amount;

    Orders order;

    public OrderItems(Long productId, Integer count, PrecisionMoney amount) {

    }

}
