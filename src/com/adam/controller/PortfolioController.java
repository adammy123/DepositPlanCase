package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.DepositPlan;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        BigDecimal totalMonthly = customerPortfolios.stream().map(CustomerPortfolio::getMonthlyDepositValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalMonthly.compareTo(BigDecimal.ZERO) > 0) {
            customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setMonthlyProportion(customerPortfolio.getMonthlyDepositValue().divide(totalMonthly, 2, RoundingMode.HALF_UP)));
        };
    }

    private static void setUpOneTimePortfolios(List<CustomerPortfolio> customerPortfolios, DepositPlan depositPlanOneTime) {
        depositPlanOneTime.getPortfolioValueMap().forEach(((portfolio, value) -> {
            CustomerPortfolio customerPortfolio = new CustomerPortfolio();
            customerPortfolio.setPortfolio(portfolio);
            customerPortfolio.setOneTimeDepositValue(value);
            customerPortfolios.add(customerPortfolio);
        }));
        BigDecimal totalOneTime = customerPortfolios.stream().map(CustomerPortfolio::getOneTimeDepositValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalOneTime.compareTo(BigDecimal.ZERO) > 0) {
            customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setOneTimeProportion(customerPortfolio.getOneTimeDepositValue().divide(totalOneTime, 2, RoundingMode.HALF_UP)));
        }
    }
}
