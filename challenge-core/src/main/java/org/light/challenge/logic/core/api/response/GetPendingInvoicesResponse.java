package org.light.challenge.logic.core.api.response;

import org.light.challenge.logic.core.api.model.Invoice;

import java.util.List;

public class GetPendingInvoicesResponse {

    private final List<Invoice> invoices;

    public GetPendingInvoicesResponse(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
}
