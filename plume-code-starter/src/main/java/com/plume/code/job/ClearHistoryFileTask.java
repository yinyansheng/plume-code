package com.plume.code.job;

import cn.hutool.core.io.FileUtil;
import com.plume.code.common.bean.ApplicationRunnerImpl;
import com.plume.code.common.helper.PathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ClearHistoryFileTask {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    /**
     * 清空历史文件 占机器资源、文件多也不方便查找
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void clearHistoryFile() {
        logger.info("[定时任务][历史数据清空] 清空下载目录开始");
        String downloadPath = PathHelper.getDownloadPath();
        File downloadDir = new File(downloadPath);
        File[] files = downloadDir.listFiles();
        for (File file : files) {
            FileUtil.del(file);
        }
        logger.info("[定时任务][历史数据清空] 目录已清空   目录: {}", downloadPath);
    }
}
