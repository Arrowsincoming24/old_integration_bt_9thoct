package com.bank.fdsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FdSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdSimulatorApplication.class, args);
    }
}
