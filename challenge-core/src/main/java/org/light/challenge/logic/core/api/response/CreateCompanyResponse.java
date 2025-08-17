package org.light.challenge.logic.core.api.response;

public class CreateCompanyResponse {

    private final String companyReference;

    public CreateCompanyResponse(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyReference() {
        return companyReference;
    }
}
