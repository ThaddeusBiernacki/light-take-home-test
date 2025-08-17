package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.light.challenge.data.model.invoice.Invoice;
import org.springframework.lang.Nullable;

public final class ApprovalCondition implements Condition {

    @Nullable private final ApprovalType requiredApproval;

    @JsonCreator
    public ApprovalCondition(@JsonProperty("requiredApproval") @Nullable ApprovalType requiredApproval) {
        this.requiredApproval = requiredApproval;
    }

    @Override
    public boolean evaluate(Invoice invoice) {
        return invoice.getApprovalType() == requiredApproval;
    }
}