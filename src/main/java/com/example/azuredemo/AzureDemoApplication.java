package com.example.azuredemo;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzureDemoApplication {

    public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(AzureDemoApplication.class, args);
    }
}
