CREATE TABLE company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(255) NOT NULL,
    company_reference VARCHAR(64) NOT NULL
);

CREATE TABLE workflow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id BIGINT NOT NULL,
    version INT NOT NULL,
    workflow JSON NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE invoice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id BIGINT NOT NULL,
    invoice_reference VARCHAR(64) NOT NULL UNIQUE,
    workflow_id BIGINT NOT NULL,
    amount BIGINT NOT NULL,
    department VARCHAR(32) NOT NULL,
    approval_type VARCHAR(32),
    status VARCHAR(32) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (workflow_id) REFERENCES workflow(id)
);
