package com.plume.code.common.bean;

import cn.hutool.core.io.FileUtil;
import com.plume.code.common.helper.PathHelper;
import com.plume.code.service.impl.GeneratorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) {
        try {
            String downloadPath = PathHelper.getDownloadPath();
//        FileUtil.del(downloadPath);//可以用  但会把download删除
            logger.info("[历史数据清空] 清空文件下载目录开始");
            File downloadDir = new File(downloadPath);
            File[] files = downloadDir.listFiles();
            for (File file : files) {
                FileUtil.del(file);
            }
            logger.info("[历史数据清空] 目录已清空   目录: {}", downloadPath);
        } catch (Exception e) {
            logger.error("[历史数据清空] 异常:{}", e.getMessage());
        }

    }

}
