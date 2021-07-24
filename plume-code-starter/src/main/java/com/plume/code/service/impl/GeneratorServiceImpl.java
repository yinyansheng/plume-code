package com.plume.code.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.plume.code.common.helper.PathHelper;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import com.plume.code.lib.generator.GeneratorBehaviorFactory;
import com.plume.code.service.GeneratorService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratorServiceImpl implements GeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorServiceImpl.class);
    @Autowired
    protected DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    protected GeneratorBehaviorFactory generatorBehaviorFactory;

    @SneakyThrows
    @Override
    public ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel) {
        List<GeneratorBehavior> generatorBehaviorList = getGeneratorBehaviorList(connectionModel, settingModel);

        if (CollectionUtils.isEmpty(generatorBehaviorList)) {
            throw new RuntimeException("generatorBehaviorList is empty");
        }

        generatorBehaviorList.forEach(GeneratorBehavior::generate);

        String downloadPath = PathHelper.getDownloadPath();
        String directoryPath = downloadPath.concat(settingModel.getBatchNo());

        if (!(new File(directoryPath).exists())) {
            throw new FileNotFoundException(directoryPath);
        }

        logger.info("[plume-code] 文件生成完毕 目录:{}", directoryPath);
        File zip = ZipUtil.zip(directoryPath);
        logger.info("[plume-code] 文件zip生成 路径:{}", zip.getAbsolutePath());

        return ResultModel.builder()
                .batchNo(settingModel.getBatchNo())
                .directoryPath(directoryPath)
                .zipPath(zip.getPath())
                .build();
    }

    @Override
    public List<GeneratorBehavior> getGeneratorBehaviorList(ConnectionModel connectionModel, SettingModel settingModel) {
        List<ContextModel> contextModelList = getContextModelList(connectionModel, settingModel);

        return contextModelList.stream().map(contextModel ->
                generatorBehaviorFactory.getGeneratorBehaviorList(contextModel)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<ContextModel> getContextModelList(ConnectionModel connectionModel, SettingModel settingModel) {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel);
        if (CollectionUtils.isEmpty(settingModel.getTableNameSet())) {
            return Collections.emptyList();
        }

        List<ClassModel> classModels = databaseBehavior.listClassModel(settingModel);
        List<ContextModel> contextModels = new ArrayList<>();

        for (ClassModel classModel : classModels) {
            if (!settingModel.getTableNameSet().contains(classModel.getTableName())) {
                continue;
            }

            List<FieldModel> fieldModels = databaseBehavior.listFieldModel(settingModel, classModel.getTableName());

            ContextModel contextModel = ContextModel.builder()
                    .connectionModel(connectionModel)
                    .settingModel(settingModel)
                    .classModel(classModel)
                    .fieldModelList(fieldModels)
                    .build();

            contextModels.add(contextModel);
        }

        return contextModels;
    }

    @Override
    public void clearFile() {
        String downloadPath = PathHelper.getDownloadPath();
        logger.info("[历史数据清空]清空文件下载目录开始");
        File downloadDir = new File(downloadPath);
        File[] files = downloadDir.listFiles();

        if (null == files || files.length == 0) {
            return;
        }

        for (File file : files) {
            FileUtil.del(file);
        }
        logger.info("[历史数据清空]目录已清空,目录: {}", downloadPath);
    }

}
