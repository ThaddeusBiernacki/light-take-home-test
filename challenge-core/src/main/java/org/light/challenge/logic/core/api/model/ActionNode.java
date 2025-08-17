package org.light.challenge.logic.core.api.model;

public class ActionNode implements WorkflowNode {

    private final ActionType actionType;
    private final String contact;

    public ActionNode(ActionType actionType,
                          String contact) {
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
