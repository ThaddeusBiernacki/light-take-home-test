package org.light.challenge.app;

import org.light.challenge.logic.core.api.Api;
import org.light.challenge.logic.core.api.model.*;
import org.light.challenge.logic.core.api.request.*;
import org.light.challenge.logic.core.api.response.GetCompanyResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ApplicationRunner {

    private final Api api;

    public ApplicationRunner(Api api) {
        this.api = api;
    }

    public void runApplication(String[] args) {
        //Create one test company and workflow for the scenario given in the specs
        String companyReference = api.createCompany(new CreateCompanyRequest("TestCompany")).getCompanyReference();
        GetCompanyResponse company = api.getCompany(new GetCompanyRequest(companyReference));
        api.createWorkflow(createWorkflowRequest(company.getCompanyReference()));
        System.out.println("=== Invoice Workflow Console ===");
        System.out.println("Company: " + company.getCompanyReference());
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nCommands:");
                System.out.println("  1) Create & process invoice");
                System.out.println("  2) Show pending invoices");
                System.out.println("  3) Review a pending invoice");
                System.out.println("  4) Exit");
                System.out.print("\nSelect option (1-4): ");
                String choice = in.nextLine().trim();
                switch (choice) {
                    case "1" -> handleCreateAndProcessInvoice(in, company.getCompanyReference());
                    case "2" -> handleShowPendingInvoices(company.getCompanyReference());
                    case "3" -> handleApproveOrRejectInvoice(in);
                    case "4" -> {
                        System.out.println("Bye");
                        return;
                    }
                    default -> System.out.println("Unknown option. Please choose 1, 2, 3, or 4.");
                }
            }
        }
    }

    private void handleCreateAndProcessInvoice(Scanner in, String companyRef) {
        long amount = askAmount(in);
        Department dept = askDepartment(in);
        ApprovalType approval = askApproval(in);
        Invoice invoice = new Invoice(UUID.randomUUID().toString(),amount, dept, approval, InvoiceStatus.PENDING);
        String result = api.processInvoice(new ProcessInvoiceRequest(companyRef, invoice)).getResult();
        System.out.println("\n➡ " + result);
    }

    private void handleShowPendingInvoices(String companyReference) {
        List<Invoice> pending = api.getPendingInvoices(new GetPendingInvoicesRequest(companyReference)).getInvoices();
        if (pending.isEmpty()) {
            System.out.println("No pending invoices.");
            return;
        }
        System.out.println("\n— Pending invoices —");
        for (Invoice inv : pending) {
            System.out.printf(" • %s | amount=%d | dept=%s | approval=%s | status=%s%n",
                    inv.getInvoiceReference(), inv.getAmount(), inv.getDepartment(),
                    inv.getApprovalType(), inv.getInvoiceStatus().name());
        }
    }

    private void handleApproveOrRejectInvoice(Scanner in) {
        System.out.print("Enter invoice reference: ");
        String ref = in.nextLine().trim();
        if (ref.isEmpty()) {
            System.out.println("Invoice reference required.");
            return;
        }
        InvoiceStatus decision = askApprovalDecision(in);
        ActionType actionType = askReviewMethod(in);
        api.reviewInvoice(new ReviewInvoiceRequest(ref, decision, actionType));
    }

    private InvoiceStatus askApprovalDecision(Scanner in) {
        while (true) {
            System.out.print("Approve or Reject? (a/r): ");
            String s = in.nextLine().trim().toLowerCase();
            if (s.equals("a") || s.equals("approve")) return InvoiceStatus.APPROVED;
            if (s.equals("r") || s.equals("reject")) return InvoiceStatus.REJECTED;
            System.out.println("Please type 'a' for approve or 'r' for reject.");
        }
    }

    private ActionType askReviewMethod(Scanner in) {
        while (true) {
            System.out.print("Review method? (slack/email): ");
            String s = in.nextLine().trim().toLowerCase();
            if (s.equals("slack")) return ActionType.SLACK;
            if (s.equals("email")) return ActionType.EMAIL;
            System.out.println("Please type 'slack' or 'email'.");
        }
    }

    private long askAmount(Scanner in) {
        while (true) {
            System.out.print("Amount (integer, e.g. 12000): ");
            String s = in.nextLine().trim().replace("_", "");
            try {
                long val = Long.parseLong(s);
                if (val < 0) {
                    System.out.println("Amount must be ≥ 0.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer amount.");
            }
        }
    }

    private Department askDepartment(Scanner in) {
        while (true) {
            System.out.print("Department (FINANCE/MARKETING): ");
            String s = in.nextLine().trim().toUpperCase();
            try {
                return Department.valueOf(s);
            } catch (IllegalArgumentException e) {
                System.out.println("Valid values: FINANCE or MARKETING.");
            }
        }
    }

    @Nullable
    private ApprovalType askApproval(Scanner in) {
        while (true) {
            System.out.print("Manager approval? (y/n): ");
            String s = in.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return ApprovalType.MANAGER;
            if (s.equals("n") || s.equals("no")) return null;
            System.out.println("Please type 'y' or 'n'.");
        }
    }

    private CreateWorkflowRequest createWorkflowRequest(String companyReference) {
        ActionNode financeTeamMemberSlack = new ActionNode(ActionType.SLACK, "finance team member");
        ActionNode financeManagerEmail = new ActionNode(ActionType.EMAIL, "finance manager");
        ActionNode cfoSlack = new ActionNode(ActionType.SLACK, "CFO");
        ActionNode cmoEmail = new ActionNode(ActionType.EMAIL, "CMO");
        //Right branches
        ConditionNode marketingCondition = new ConditionNode(new DepartmentCondition(Department.MARKETING), cfoSlack, cmoEmail);
        //Left branches
        ConditionNode managerApproval = new ConditionNode(new ApprovalCondition(ApprovalType.MANAGER), financeTeamMemberSlack, financeManagerEmail);
        ConditionNode invoiceGt5k = new ConditionNode(new AmountCondition(AmountOperand.GREATER_THAN, 5000L), financeTeamMemberSlack, managerApproval);
        ConditionNode root = new ConditionNode(new AmountCondition(AmountOperand.GREATER_THAN, 10000L), invoiceGt5k, marketingCondition);
        return new CreateWorkflowRequest(companyReference, root);
    }
}
