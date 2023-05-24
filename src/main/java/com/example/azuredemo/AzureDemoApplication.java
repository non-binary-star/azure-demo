package com.example.azuredemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AzureDemoApplication implements CommandLineRunner {

    @Value("${spring.cloud.azure.eventhubs.namespace}")
    private String namespace;

    @Value("${spring.cloud.azure.eventhubs.enabled}")
    private Boolean enabled;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(AzureDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
    }
}
