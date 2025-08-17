package org.light.challenge.data;

import org.light.challenge.data.model.Company;
import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.data.model.invoice.InvoiceStatus;
import org.light.challenge.data.model.workflow.ApprovalType;
import org.light.challenge.data.model.workflow.Department;
import org.light.challenge.data.model.workflow.Workflow;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
//Ideally this would call the DB
public class WorkflowRepository {

    private static final List<Company> COMPANIES = new ArrayList<>();
    private static final List<Workflow> WORKFLOWS = new ArrayList<>();
    private static final List<Invoice> INVOICES = new ArrayList<>();

    public void storeCompany(Company company) {
        company.setId(COMPANIES.size() + 1L); // Simple ID generation for demo purposes
        COMPANIES.add(company);
    }

    public Company getCompany(String companyReference) {
        List<Company> companies = COMPANIES.stream().filter(c -> c.getCompanyReference().equals(companyReference)).toList();
        if (companies.size() > 1) {
            throw new IllegalStateException("More than one company found for specified company reference");
        }
        if (companies.isEmpty()) {
            throw new IllegalArgumentException("No Company with specified reference found");
        }
        return companies.get(0);
    }

    public void storeWorkflow(Workflow workflow) {
        workflow.setId(WORKFLOWS.size() + 1L); // Simple ID generation for demo purposes
        WORKFLOWS.add(workflow);
    }

    @Nullable
    public Workflow getLatestWorkflow(String companyReference) {
        List<Workflow> workflows = WORKFLOWS.stream().filter(w -> w.getCompanyReference().equals(companyReference))
                .sorted(Comparator.comparingInt(Workflow::getVersion).reversed()).toList();
        if (workflows.isEmpty()) {
            return null;
        }
        return workflows.get(0);
    }

    public Invoice storeInvoice(String invoiceReference,
                                long workflowId,
                                long companyId,
                                long amount,
                                Department department,
                                @Nullable ApprovalType approvalType) {
        Invoice invoice = new Invoice(
                invoiceReference,
                companyId,
                workflowId,
                amount,
                department,
                approvalType,
                InvoiceStatus.PENDING
        );
        invoice.setId(INVOICES.size() + 1L); // Simple ID generation for demo purposes
        INVOICES.add(invoice);
        return invoice;
    }

    public List<Invoice> getPendingInvoices(String companyReference) {
        Company company = getCompany(companyReference);
        return INVOICES.stream().filter(i -> i.getCompanyId().equals(company.getId())
                && i.getStatus() == InvoiceStatus.PENDING).toList();
    }

    public Invoice getInvoiceByReference(String invoiceReference) {
        return INVOICES.stream()
                .filter(i -> i.getInvoiceReference().equals(invoiceReference))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Invoice with specified reference found"));
    }

    public void updateInvoiceStatus(Invoice invoice, InvoiceStatus invoiceStatus) {
        //Nothing to do here really as it's been passed by reference, realistically this would be a DB update
        invoice.setStatus(invoiceStatus);
    }
}