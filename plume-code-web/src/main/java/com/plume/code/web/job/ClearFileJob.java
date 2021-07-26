package com.plume.code.web.job;

import com.plume.code.web.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zxh
 */
@Component
@Slf4j
public class ClearFileJob {

    @Autowired
    protected GeneratorService generatorService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void clearHistoryFile() {
        generatorService.clear();
    }
}
