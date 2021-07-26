package com.plume.code.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class},
        scanBasePackages = {"com.plume.code"}
)
@Slf4j
@EnableScheduling
public class PlumeCodeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlumeCodeWebApplication.class, args);
    }
}
