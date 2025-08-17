package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "nodeType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ActionNode.class, name = "action"),
    @JsonSubTypes.Type(value = ConditionNode.class, name = "condition")
})
public interface WorkflowNode {
}
