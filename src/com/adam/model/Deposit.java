package com.adam.model;

import java.math.BigDecimal;

public class Deposit {

    private BigDecimal value;

    public Deposit(BigDecimal value) {
        this.value = value;
    }

    public void subtractDeposit(BigDecimal toSubtract) {
        setValue(getValue().subtract(toSubtract));
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
