package com.personalsandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.personalsandbox")
public class PersonalSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalSandboxApplication.class, args);
    }
}
