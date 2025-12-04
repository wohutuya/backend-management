package com.hutuya.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.hutuya.project.mapper")
@EnableAsync
public class BackendManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendManagementApplication.class, args);
    }

}
