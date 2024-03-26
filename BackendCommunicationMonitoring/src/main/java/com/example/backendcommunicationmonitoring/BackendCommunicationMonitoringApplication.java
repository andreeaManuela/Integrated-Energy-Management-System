package com.example.backendcommunicationmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.validation.annotation.Validated;

import java.util.TimeZone;

@SpringBootApplication
@Validated
public class BackendCommunicationMonitoringApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(BackendCommunicationMonitoringApplication.class);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(BackendCommunicationMonitoringApplication.class, args);
    }

}
