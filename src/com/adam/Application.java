package com.adam;

import com.adam.model.*;
import com.adam.controller.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        DepositPlan depositPlanOneTime = getDepositPlanOneTIme();
        DepositPlan depositPlanMonthly = getDepositPlanMonthly();
        List<Deposit> deposits = generateDeposits();

        PrintController.printDepositPlan(depositPlanOneTime);
        PrintController.printDepositPlan(depositPlanMonthly);
        PrintController.printDeposits(deposits);
        System.out.println("-----------------------------------\n");

        List<CustomerPortfolio> customerPortfolios = ApplicationController.runApplication(Arrays.asList(depositPlanOneTime, depositPlanMonthly), deposits);
        PrintController.printCustomerPortfolios(customerPortfolios);
    }

    private static DepositPlan getDepositPlanMonthly() {
        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, BigDecimal.ZERO);
        depositPlanMonthly.addPortfolioPlan(Portfolio.RETIREMENT, new BigDecimal(100));
        return depositPlanMonthly;
    }

    private static DepositPlan getDepositPlanOneTIme() {
        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, new BigDecimal(10000));
        depositPlanOneTime.addPortfolioPlan(Portfolio.RETIREMENT, new BigDecimal(500));
        return depositPlanOneTime;
    }

    private static List<Deposit> generateDeposits() {
        return Arrays.asList(new Deposit(new BigDecimal(10500)), new Deposit(new BigDecimal(100)));
    }
}
