package com.adam.model;

public class Deposit {

    private long value;

    public Deposit(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
