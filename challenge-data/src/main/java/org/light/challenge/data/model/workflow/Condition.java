package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.light.challenge.data.model.invoice.Invoice;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "conditionType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AmountCondition.class, name = "amount"),
        @JsonSubTypes.Type(value = DepartmentCondition.class, name = "department"),
        @JsonSubTypes.Type(value = ApprovalCondition.class, name = "approval")
})
public interface Condition {
    boolean evaluate(Invoice invoice);
}
