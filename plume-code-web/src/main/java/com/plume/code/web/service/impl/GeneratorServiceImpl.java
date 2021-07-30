package com.plume.code.web.service.impl;

import cn.hutool.core.io.FileUtil;
import com.plume.code.core.common.helper.PathHelper;
import com.plume.code.web.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public void clear() {
        String downloadPath = PathHelper.getDownloadPath();
        log.info("[历史数据清空]清空文件下载目录开始");
        File downloadDir = new File(downloadPath);
        File[] files = downloadDir.listFiles();

        if (null == files || files.length == 0) {
            return;
        }

        for (File file : files) {
            FileUtil.del(file);
        }
        log.info("[历史数据清空]目录已清空,目录: {}", downloadPath);
    }

}
