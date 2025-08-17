package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConditionNode implements WorkflowNode {

    private final Condition condition;
    private final WorkflowNode onFailure;
    private final WorkflowNode onSuccess;

    @JsonCreator
    public ConditionNode(
            @JsonProperty("condition") Condition condition,
            @JsonProperty("onFailure") WorkflowNode onFailure,
            @JsonProperty("onSuccess") WorkflowNode onSuccess) {
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
