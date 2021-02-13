package com.adam.model;

public class CustomerPortfolio {

    private Portfolio portfolio;
    private long balance = 0L;
    private long oneTimeDepositValue;
    private long monthlyDepositValue;
    private long oneTimeProportion;
    private long monthlyProportion;

    public long getOneTimeProportion() {
        return oneTimeProportion;
    }

    public void setOneTimeProportion(long oneTimeProportion) {
        this.oneTimeProportion = oneTimeProportion;
    }

    public long getMonthlyProportion() {
        return monthlyProportion;
    }

    public void setMonthlyProportion(long monthlyProportion) {
        this.monthlyProportion = monthlyProportion;
    }

    public CustomerPortfolio () {}

    public long getOneTimeShortfall () {
        long shortfall = oneTimeDepositValue - balance;
        return shortfall > 0 ? shortfall : 0;
    }

    public boolean isOneTimeDepositCompleted() {
        return getOneTimeShortfall() == 0;
    }

    public long getOneTimeDepositValue() {
        return oneTimeDepositValue;
    }

    public void setOneTimeDepositValue(long oneTimeDepositValue) {
        this.oneTimeDepositValue = oneTimeDepositValue;
    }

    public long getMonthlyDepositValue() {
        return monthlyDepositValue;
    }

    public void setMonthlyDepositValue(long monthlyDepositValue) {
        this.monthlyDepositValue = monthlyDepositValue;
    }


    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
