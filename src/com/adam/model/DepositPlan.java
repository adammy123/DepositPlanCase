package com.adam.model;

import java.util.HashMap;

public class DepositPlan {

    private Plan plan;
    private HashMap<Portfolio, Long> portfolioValueMap = new HashMap<>();

    public DepositPlan() {
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public HashMap<Portfolio, Long> getPortfolioValueMap() {
        return portfolioValueMap;
    }

    public void addPortfolioPlan(Portfolio portfolio, long value) {
        this.portfolioValueMap.put(portfolio, value);
    }
}
