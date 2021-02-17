package com.adam.controller;

import com.adam.model.*;
import com.adam.util.PrintUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApplicationControllerTest {

    @Test(expected = DepositPlanCaseException.class)
    public void depositSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.ONE_TIME);
        depositPlan.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(-1))));
    }

    @Test(expected = DepositPlanCaseException.class)
    public void depositPlanOneTimeValueSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.ONE_TIME);
        depositPlan.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(-1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(1))));
    }

    @Test(expected = DepositPlanCaseException.class)
    public void depositPlanMonthlyValueSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.MONTHLY);
        depositPlan.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(-1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(1))));
    }

    @Test
    public void scenario1() throws DepositPlanCaseException {
        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.GENERAL, new BigDecimal(1000));
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(500));
        depositPlanOneTime.addPortfolioPlan(Portfolio.RETIREMENT, BigDecimal.ZERO);

        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.GENERAL, new BigDecimal(100));
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(100));
        depositPlanMonthly.addPortfolioPlan(Portfolio.RETIREMENT, new BigDecimal(200));

        List<Deposit> deposits = Arrays.asList(new Deposit(new BigDecimal(1000)),
                                               new Deposit(new BigDecimal(200)),
                                               new Deposit(new BigDecimal(600)),
                                               new Deposit(new BigDecimal(500)));

        PrintUtil.printDeposits(deposits);
        PrintUtil.printDepositPlan(depositPlanOneTime);

        List<CustomerPortfolio> customerPortfolios = ApplicationController.runApplication(Arrays.asList(depositPlanOneTime, depositPlanMonthly), deposits);
        PrintUtil.printCustomerPortfolios(customerPortfolios);

        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.HIGH_RISK)).findFirst().get().getBalance().compareTo(new BigDecimal(700)));
        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.GENERAL)).findFirst().get().getBalance().compareTo(new BigDecimal(1200)));
        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.RETIREMENT)).findFirst().get().getBalance().compareTo(new BigDecimal(400)));
    }

    @Test
    public void scenario2() throws DepositPlanCaseException {
        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.GENERAL, BigDecimal.ZERO);
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(6000));
        depositPlanOneTime.addPortfolioPlan(Portfolio.RETIREMENT, new BigDecimal(4000));

        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.GENERAL, new BigDecimal(400));
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, BigDecimal.ZERO);
        depositPlanMonthly.addPortfolioPlan(Portfolio.RETIREMENT, new BigDecimal(800));

        List<Deposit> deposits = Collections.singletonList(new Deposit(new BigDecimal(20000)));

        List<CustomerPortfolio> customerPortfolios = ApplicationController.runApplication(Arrays.asList(depositPlanOneTime, depositPlanMonthly), deposits);
        PrintUtil.printCustomerPortfolios(customerPortfolios);

        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.HIGH_RISK)).findFirst().get().getBalance().compareTo(new BigDecimal(6000)));
        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.GENERAL)).findFirst().get().getBalance().compareTo(new BigDecimal(3300)));
        Assert.assertEquals(0, customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.RETIREMENT)).findFirst().get().getBalance().compareTo(new BigDecimal(10700)));
    }
}