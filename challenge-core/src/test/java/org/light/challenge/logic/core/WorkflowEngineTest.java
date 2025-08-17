package org.light.challenge.logic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.data.model.workflow.*;
import org.light.challenge.logic.core.engine.WorkflowEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowEngineTest {

    private WorkflowEngine engine;
    private WorkflowNode root;

    @BeforeEach
    void setUp() {
        // Engine under test
        engine = new WorkflowEngine();

        ActionNode financeTeamMemberSlack = new ActionNode(ActionType.SLACK, "finance team member");
        ActionNode financeManagerEmail = new ActionNode(ActionType.EMAIL, "finance manager");
        ActionNode cfoSlack = new ActionNode(ActionType.SLACK, "CFO");
        ActionNode cmoEmail = new ActionNode(ActionType.EMAIL, "CMO");
        //Right branches
        ConditionNode marketingCondition = new ConditionNode(new DepartmentCondition(Department.MARKETING), cfoSlack, cmoEmail);
        //Left branches
        ConditionNode managerApproval = new ConditionNode(new ApprovalCondition(ApprovalType.MANAGER), financeTeamMemberSlack, financeManagerEmail);
        ConditionNode invoiceGt5k = new ConditionNode(new AmountCondition(AmountOperand.GREATER_THAN, 5000L), financeTeamMemberSlack, managerApproval);
        root = new ConditionNode(new AmountCondition(AmountOperand.GREATER_THAN, 10000L), invoiceGt5k, marketingCondition);
    }

    @Test
    void over10k_marketing_goesToEmailCMO() {
        Invoice inv = new Invoice("INV-1001", 1L, 1L, 12_000L, Department.MARKETING, ApprovalType.MANAGER, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-1001", result.getInvoiceReference());
        assertTrue(result.getResult().contains("email"), "Expected email action");
        assertTrue(result.getResult().contains("CMO"), "Expected CMO recipient");
    }

    @Test
    void over10k_finance_goesToSlackCFO() {
        Invoice inv = new Invoice("INV-1002", 1L, 1L, 20_000L, Department.FINANCE, ApprovalType.MANAGER, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-1002", result.getInvoiceReference());
        assertTrue(result.getResult().contains("Slack"), "Expected Slack action");
        assertTrue(result.getResult().contains("CFO"), "Expected CFO recipient");
    }

    @Test
    void midRange_finance_withManagerApproval_goesToEmailFinanceManager() {
        Invoice inv = new Invoice("INV-2002", 1L, 1L, 8_000L, Department.FINANCE, ApprovalType.MANAGER, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-2002", result.getInvoiceReference());
        assertTrue(result.getResult().contains("email"), "Expected email action");
        assertTrue(result.getResult().contains("finance manager"), "Expected Finance Manager recipient");
    }

    @Test
    void midRange_finance_withoutManagerApproval_goesToSlackFinanceTeam() {
        Invoice inv = new Invoice("INV-2003", 1L, 1L, 9_000L, Department.FINANCE, null, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-2003", result.getInvoiceReference());
        assertTrue(result.getResult().contains("Slack"), "Expected Slack action");
        assertTrue(result.getResult().contains("finance team"), "Expected finance team recipient");
    }

    @Test
    void midRange_marketing_withManagerApproval_goesToEmailFinanceManager() {
        Invoice inv = new Invoice("INV-2002", 1L, 1L, 8_000L, Department.MARKETING, ApprovalType.MANAGER, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-2002", result.getInvoiceReference());
        assertTrue(result.getResult().contains("email"), "Expected email action");
        assertTrue(result.getResult().contains("finance manager"), "Expected Finance Manager recipient");
    }

    @Test
    void midRange_marketing_withoutManagerApproval_goesToSlackFinanceTeam() {
        Invoice inv = new Invoice("INV-2003", 1L, 1L, 9_000L, Department.MARKETING, null, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-2003", result.getInvoiceReference());
        assertTrue(result.getResult().contains("Slack"), "Expected Slack action");
        assertTrue(result.getResult().contains("finance team"), "Expected finance team recipient");
    }

    @Test
    void under5k_marketing_goesToSlackFinanceTeam() {
        Invoice inv = new Invoice("INV-3001", 1L, 1L, 3_000L, Department.MARKETING, null, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-3001", result.getInvoiceReference());
        assertTrue(result.getResult().contains("Slack"), "Expected Slack action");
        assertTrue(result.getResult().contains("finance team"), "Expected finance team recipient");
    }

    @Test
    void under5k_finance_goesToSlackFinanceTeam() {
        Invoice inv = new Invoice("INV-3001", 1L, 1L, 3_000L, Department.FINANCE, null, null);

        var result = engine.execute(root, inv);

        assertEquals("INV-3001", result.getInvoiceReference());
        assertTrue(result.getResult().contains("Slack"), "Expected Slack action");
        assertTrue(result.getResult().contains("finance team"), "Expected finance team recipient");
    }
}
