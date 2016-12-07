package com.skiwi.bfcompiler.optimizers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank van Heeswijk
 */
public class OptimizeStrategyOrder {
    private final List<OptimizeStrategy> optimizeStrategyOrder = new ArrayList<>();

    private OptimizeStrategyOrder(final Builder builder) {
        this.optimizeStrategyOrder.addAll(builder.optimizeStrategyOrder);
    }

    public List<OptimizeStrategy> getOptimizeStrategyOrder() {
        return optimizeStrategyOrder;
    }

    public static class Builder {
        private final List<OptimizeStrategy> optimizeStrategyOrder = new ArrayList<>();

        public Builder then(final OptimizeStrategy optimizeStrategy) {
            optimizeStrategyOrder.add(optimizeStrategy);
            return this;
        }

        public Builder then(final List<OptimizeStrategy> optimizeStrategies) {
            optimizeStrategyOrder.addAll(optimizeStrategies);
            return this;
        }

        public OptimizeStrategyOrder build() {
            return new OptimizeStrategyOrder(this);
        }
    }
}
