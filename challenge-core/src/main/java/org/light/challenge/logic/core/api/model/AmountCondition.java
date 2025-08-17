package org.light.challenge.logic.core.api.model;

public class AmountCondition implements Condition {

    private final AmountOperand operand;
    private final long threshold;

    public AmountCondition(AmountOperand operand, long threshold) {
        this.operand = operand;
        this.threshold = threshold;
    }

    public AmountOperand getOperand() {
        return operand;
    }

    public long getThreshold() {
        return threshold;
    }
}
