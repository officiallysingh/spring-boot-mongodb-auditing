package com.ksoot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info =
        @Info(
                title = "CRUD API",
                version = "0.0.1",
                description = "Spring Data MongoDB Auditing and Text search API"))
public class SpringBootMongodbAuditingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootMongodbAuditingApplication.class, args);
    }
}
