package com.gdelboni.vittadiet;

import org.springframework.boot.SpringApplication;

public class TestVittaDietApplication {

    public static void main(String[] args) {
        SpringApplication.from(VittaDietApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
