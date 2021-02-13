package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;
import com.adam.model.DepositPlan;
import com.adam.model.Plan;

import java.util.List;
import java.util.stream.Collectors;

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
        if(deposit.getValue() > 0) allocateOneTimeFunds(customerPortfolios, deposit);
        if(deposit.getValue() > 0) allocateMonthlyFunds(customerPortfolios, deposit);
    }

    private static void allocateMonthlyFunds(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setBalance(customerPortfolio.getBalance() + (long)customerPortfolio.getMonthlyProportion() * deposit.getValue()));
    }

    private static void allocateOneTimeFunds(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        List<CustomerPortfolio> customerPortfoliosWithOneTimeShortfall = customerPortfolios.stream().filter(customerPortfolio -> !customerPortfolio.isOneTimeDepositCompleted()).collect(Collectors.toList());
        if(!customerPortfoliosWithOneTimeShortfall.isEmpty()) {
            long totalOneTimeShortfall = customerPortfoliosWithOneTimeShortfall.stream().mapToLong(CustomerPortfolio::getOneTimeShortfall).sum();
            if(deposit.getValue() >= totalOneTimeShortfall) {
                customerPortfoliosWithOneTimeShortfall.forEach(customerPortfolio -> customerPortfolio.setBalance(customerPortfolio.getBalance() + customerPortfolio.getOneTimeDepositValue()));
                deposit.setValue(deposit.getValue() - totalOneTimeShortfall);
            } else {
                //allocate one time proportionately
                customerPortfoliosWithOneTimeShortfall.forEach(customerPortfolio -> customerPortfolio.setBalance(customerPortfolio.getBalance() + (long)customerPortfolio.getOneTimeProportion() * deposit.getValue()));
                deposit.setValue(0);
            }
        }
    }
}
