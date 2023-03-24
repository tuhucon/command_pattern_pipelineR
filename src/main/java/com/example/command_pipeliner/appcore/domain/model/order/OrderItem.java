package com.example.command_pipeliner.appcore.domain.model.order;


import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItem {

    Long productId;
    
    Integer count;
    
    PrecisionMoney amount = PrecisionMoney.ZERO;

    @JsonIgnore
    Order order;
    
    public OrderItem(Long productId, Integer count, PrecisionMoney amount) {
        this.productId = productId;
        this.count = count;
        this.amount = amount;
    }
    
}
