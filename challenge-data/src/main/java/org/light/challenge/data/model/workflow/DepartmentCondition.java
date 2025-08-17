package org.light.challenge.data.model.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.light.challenge.data.model.invoice.Invoice;

public final class DepartmentCondition implements Condition {

    private final Department department;

    @JsonCreator
    public DepartmentCondition(@JsonProperty("department") Department department) {
        this.department = department;
    }

    @Override
    public boolean evaluate(Invoice invoice) {
        return invoice.getDepartment() == department;
    }
}