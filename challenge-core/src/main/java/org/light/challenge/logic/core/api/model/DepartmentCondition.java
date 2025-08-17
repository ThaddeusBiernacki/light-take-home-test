package org.light.challenge.logic.core.api.model;

public class DepartmentCondition implements Condition {

    private final Department department;

    public DepartmentCondition(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}
