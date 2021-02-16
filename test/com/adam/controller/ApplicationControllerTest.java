package com.adam.controller;

import com.adam.model.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class ApplicationControllerTest {

    @Test(expected = DepositPlanCaseException.class)
    public void depositSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.ONE_TIME);
        depositPlan.getPortfolioValueMap().put(Portfolio.HIGH_RISK, new BigDecimal(1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(-1))));
    }

    @Test(expected = DepositPlanCaseException.class)
    public void depositPlanOneTimeValueSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.ONE_TIME);
        depositPlan.getPortfolioValueMap().put(Portfolio.HIGH_RISK, new BigDecimal(-1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(1))));
    }

    @Test(expected = DepositPlanCaseException.class)
    public void depositPlanMonthlyValueSmallerThanZeroTest() throws DepositPlanCaseException {
        DepositPlan depositPlan = new DepositPlan();
        depositPlan.setPlan(Plan.MONTHLY);
        depositPlan.getPortfolioValueMap().put(Portfolio.HIGH_RISK, new BigDecimal(-1));
        ApplicationController.runApplication(Collections.singletonList(depositPlan), Collections.singletonList(new Deposit(new BigDecimal(1))));
    }
}