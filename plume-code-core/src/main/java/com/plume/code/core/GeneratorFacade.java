package com.plume.code.core;

import cn.hutool.core.util.ZipUtil;
import com.plume.code.core.common.helper.PathHelper;
import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.GeneratorConfigModel;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.DatabaseBehavior;
import com.plume.code.core.database.DatabaseBehaviorFactory;
import com.plume.code.core.database.model.ClassModel;
import com.plume.code.core.database.model.ContextModel;
import com.plume.code.core.database.model.FieldModel;
import com.plume.code.core.database.model.ResultModel;
import com.plume.code.core.generator.GeneratorBehavior;
import com.plume.code.core.generator.GeneratorBehaviorFactory;
import lombok.SneakyThrows;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneratorFacade {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorFacade.class);
    @Autowired
    protected DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    protected GeneratorBehaviorFactory generatorBehaviorFactory;

    public ResultModel generate(GeneratorConfigModel generatorConfigModel) {
        return generate(generatorConfigModel.getConnectionModel(), generatorConfigModel.getSettingModel());
    }

    @SneakyThrows
    public ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel) {
        setDefault(settingModel);
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

    public List<GeneratorBehavior> getGeneratorBehaviorList(ConnectionModel connectionModel, SettingModel settingModel) {
        List<ContextModel> contextModelList = getContextModelList(connectionModel, settingModel);

        return contextModelList.stream().map(contextModel ->
                generatorBehaviorFactory.getGeneratorBehaviorList(contextModel)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

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

    private void setDefault(SettingModel settingModel) {
        if (StringUtils.isNullOrEmpty(settingModel.getBatchNo())) {
            settingModel.setBatchNo(String.valueOf(System.currentTimeMillis()));
        }

        if (StringUtils.isNullOrEmpty(settingModel.getDtoPostfix())) {
            settingModel.setDtoPostfix("DTO");
        }

        if (StringUtils.isNullOrEmpty(settingModel.getEntPostfix())) {
            settingModel.setEntPostfix("ENT");
        }

        if (StringUtils.isNullOrEmpty(settingModel.getVoPostfix())) {
            settingModel.setVoPostfix("VO");
        }

        if (StringUtils.isNullOrEmpty(settingModel.getQueryPostfix())) {
            settingModel.setQueryPostfix("Query");
        }

        if (null == settingModel.getLombokState()) {
            settingModel.setLombokState(false);
        }
    }
}
