package org.light.challenge.logic.core.api.request;

import org.light.challenge.logic.core.api.model.ConditionNode;

public class CreateWorkflowRequest {

    private final String companyReference;
    private final ConditionNode rootConditionNode;

    public CreateWorkflowRequest(String companyReference, ConditionNode rootConditionNode) {
        this.companyReference = companyReference;
        this.rootConditionNode = rootConditionNode;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public ConditionNode getRootConditionNode() {
        return rootConditionNode;
    }
}
