package org.light.challenge.logic.core.engine;

public class WorkflowResult {

    private final String result;
    private final String invoiceReference;

    public WorkflowResult(String result,
                          String invoiceReference) {
        this.result = result;
        this.invoiceReference = invoiceReference;
    }

    public String getResult() {
        return result;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }
}
