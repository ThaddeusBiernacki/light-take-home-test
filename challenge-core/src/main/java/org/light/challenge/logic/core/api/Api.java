package org.light.challenge.logic.core.api;

import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.logic.core.WorkflowDataService;
import org.light.challenge.logic.core.WorkflowService;
import org.light.challenge.logic.core.api.request.*;
import org.light.challenge.logic.core.api.response.CreateCompanyResponse;
import org.light.challenge.logic.core.api.response.GetCompanyResponse;
import org.light.challenge.data.model.Company;
import org.light.challenge.logic.core.api.response.GetPendingInvoicesResponse;
import org.light.challenge.logic.core.api.response.ProcessInvoiceResponse;
import org.light.challenge.logic.core.converter.DataConverter;
import org.light.challenge.logic.core.engine.WorkflowResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Api {

    private final WorkflowDataService workflowDataService;
    private final WorkflowService workflowService;
    private final DataConverter dataConverter;

    public Api(WorkflowDataService workflowDataService,
               WorkflowService workflowService,
               DataConverter dataConverter) {
        this.workflowDataService = workflowDataService;
        this.workflowService = workflowService;
        this.dataConverter = dataConverter;
    }

    public CreateCompanyResponse createCompany(CreateCompanyRequest request) {
        String companyReference = workflowDataService.persistCompany(request.getCompanyName());
        return new CreateCompanyResponse(companyReference);
    }

    public GetCompanyResponse getCompany(GetCompanyRequest request) {
        Company company = workflowDataService.getCompany(request.getCompanyReference());
        return new GetCompanyResponse(company.getCompanyName(), company.getCompanyReference());
    }

    public void createWorkflow(CreateWorkflowRequest request) {
        workflowDataService.persistWorkflow(request.getCompanyReference(), dataConverter.convertConditionNode(request.getRootConditionNode()));
    }

    public ProcessInvoiceResponse processInvoice(ProcessInvoiceRequest request) {
        WorkflowResult result = workflowService.processInvoice(
                request.getInvoice().getInvoiceReference(),
                request.getCompanyReference(),
                request.getInvoice().getAmount(),
                dataConverter.convertDepartment(request.getInvoice().getDepartment()),
                dataConverter.convertApprovalType(request.getInvoice().getApprovalType()));
        return new ProcessInvoiceResponse(
                result.getResult(),
                result.getInvoiceReference()
        );
    }

    public GetPendingInvoicesResponse getPendingInvoices(GetPendingInvoicesRequest request) {
        List<Invoice> pendingInvoices = workflowDataService.getPendingInvoices(request.getCompanyReference());
        return new GetPendingInvoicesResponse(dataConverter.convertInvoices(pendingInvoices));
    }

    public void reviewInvoice(ReviewInvoiceRequest request) {
        workflowDataService.reviewInvoice(
                request.getInvoiceReference(),
                dataConverter.convertInvoiceStatus(request.getInvoiceStatus()),
                dataConverter.convertActionType(request.getActionType()));
    }
}