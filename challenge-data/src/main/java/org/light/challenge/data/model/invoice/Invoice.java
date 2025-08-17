package org.light.challenge.data.model.invoice;

import org.light.challenge.data.model.workflow.ApprovalType;
import org.light.challenge.data.model.workflow.Department;
import org.springframework.lang.Nullable;

public class Invoice {

    private Long id;
    private Long companyId;
    private String invoiceReference;
    private long workflowId;
    private long amount;
    private Department department;
    @Nullable private ApprovalType approvalType;
    private InvoiceStatus status;

    public Invoice(String invoiceReference,
                   long companyId,
                   long workflowId,
                   long amount,
                   Department department,
                   @Nullable ApprovalType approvalType,
                   InvoiceStatus invoiceStatus) {
        this.invoiceReference = invoiceReference;
        this.companyId = companyId;
        this.workflowId = workflowId;
        this.amount = amount;
        this.department = department;
        this.approvalType = approvalType;
        this.status = invoiceStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(long workflowId) {
        this.workflowId = workflowId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Nullable
    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ApprovalType approvalType) {
        this.approvalType = approvalType;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
