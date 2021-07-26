package com.plume.code.cli;

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
public class PlumeCodeCliApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlumeCodeCliApplication.class, args);
    }
}
