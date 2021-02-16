package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;
import com.adam.model.Portfolio;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllocationControllerTest {

    @Test
    public void allocateFundsTest_HappyPath() {
        CustomerPortfolio customerPortfolioHighRisk = new CustomerPortfolio();
        customerPortfolioHighRisk.setPortfolio(Portfolio.HIGH_RISK);
        customerPortfolioHighRisk.setOneTimeDepositValue(new BigDecimal(400));
        customerPortfolioHighRisk.setMonthlyDepositValue(new BigDecimal(500));
        customerPortfolioHighRisk.setOneTimeProportion(new BigDecimal(".4"));
        customerPortfolioHighRisk.setMonthlyProportion(new BigDecimal(".25"));

        CustomerPortfolio customerPortfolioRetirement = new CustomerPortfolio();
        customerPortfolioRetirement.setPortfolio(Portfolio.HIGH_RISK);
        customerPortfolioRetirement.setOneTimeDepositValue(new BigDecimal(600));
        customerPortfolioRetirement.setMonthlyDepositValue(new BigDecimal(1500));
        customerPortfolioRetirement.setOneTimeProportion(new BigDecimal(".6"));
        customerPortfolioRetirement.setMonthlyProportion(new BigDecimal(".75"));

        List<CustomerPortfolio> customerPortfolios = Arrays.asList(customerPortfolioHighRisk, customerPortfolioRetirement);
        List<Deposit> deposits = Collections.singletonList(new Deposit(new BigDecimal(3000)));

        AllocationController.allocateFunds(customerPortfolios, deposits);

        Assert.assertEquals(customerPortfolioHighRisk.getBalance().compareTo(new BigDecimal(900)),0);
        Assert.assertEquals(customerPortfolioRetirement.getBalance().compareTo(new BigDecimal(2100)), 0);
    }
    @Test
    public void allocateFundsTest_OneDepositLessThanTotalOneTIme() {
        CustomerPortfolio customerPortfolioHighRisk = new CustomerPortfolio();
        customerPortfolioHighRisk.setPortfolio(Portfolio.HIGH_RISK);
        customerPortfolioHighRisk.setOneTimeDepositValue(new BigDecimal(400));
        customerPortfolioHighRisk.setOneTimeProportion(new BigDecimal(".4"));

        CustomerPortfolio customerPortfolioRetirement = new CustomerPortfolio();
        customerPortfolioRetirement.setPortfolio(Portfolio.HIGH_RISK);
        customerPortfolioRetirement.setOneTimeDepositValue(new BigDecimal(600));
        customerPortfolioRetirement.setOneTimeProportion(new BigDecimal(".6"));

        List<CustomerPortfolio> customerPortfolios = Arrays.asList(customerPortfolioHighRisk, customerPortfolioRetirement);
        List<Deposit> deposits = Collections.singletonList(new Deposit(new BigDecimal(100)));

        AllocationController.allocateFunds(customerPortfolios, deposits);

        Assert.assertEquals(customerPortfolioHighRisk.getBalance().compareTo(new BigDecimal(40)),0);
        Assert.assertEquals(customerPortfolioRetirement.getBalance().compareTo(new BigDecimal(60)), 0);
    }
}