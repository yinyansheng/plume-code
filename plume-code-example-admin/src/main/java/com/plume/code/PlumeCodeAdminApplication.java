package com.plume.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.plume.code.mapper")
public class PlumeCodeAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlumeCodeAdminApplication.class, args);
    }
}
