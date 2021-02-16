package com.adam.controller;

import com.adam.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.adam.model.ErrorMessage.DEPOSIT_NOT_MORE_THAN_ZERO;
import static com.adam.model.ErrorMessage.DEPOSIT_PLAN_NOT_MORE_THAN_ZERO;

public class ApplicationController {

    public static List<CustomerPortfolio> runApplication (List<DepositPlan> depositPlans, List<Deposit> deposits) throws DepositPlanCaseException {
        List<CustomerPortfolio> customerPortfolios = getCustomerPortfolios(depositPlans);

        if(deposits.stream().anyMatch(deposit -> deposit.getValue().compareTo(BigDecimal.ZERO) <= 0)) {
            throw new DepositPlanCaseException(DEPOSIT_NOT_MORE_THAN_ZERO);
        }
        AllocationController.allocateFunds(customerPortfolios, deposits);

        return customerPortfolios;
    }

    private static List<CustomerPortfolio> getCustomerPortfolios(List<DepositPlan> depositPlans) throws DepositPlanCaseException {
        DepositPlan depositPlanOneTime = new DepositPlan();
        DepositPlan depositPlanMonthly = new DepositPlan();

        Optional<DepositPlan> depositPlanOneTimeOptional = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.ONE_TIME)).findFirst();
        if(depositPlanOneTimeOptional.isPresent()) depositPlanOneTime = depositPlanOneTimeOptional.get();
        if(depositPlanOneTime.getPortfolioValueMap().values().stream().anyMatch(value -> value.compareTo(BigDecimal.ZERO) < 0)){
            throw new DepositPlanCaseException(DEPOSIT_PLAN_NOT_MORE_THAN_ZERO);
        }

        Optional<DepositPlan> depositPlanMonthlyOptional = depositPlans.stream().filter(depositPlan -> depositPlan.getPlan().equals(Plan.MONTHLY)).findFirst();
        if(depositPlanMonthlyOptional.isPresent()) depositPlanMonthly = depositPlanMonthlyOptional.get();
        if(depositPlanMonthly.getPortfolioValueMap().values().stream().anyMatch(value -> value.compareTo(BigDecimal.ZERO) < 0)){
            throw new DepositPlanCaseException(DEPOSIT_PLAN_NOT_MORE_THAN_ZERO);
        }

        return PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);
    }
}
