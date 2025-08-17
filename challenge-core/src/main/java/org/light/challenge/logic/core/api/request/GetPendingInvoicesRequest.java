package org.light.challenge.logic.core.api.request;

public class GetPendingInvoicesRequest {

    private final String companyReference;

    public GetPendingInvoicesRequest(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyReference() {
        return companyReference;
    }
}
