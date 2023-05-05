package com.example.command_pipeliner.appcore.domain.model.common;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor
@Data
public class PrecisionMoney implements Comparable<PrecisionMoney>{

    public static PrecisionMoney ZERO = new PrecisionMoney("0.0000000000");

    BigDecimal value;

    public PrecisionMoney(BigDecimal money) {
        this(money.toString());
    }

    public PrecisionMoney(String money) {
        this.value = new BigDecimal(money, new MathContext(10, RoundingMode.HALF_UP));
    }

    public PrecisionMoney add(PrecisionMoney money) {
        if (money == null) {
            return this;
        }
        return new PrecisionMoney(value.add(money.value));
    }

    public PrecisionMoney multiple(PrecisionMoney money) {
        if (money == null) {
            return ZERO;
        }
        return new PrecisionMoney(value.multiply(money.value));
    }

    public PrecisionMoney subtract(PrecisionMoney money) {
        if (money == null) {
            return this;
        }
        return new PrecisionMoney(value.subtract(money.value));
    }

    public PrecisionMoney divide(PrecisionMoney money) {
        if (money == null) {
            throw new RuntimeException("can not device to null or zero");
        }
        return new PrecisionMoney(value.divide(money.value));
    }

    public RoundMoney toRoundMoney() {
        return new RoundMoney(this.toString());
    }

    @Override
    public int compareTo(PrecisionMoney o) {
        if (o == null || o.getClass() != PrecisionMoney.class) {
            throw new RuntimeException("Cannot compare with null value or different class");
        }
        return value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
