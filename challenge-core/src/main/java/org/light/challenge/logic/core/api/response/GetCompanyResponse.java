package org.light.challenge.logic.core.api.response;

public class GetCompanyResponse {

    private final String companyName;
    private final String companyReference;

    public GetCompanyResponse(String companyName, String companyReference) {
        this.companyName = companyName;
        this.companyReference = companyReference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyReference() {
        return companyReference;
    }
}
