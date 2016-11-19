package com.skiwi.bfcompiler.options;

/**
 * @author Frank van Heeswijk
 */
public class BFOptions {
    private final int memoryCellAmount;

    private BFOptions(final Builder builder) {
        this.memoryCellAmount = builder.memoryCellAmount;
    }

    public int getMemoryCellAmount() {
        return memoryCellAmount;
    }

    public static class Builder {
        private int memoryCellAmount;

        public Builder memoryCellAmount(final int memoryCellAmount) {
            this.memoryCellAmount = memoryCellAmount;
            return this;
        }

        public BFOptions build() {
            return new BFOptions(this);
        }
    }
}
