package org.light.challenge.logic.core.api.model;

public class ConditionNode implements WorkflowNode {

    private final Condition condition;
    private final WorkflowNode onFailure;
    private final WorkflowNode onSuccess;

    public ConditionNode(Condition condition,
                         WorkflowNode onFailure,
                         WorkflowNode onSuccess) {
        this.condition = condition;
        this.onFailure = onFailure;
        this.onSuccess = onSuccess;
    }

    public Condition getCondition() {
        return condition;
    }
    public WorkflowNode getOnFailure() {
        return onFailure;
    }
    public WorkflowNode getOnSuccess() {
        return onSuccess;
    }
}
