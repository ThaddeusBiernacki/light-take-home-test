package org.light.challenge.logic.core.engine;

import org.light.challenge.data.model.invoice.Invoice;
import org.light.challenge.data.model.workflow.ActionNode;
import org.light.challenge.data.model.workflow.ConditionNode;
import org.light.challenge.data.model.workflow.WorkflowNode;
import org.springframework.stereotype.Component;

@Component
public final class WorkflowEngine {

    public WorkflowResult execute(WorkflowNode root, Invoice invoice) {
        WorkflowNode node = root;
        while (true) {
            if (node == null) {
                throw new IllegalStateException("Workflow invalid, reached null branch");
            }

            if (node instanceof ActionNode action) {
                String contact = action.getContact();
                String result = "";
                switch (action.getActionType()) {
                    case EMAIL -> {
                        result = "Sending approval request via email to " + contact;
                    }
                    case SLACK -> {
                        result = "Sending approval request via Slack to " + contact;
                    }
                }
                return new WorkflowResult(result, invoice.getInvoiceReference());
            }

            if (node instanceof ConditionNode cond) {
                boolean conditionMet = cond.getCondition().evaluate(invoice);
                node = conditionMet ? cond.getOnSuccess() : cond.getOnFailure();
                continue;
            }

            throw new IllegalStateException("Unsupported node type: " + node.getClass());
        }
    }
}
