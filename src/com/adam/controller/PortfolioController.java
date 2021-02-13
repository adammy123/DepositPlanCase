package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.DepositPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortfolioController {

    public static List<CustomerPortfolio> setUpCustomerPortfolios(DepositPlan depositPlanOneTime, DepositPlan depositPlanMonthly) {
        List<CustomerPortfolio> customerPortfolios = new ArrayList<>();

        setUpOneTimePortfolios(customerPortfolios, depositPlanOneTime);
        setUpMonthlyPortfolios(customerPortfolios, depositPlanMonthly);

        return customerPortfolios;
    }

    private static void setUpMonthlyPortfolios(List<CustomerPortfolio> customerPortfolios, DepositPlan depositPlanMonthly) {
        depositPlanMonthly.getPortfolioValueMap().forEach(((portfolio, value) -> {
            Optional<CustomerPortfolio> customerPortfolioOptional = customerPortfolios.stream().filter(
                    customerPortfolio -> customerPortfolio.getPortfolio().equals(portfolio)).findFirst();
            if(customerPortfolioOptional.isPresent()){
                CustomerPortfolio customerPortfolio = customerPortfolioOptional.get();
                customerPortfolio.setMonthlyDepositValue(value);
            } else {
                CustomerPortfolio customerPortfolio = new CustomerPortfolio();
                customerPortfolio.setPortfolio(portfolio);
                customerPortfolio.setMonthlyDepositValue(value);
                customerPortfolios.add(customerPortfolio);
            }
        }));
        long totalMonthly = customerPortfolios.stream().mapToLong(CustomerPortfolio::getMonthlyDepositValue).sum();
        customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setMonthlyProportion(customerPortfolio.getMonthlyDepositValue()/totalMonthly));
    }

    private static void setUpOneTimePortfolios(List<CustomerPortfolio> customerPortfolios, DepositPlan depositPlanOneTime) {
        depositPlanOneTime.getPortfolioValueMap().forEach(((portfolio, value) -> {
            CustomerPortfolio customerPortfolio = new CustomerPortfolio();
            customerPortfolio.setPortfolio(portfolio);
            customerPortfolio.setOneTimeDepositValue(value);
            customerPortfolios.add(customerPortfolio);
        }));
        long totalOneTime = customerPortfolios.stream().mapToLong(CustomerPortfolio::getOneTimeDepositValue).sum();
        customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setOneTimeProportion(customerPortfolio.getOneTimeDepositValue()/totalOneTime));
    }
}
