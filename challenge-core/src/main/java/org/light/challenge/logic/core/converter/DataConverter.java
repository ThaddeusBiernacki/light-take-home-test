package org.light.challenge.logic.core.converter;

import org.light.challenge.data.model.workflow.*;
import org.light.challenge.logic.core.api.model.Invoice;
import org.light.challenge.logic.core.api.model.InvoiceStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataConverter {

    public ConditionNode convertConditionNode(org.light.challenge.logic.core.api.model.ConditionNode conditionNode) {
        Condition condition = convertCondition(conditionNode.getCondition());
        WorkflowNode onFailure = convertWorkflowNode(conditionNode.getOnFailure());
        WorkflowNode onSuccess = convertWorkflowNode(conditionNode.getOnSuccess());
        return new ConditionNode(condition, onFailure, onSuccess);
    }

    private WorkflowNode convertWorkflowNode(org.light.challenge.logic.core.api.model.WorkflowNode apiNode) {
        if (apiNode == null) {
            return null;
        }
        if (apiNode instanceof org.light.challenge.logic.core.api.model.ActionNode a) {
            return new ActionNode(convertActionType(a.getActionType()), a.getContact());
        }
        if (apiNode instanceof org.light.challenge.logic.core.api.model.ConditionNode c) {
            return convertConditionNode(c);
        }
        throw new IllegalArgumentException("Unsupported API node type: " + apiNode.getClass());
    }

    private Condition convertCondition(org.light.challenge.logic.core.api.model.Condition apiCond) {
        if (apiCond instanceof org.light.challenge.logic.core.api.model.AmountCondition a) {
            return new AmountCondition(convertAmountOperand(a.getOperand()), a.getThreshold());
        }
        if (apiCond instanceof org.light.challenge.logic.core.api.model.DepartmentCondition d) {
            return new DepartmentCondition(convertDepartment(d.getDepartment()));
        }
        if (apiCond instanceof org.light.challenge.logic.core.api.model.ApprovalCondition a) {
            return new ApprovalCondition(convertApprovalType(a.getApprovalType()));
        }
        throw new IllegalArgumentException("Unsupported API condition: " + apiCond.getClass());
    }

    public ActionType convertActionType(org.light.challenge.logic.core.api.model.ActionType actionType) {
        return switch (actionType) {
            case SLACK -> ActionType.SLACK;
            case EMAIL -> ActionType.EMAIL;
        };
    }

    private AmountOperand convertAmountOperand(org.light.challenge.logic.core.api.model.AmountOperand amountOperand) {
        return switch (amountOperand) {
            case LESS_THAN -> AmountOperand.LESS_THAN;
            case GREATER_THAN -> AmountOperand.GREATER_THAN;
        };
    }

    @Nullable
    public ApprovalType convertApprovalType(@Nullable org.light.challenge.logic.core.api.model.ApprovalType approvalType) {
        if (approvalType == null) {
            return null;
        }
        return switch (approvalType) {
            case MANAGER -> ApprovalType.MANAGER;
        };
    }

    @Nullable
    public org.light.challenge.logic.core.api.model.ApprovalType convertApprovalType(@Nullable ApprovalType approvalType) {
        if (approvalType == null) {
            return null;
        }
        return switch (approvalType) {
            case MANAGER -> org.light.challenge.logic.core.api.model.ApprovalType.MANAGER;
        };
    }

    public Department convertDepartment(org.light.challenge.logic.core.api.model.Department department) {
        return switch (department) {
            case FINANCE -> Department.FINANCE;
            case MARKETING -> Department.MARKETING;
        };
    }

    public org.light.challenge.logic.core.api.model.Department convertDepartment(Department department) {
        return switch (department) {
            case FINANCE -> org.light.challenge.logic.core.api.model.Department.FINANCE;
            case MARKETING -> org.light.challenge.logic.core.api.model.Department.MARKETING;
        };
    }

    public List<Invoice> convertInvoices(List<org.light.challenge.data.model.invoice.Invoice> invoices) {
        return invoices.stream()
                .map(invoice -> new Invoice(
                        invoice.getInvoiceReference(),
                        invoice.getAmount(),
                        convertDepartment(invoice.getDepartment()),
                        convertApprovalType(invoice.getApprovalType()),
                        convertInvoiceStatus(invoice.getStatus())))
                .toList();
    }

    public InvoiceStatus convertInvoiceStatus(org.light.challenge.data.model.invoice.InvoiceStatus status) {
        return switch (status) {
            case PENDING -> InvoiceStatus.PENDING;
            case APPROVED -> InvoiceStatus.APPROVED;
            case REJECTED -> InvoiceStatus.REJECTED;
        };
    }

    public org.light.challenge.data.model.invoice.InvoiceStatus convertInvoiceStatus(InvoiceStatus status) {
        return switch (status) {
            case PENDING -> org.light.challenge.data.model.invoice.InvoiceStatus.PENDING;
            case APPROVED -> org.light.challenge.data.model.invoice.InvoiceStatus.APPROVED;
            case REJECTED -> org.light.challenge.data.model.invoice.InvoiceStatus.REJECTED;
        };
    }
}
