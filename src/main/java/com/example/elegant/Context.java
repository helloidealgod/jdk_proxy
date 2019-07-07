package com.example.elegant;

import java.math.BigDecimal;

public class Context {
    private Strategy strategy;

    public BigDecimal getPay(Integer type) throws Exception{
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        strategy = strategyFactory.create(type);
        return strategy.getPay(type);
    }
}
