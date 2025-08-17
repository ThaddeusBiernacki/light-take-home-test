package org.light.challenge.data.model;

public class Company {

    private Long id;
    private String companyName;
    private String companyReference;

    public Company(String companyName, String companyReference) {
        this.companyName = companyName;
        this.companyReference = companyReference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }
}
