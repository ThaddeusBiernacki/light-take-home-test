package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.light.challenge.data.model.invoice.Invoice;

public final class AmountCondition implements Condition {

    private final AmountOperand operand;
    private final long threshold;

    @JsonCreator
    public AmountCondition(
            @JsonProperty("operand") AmountOperand operand,
            @JsonProperty("threshold") long threshold) {
        this.operand = operand;
        this.threshold = threshold;
    }

    @Override
    public boolean evaluate(Invoice invoice) {
        long value = invoice.getAmount();
        return switch (operand) {
            case GREATER_THAN -> value > threshold;
            case LESS_THAN -> value < threshold;
        };
    }
}

