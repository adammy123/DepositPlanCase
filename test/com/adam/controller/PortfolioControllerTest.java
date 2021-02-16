package com.adam.controller;

import com.adam.model.CustomerPortfolio;
import com.adam.model.DepositPlan;
import com.adam.model.Plan;
import com.adam.model.Portfolio;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class PortfolioControllerTest {

    private final BigDecimal oneTimeHighRisk = new BigDecimal(10000);
    private final BigDecimal monthlyHighRisk = BigDecimal.ZERO;
    private final BigDecimal oneTimeRetirement = new BigDecimal(500);
    private final BigDecimal monthlyRetirement = new BigDecimal(100);

    @Test
    public void setUpCustomerPortfolios_HappyPath() {

        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, oneTimeHighRisk);
        depositPlanOneTime.addPortfolioPlan(Portfolio.RETIREMENT, oneTimeRetirement);

        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, monthlyHighRisk);
        depositPlanMonthly.addPortfolioPlan(Portfolio.RETIREMENT, monthlyRetirement);

        BigDecimal expectedOneTimeProportionHighRisk = oneTimeHighRisk.divide(oneTimeHighRisk.add(oneTimeRetirement), 2, RoundingMode.HALF_UP);
        BigDecimal expectedOneTimeProportionRetirement = oneTimeRetirement.divide((oneTimeHighRisk.add(oneTimeRetirement)), 2, RoundingMode.HALF_UP);
        BigDecimal expectedMonthlyProportionHighRisk = monthlyHighRisk.divide((monthlyHighRisk.add(monthlyRetirement)), 2, RoundingMode.HALF_UP);
        BigDecimal expectedMonthlyProportionRetirement = monthlyRetirement.divide((monthlyHighRisk.add(monthlyRetirement)), 2, RoundingMode.HALF_UP);

        List<CustomerPortfolio> customerPortfolios = PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);

        Assert.assertEquals(2, customerPortfolios.size());

        CustomerPortfolio customerPortfolioHighRisk = customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.HIGH_RISK)).findFirst().get();
        Assert.assertEquals(customerPortfolioHighRisk.getOneTimeDepositValue().compareTo(oneTimeHighRisk), 0);
        Assert.assertEquals(customerPortfolioHighRisk.getMonthlyDepositValue().compareTo(monthlyHighRisk), 0);
        Assert.assertEquals(customerPortfolioHighRisk.getOneTimeProportion().compareTo(expectedOneTimeProportionHighRisk), 0);
        Assert.assertEquals(customerPortfolioHighRisk.getMonthlyProportion().compareTo(expectedMonthlyProportionHighRisk), 0);

        CustomerPortfolio customerPortfolioRetirement = customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.RETIREMENT)).findFirst().get();
        Assert.assertEquals(customerPortfolioRetirement.getOneTimeDepositValue().compareTo(oneTimeRetirement), 0);
        Assert.assertEquals(customerPortfolioRetirement.getMonthlyDepositValue().compareTo(monthlyRetirement), 0);
        Assert.assertEquals(customerPortfolioRetirement.getOneTimeProportion().compareTo(expectedOneTimeProportionRetirement), 0);
        Assert.assertEquals(customerPortfolioRetirement.getMonthlyProportion().compareTo(expectedMonthlyProportionRetirement), 0);
    }

    @Test
    public void setUpCustomerPortfolios_OneTimeOnly() {

        DepositPlan depositPlanOneTime = new DepositPlan();
        depositPlanOneTime.setPlan(Plan.ONE_TIME);
        depositPlanOneTime.addPortfolioPlan(Portfolio.HIGH_RISK, oneTimeHighRisk);

        DepositPlan depositPlanMonthly = new DepositPlan();

        List<CustomerPortfolio> customerPortfolios = PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);

        Assert.assertEquals(1, customerPortfolios.size());

        CustomerPortfolio customerPortfolioHighRisk = customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.HIGH_RISK)).findFirst().get();
        Assert.assertEquals(customerPortfolioHighRisk.getOneTimeDepositValue().compareTo(oneTimeHighRisk), 0);
    }

    @Test
    public void setUpCustomerPortfolios_MonthlyOnly() {
        DepositPlan depositPlanOneTime = new DepositPlan();

        DepositPlan depositPlanMonthly = new DepositPlan();
        depositPlanMonthly.setPlan(Plan.MONTHLY);
        depositPlanMonthly.addPortfolioPlan(Portfolio.HIGH_RISK, monthlyHighRisk);

        List<CustomerPortfolio> customerPortfolios = PortfolioController.setUpCustomerPortfolios(depositPlanOneTime, depositPlanMonthly);

        Assert.assertEquals(1, customerPortfolios.size());

        CustomerPortfolio customerPortfolioHighRisk = customerPortfolios.stream().filter(customerPortfolio -> customerPortfolio.getPortfolio().equals(Portfolio.HIGH_RISK)).findFirst().get();
        Assert.assertEquals(customerPortfolioHighRisk.getMonthlyDepositValue().compareTo(monthlyHighRisk), 0);
    }
}