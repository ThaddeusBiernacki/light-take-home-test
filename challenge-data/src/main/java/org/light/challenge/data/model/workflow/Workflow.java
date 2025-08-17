package org.light.challenge.data.model.workflow;

public class Workflow {

    private Long id;
    private Long companyId;
    private String companyReference;
    private Integer version;
    private ConditionNode headConditionNode;

    public Workflow(long companyId, String companyReference, int version, ConditionNode headConditionNode) {
        this.companyId = companyId;
        this.companyReference = companyReference;
        this.version = version;
        this.headConditionNode = headConditionNode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ConditionNode getHeadConditionNode() {
        return headConditionNode;
    }

    public void setHeadConditionNode(ConditionNode headConditionNode) {
        this.headConditionNode = headConditionNode;
    }
}
