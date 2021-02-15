package com.adam.model;

import java.math.BigDecimal;

public class CustomerPortfolio {

    private Portfolio portfolio;
    private BigDecimal balance = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
    private BigDecimal oneTimeDepositValue= BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
    private BigDecimal monthlyDepositValue= BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
    private BigDecimal oneTimeProportion= BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
    private BigDecimal monthlyProportion= BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);

    public BigDecimal getOneTimeProportion() {
        return oneTimeProportion;
    }

    public void setOneTimeProportion(BigDecimal oneTimeProportion) {
        this.oneTimeProportion = oneTimeProportion;
    }

    public BigDecimal getMonthlyProportion() {
        return monthlyProportion;
    }

    public void setMonthlyProportion(BigDecimal monthlyProportion) {
        this.monthlyProportion = monthlyProportion;
    }

    public CustomerPortfolio () {}

    public BigDecimal getOneTimeShortfall () {
        BigDecimal shortfall = oneTimeDepositValue.subtract(balance);
        return shortfall.compareTo(BigDecimal.ZERO) > 0 ? shortfall : BigDecimal.ZERO;
    }

    public boolean isOneTimeDepositCompleted() {
        return getOneTimeShortfall().equals(BigDecimal.ZERO);
    }

    public BigDecimal getOneTimeDepositValue() {
        return oneTimeDepositValue;
    }

    public void setOneTimeDepositValue(BigDecimal oneTimeDepositValue) {
        this.oneTimeDepositValue = oneTimeDepositValue;
    }

    public BigDecimal getMonthlyDepositValue() {
        return monthlyDepositValue;
    }

    public void setMonthlyDepositValue(BigDecimal monthlyDepositValue) {
        this.monthlyDepositValue = monthlyDepositValue;
    }


    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
