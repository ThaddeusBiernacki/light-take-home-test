package org.light.challenge.logic.core.api.request;

import org.light.challenge.logic.core.api.model.ActionType;
import org.light.challenge.logic.core.api.model.InvoiceStatus;

public class ReviewInvoiceRequest {

    private final String invoiceReference;
    private final InvoiceStatus invoiceStatus;
    private final ActionType actionType;

    public ReviewInvoiceRequest(String invoiceReference, InvoiceStatus invoiceStatus, ActionType actionType) {
        this.invoiceReference = invoiceReference;
        this.invoiceStatus = invoiceStatus;
        this.actionType = actionType;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
