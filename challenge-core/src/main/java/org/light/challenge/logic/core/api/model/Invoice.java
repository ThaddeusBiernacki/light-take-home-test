package org.light.challenge.logic.core.api.model;

import org.springframework.lang.Nullable;

public class Invoice {

    private final String invoiceReference;
    private final long amount;
    private final Department department;
    @Nullable private final ApprovalType approvalType;
    private final InvoiceStatus invoiceStatus;

    public Invoice(String invoiceReference,
                   long amount,
                   Department department,
                   @Nullable ApprovalType approvalType,
                   InvoiceStatus invoiceStatus) {
        this.invoiceReference = invoiceReference;
        this.amount = amount;
        this.department = department;
        this.approvalType = approvalType;
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public long getAmount() {
        return amount;
    }

    public Department getDepartment() {
        return department;
    }

    @Nullable
    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }
}
