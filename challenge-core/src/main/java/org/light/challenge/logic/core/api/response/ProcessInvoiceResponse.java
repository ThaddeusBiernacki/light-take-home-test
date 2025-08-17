package org.light.challenge.logic.core.api.response;

public class ProcessInvoiceResponse {

    private final String result;
    private final String invoiceReference;

    public ProcessInvoiceResponse(String result, String invoiceReference) {
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
