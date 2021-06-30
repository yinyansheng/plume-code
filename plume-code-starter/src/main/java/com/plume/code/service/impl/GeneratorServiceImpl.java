package com.plume.code.service.impl;

import cn.hutool.core.util.ZipUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    protected DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    protected GeneratorBehaviorFactory generatorBehaviorFactory;

    @SneakyThrows
    @Override
    public ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel) {
        List<GeneratorBehavior> generatorBehaviorList = getGeneratorBehaviorList(connectionModel, settingModel);
        generatorBehaviorList.forEach(GeneratorBehavior::generate);

        String userDirPath = System.getProperty("user.dir");
        String downloadPath = userDirPath.concat("/plume-code-download/");

        String directoryPath = downloadPath.concat(settingModel.getBatchNo());

        if (!(new File(directoryPath).exists())) {
            throw new FileNotFoundException(directoryPath);
        }

        File zip = ZipUtil.zip(directoryPath);

        return ResultModel.builder()
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


}
