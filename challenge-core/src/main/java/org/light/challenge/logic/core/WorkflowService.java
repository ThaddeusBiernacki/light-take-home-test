package org.light.challenge.logic.core;

import org.light.challenge.data.model.Company;
import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.data.model.workflow.ApprovalType;
import org.light.challenge.data.model.workflow.Department;
import org.light.challenge.data.model.workflow.Workflow;
import org.light.challenge.logic.core.engine.WorkflowEngine;
import org.light.challenge.logic.core.engine.WorkflowResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class WorkflowService {

    private final WorkflowDataService workflowDataService;
    private final WorkflowEngine workflowEngine;

    public WorkflowService(WorkflowDataService workflowDataService,
                           WorkflowEngine workflowEngine) {
        this.workflowDataService = workflowDataService;
        this.workflowEngine = workflowEngine;
    }

    public WorkflowResult processInvoice(String invoiceReference,
                                         String companyReference,
                                         long invoiceAmount,
                                         Department department,
                                         @Nullable ApprovalType approvalType) {
        Company company = workflowDataService.getCompany(companyReference);
        Workflow workflow = workflowDataService.getLatestWorkflow(company.getCompanyReference());
        if (workflow == null) {
            throw new IllegalStateException("No workflow found for company: " + company.getCompanyReference());
        }
        Invoice invoice = workflowDataService.persistInvoice(
                invoiceReference,
                company,
                workflow.getId(),
                invoiceAmount,
                department,
                approvalType
        );
        return workflowEngine.execute(workflow.getHeadConditionNode(), invoice);
    }
}
