package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;
import com.adam.model.DepositPlan;
import com.adam.model.Plan;

import java.util.List;

public class AllocationController {

    public static List<CustomerPortfolio> getFundsAllocation(List<DepositPlan> depositPlans, List<Deposit> deposits){
        //assuming we get both one-time and monthly deposit plans in the list
        //todo: fix the optional
        DepositPlan depositPlanOneTime = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.ONE_TIME)).findFirst().get();
        DepositPlan depositPlanMonthly = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.MONTHLY)).findFirst().get();
        List<CustomerPortfolio> customerPortfolios = PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);

        allocateFunds(customerPortfolios, deposits);
        return customerPortfolios;
    }

    private static void allocateFunds(List<CustomerPortfolio> customerPortfolios, List<Deposit> deposits) {
        deposits.forEach(deposit -> allocateFund(customerPortfolios, deposit));
    }

    private static void allocateFund(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        //assume
    }
}
