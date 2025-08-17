package org.light.challenge.logic.core;

import org.light.challenge.data.model.Company;
import org.light.challenge.data.WorkflowRepository;
import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.data.model.invoice.InvoiceStatus;
import org.light.challenge.data.model.workflow.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WorkflowDataService {

    private final WorkflowRepository workflowRepository;

    public WorkflowDataService(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public String persistCompany(String companyName) {
        // In the real world, it'd be nice to have a specific reference generator
        String uniqueReference = UUID.randomUUID().toString();
        Company company = new Company(companyName, uniqueReference);
        workflowRepository.storeCompany(company);
        return uniqueReference;
    }

    public Company getCompany(String companyReference) {
        return workflowRepository.getCompany(companyReference);
    }

    public void persistWorkflow(String companyReference, ConditionNode headNode) {
        Workflow latestWorkflow = workflowRepository.getLatestWorkflow(companyReference);
        int version = latestWorkflow != null ? latestWorkflow.getVersion() + 1 : 1;
        Company company = workflowRepository.getCompany(companyReference);
        Workflow newWorkflow = new Workflow(company.getId(), company.getCompanyReference(), version, headNode);
        workflowRepository.storeWorkflow(newWorkflow);
    }

    @Nullable
    public Workflow getLatestWorkflow(String companyReference) {
        return workflowRepository.getLatestWorkflow(companyReference);
    }

    public Invoice persistInvoice(String invoiceReference,
                                  Company company,
                                  long workflowId,
                                  long amount,
                                  Department department,
                                  @Nullable ApprovalType approvalType) {
        return workflowRepository.storeInvoice(invoiceReference, workflowId, company.getId(), amount, department, approvalType);
    }

    public List<Invoice> getPendingInvoices(String companyReference) {
        return workflowRepository.getPendingInvoices(companyReference);
    }

    public void reviewInvoice(String invoiceReference, InvoiceStatus invoiceStatus, ActionType actionType) {
        Invoice invoice = workflowRepository.getInvoiceByReference(invoiceReference);
        System.out.println("\nSending review for invoice: " + invoiceReference +
                " with status: " + invoiceStatus.name().toLowerCase() + ", via: " + actionType.name().toLowerCase());
        workflowRepository.updateInvoiceStatus(invoice, invoiceStatus);
    }
}
