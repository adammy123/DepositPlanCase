package com.adam.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class DepositPlan {

    private Plan plan;
    private HashMap<Portfolio, BigDecimal> portfolioValueMap = new HashMap<>();

    public DepositPlan() {
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public HashMap<Portfolio, BigDecimal> getPortfolioValueMap() {
        return portfolioValueMap;
    }

    public void addPortfolioPlan(Portfolio portfolio, BigDecimal value) {
        this.portfolioValueMap.put(portfolio, value);
    }
}
