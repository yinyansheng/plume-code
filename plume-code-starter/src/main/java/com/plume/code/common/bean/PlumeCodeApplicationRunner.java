package com.plume.code.common.bean;

import com.plume.code.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zxh
 * 项目启动类
 */
@Component
public class PlumeCodeApplicationRunner implements ApplicationRunner {

    @Autowired
    protected GeneratorService generatorService;

    @Override
    public void run(ApplicationArguments args) {
        generatorService.clearFile();
    }

}

