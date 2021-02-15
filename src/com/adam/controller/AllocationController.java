package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AllocationController {

    public static void allocateFunds(List<CustomerPortfolio> customerPortfolios, List<Deposit> deposits) {
        deposits.forEach(deposit -> allocateFund(customerPortfolios, deposit));
    }

    private static void allocateFund(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        if (deposit.getValue().compareTo(BigDecimal.ZERO) > 0) allocateOneTimeFunds(customerPortfolios, deposit);
        if (deposit.getValue().compareTo(BigDecimal.ZERO) > 0) allocateMonthlyFunds(customerPortfolios, deposit);
    }

    private static void allocateMonthlyFunds(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        customerPortfolios.forEach(customerPortfolio -> customerPortfolio.setBalance(customerPortfolio.getBalance().add(customerPortfolio.getMonthlyProportion().multiply(deposit.getValue()))));
    }

    private static void allocateOneTimeFunds(List<CustomerPortfolio> customerPortfolios, Deposit deposit) {
        List<CustomerPortfolio> customerPortfoliosWithOneTimeShortfall = customerPortfolios.stream().filter(customerPortfolio
                -> !customerPortfolio.isOneTimeDepositCompleted()).collect(Collectors.toList());
        if (!customerPortfoliosWithOneTimeShortfall.isEmpty()) {
            BigDecimal totalOneTimeShortfall = customerPortfoliosWithOneTimeShortfall.stream().map(CustomerPortfolio::getOneTimeShortfall).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (deposit.getValue().compareTo(totalOneTimeShortfall) >= 0) {
                customerPortfoliosWithOneTimeShortfall.forEach(customerPortfolio ->
                        customerPortfolio.setBalance(customerPortfolio.getBalance().add(customerPortfolio.getOneTimeDepositValue())));
                deposit.setValue(deposit.getValue().subtract(totalOneTimeShortfall));
            } else {
                customerPortfoliosWithOneTimeShortfall.forEach(customerPortfolio ->
                        customerPortfolio.setBalance(customerPortfolio.getBalance().add(customerPortfolio.getOneTimeProportion().multiply(deposit.getValue()))));
                deposit.setValue(BigDecimal.ZERO);
            }
        }
    }
}
