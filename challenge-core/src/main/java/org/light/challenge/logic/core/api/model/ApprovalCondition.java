package org.light.challenge.logic.core.api.model;

public class ApprovalCondition implements Condition {

    private final ApprovalType approvalType;

    public ApprovalCondition(ApprovalType approvalType) {
        this.approvalType = approvalType;
    }

    public ApprovalType getApprovalType() {
        return approvalType;
    }
}
