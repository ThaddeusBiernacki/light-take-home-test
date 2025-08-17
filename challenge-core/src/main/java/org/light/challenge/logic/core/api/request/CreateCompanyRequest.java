package org.light.challenge.logic.core.api.request;

public class CreateCompanyRequest {

    private final String companyName;

    public CreateCompanyRequest(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
