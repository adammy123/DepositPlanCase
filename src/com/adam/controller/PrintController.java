package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.Deposit;
import com.adam.model.DepositPlan;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class PrintController {

    private static DecimalFormat df = new DecimalFormat("#,###.00");

    public static void printCustomerPortfolios(List<CustomerPortfolio> customerPortfolios) {
        customerPortfolios.forEach(customerPortfolio -> {
            System.out.printf("Portfolio: %s\n", customerPortfolio.getPortfolio());
            System.out.printf("One-time deposit plan: %s\n", df.format(customerPortfolio.getOneTimeDepositValue()));
            System.out.printf("Monthly deposit plan: %s\n", df.format(customerPortfolio.getMonthlyDepositValue()));
            System.out.printf("Current value: %s\n\n", df.format(customerPortfolio.getBalance()));
        });
    }

    public static void printDeposits(List<Deposit> deposits) {
        System.out.printf("Deposits: %s\n\n", Arrays.toString(deposits.stream().map(Deposit::getValue).toArray()));
    }

    public static void printDepositPlan(DepositPlan depositPlan) {
        System.out.printf("Deposit Plan: %s\n", depositPlan.getPlan());
        depositPlan.getPortfolioValueMap().forEach(((portfolio, value) -> {
            System.out.printf("Portfolio: %s, Value: %s\n", portfolio, df.format(value));
        }));
        System.out.println();

    }
}
