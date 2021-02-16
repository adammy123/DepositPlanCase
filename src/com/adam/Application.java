package com.adam;

import com.adam.model.*;
import com.adam.controller.*;
import com.adam.util.PrintUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        DepositPlan depositPlanOneTime = getDepositPlanOneTIme();
        DepositPlan depositPlanMonthly = getDepositPlanMonthly();
        List<Deposit> deposits = generateDeposits();

        PrintUtil.printDepositPlan(depositPlanOneTime);
        PrintUtil.printDepositPlan(depositPlanMonthly);
        PrintUtil.printDeposits(deposits);
        System.out.println("-----------------------------------\n");

        List<CustomerPortfolio> customerPortfolios = new ArrayList<>();
        try {
            customerPortfolios = ApplicationController.runApplication(Arrays.asList(depositPlanOneTime, depositPlanMonthly), deposits);
        } catch (DepositPlanCaseException e){
            System.out.println(e.getMessage());
            System.out.println("Please re-run application with correct values!");
        }


        PrintUtil.printCustomerPortfolios(customerPortfolios);
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
