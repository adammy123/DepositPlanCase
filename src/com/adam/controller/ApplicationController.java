package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;
import com.adam.model.DepositPlan;
import com.adam.model.Plan;

import java.util.List;
import java.util.Optional;

public class ApplicationController {

    public static List<CustomerPortfolio> runApplication (List<DepositPlan> depositPlans, List<Deposit> deposits) {
        List<CustomerPortfolio> customerPortfolios = getCustomerPortfolios(depositPlans);
        AllocationController.allocateFunds(customerPortfolios, deposits);

        return customerPortfolios;
    }

    private static List<CustomerPortfolio> getCustomerPortfolios(List<DepositPlan> depositPlans) {
        DepositPlan depositPlanOneTime = new DepositPlan();
        DepositPlan depositPlanMonthly = new DepositPlan();

        Optional<DepositPlan> depositPlanOneTimeOptional = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.ONE_TIME)).findFirst();
        if(depositPlanOneTimeOptional.isPresent()) depositPlanOneTime = depositPlanOneTimeOptional.get();

        Optional<DepositPlan> depositPlanMonthlyOptional = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.MONTHLY)).findFirst();
        if(depositPlanMonthlyOptional.isPresent()) depositPlanMonthly = depositPlanMonthlyOptional.get();

        return PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);
    }
}
