package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionNode implements WorkflowNode {

    private final ActionType actionType;
    private final String contact;

    @JsonCreator
    public ActionNode(
            @JsonProperty("actionType") ActionType actionType,
            @JsonProperty("contact") String contact) {
        this.actionType = actionType;
        this.contact = contact;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getContact() {
        return contact;
    }
}
