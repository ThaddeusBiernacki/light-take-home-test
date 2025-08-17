package org.light.challenge.logic.core.api.request;


import org.light.challenge.logic.core.api.model.Invoice;

public class ProcessInvoiceRequest {

    private final String companyReference;
    private final Invoice invoice;

    public ProcessInvoiceRequest(String companyReference, Invoice invoice) {
        this.companyReference = companyReference;
        this.invoice = invoice;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
