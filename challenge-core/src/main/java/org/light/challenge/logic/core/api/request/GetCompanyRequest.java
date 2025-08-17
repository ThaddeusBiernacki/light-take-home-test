package org.light.challenge.logic.core.api.request;

public class GetCompanyRequest {

    private final String companyReference;

    public GetCompanyRequest(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyReference() {
        return companyReference;
    }
}
