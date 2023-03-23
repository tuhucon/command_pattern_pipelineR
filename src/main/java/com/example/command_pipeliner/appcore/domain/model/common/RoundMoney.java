package com.example.command_pipeliner.appcore.domain.model.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RoundMoney {

    BigDecimal value;

    public RoundMoney(String money) {
        value = new BigDecimal(money, new MathContext(2, RoundingMode.HALF_UP));
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
