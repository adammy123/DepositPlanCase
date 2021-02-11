package com.adam;

import com.adam.model.*;
import com.adam.controller.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Application {

    public static void main(String[] args) {

        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, 10000L);
        depositPlanOneTime.addPortfolioPlan(Portfolio.RETIREMENT, 500L);

        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, 0L);
        depositPlanMonthly.addPortfolioPlan(Portfolio.RETIREMENT, 100L);

        List<Deposit> deposits = Arrays.asList(new Deposit(10500L), new Deposit(100L));

        PrintController.printDepositPlan(depositPlanOneTime);
        PrintController.printDepositPlan(depositPlanMonthly);
        PrintController.printDeposits(deposits);
        System.out.println("-----------------------------------\n");

        List<CustomerPortfolio> customerPortfolios = AllocationController.getFundsAllocation(Arrays.asList(depositPlanOneTime, depositPlanMonthly), deposits);
        PrintController.printCustomerPortfolios(customerPortfolios);
    }
}
