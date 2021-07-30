package com.plume.code.core;

import cn.hutool.core.util.ZipUtil;
import com.plume.code.core.common.enums.ControllerOption;
import com.plume.code.core.common.enums.PortalOption;
import com.plume.code.core.common.enums.ServiceOption;
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
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GeneratorFacade {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorFacade.class);
    @Autowired
    protected DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    protected GeneratorBehaviorFactory generatorBehaviorFactory;

    public ResultModel generate(GeneratorConfigModel generatorConfigModel) {
        handleTemplate(generatorConfigModel);
        return generate(generatorConfigModel.getConnectionModel(), generatorConfigModel.getSettingModel());
    }

    private void handleTemplate(GeneratorConfigModel generatorConfigModel) {
        if (!CollectionUtils.isEmpty(generatorConfigModel.getSettingModel().getTemplateNameSet())) {
            return;
        }

        Set<String> templateSet = new HashSet<>();
        if (generatorConfigModel.getPortal().contains(PortalOption.ElementUI)) {
            templateSet.addAll(Arrays.asList(
                    "ElementUi-api.js.tpl",
                    "ElementUi-Dialog.vue.tpl",
                    "ElementUi-index.js.tpl",
                    "ElementUi-Main.vue.tpl",
                    "ElementUi-object.js.tpl",
                    "ElementUi-Search.vue.tpl"));
        }

        if (generatorConfigModel.getController().contains(ControllerOption.VO)) {
            templateSet.add("VO.java.tpl");
        }

        if (generatorConfigModel.getController().contains(ControllerOption.Query)) {
            templateSet.add("Query.java.tpl");
        }

        if (generatorConfigModel.getService().contains(ServiceOption.DTO)) {
            templateSet.add("DTO.java.tpl");
        }

        switch (generatorConfigModel.getRepository()) {
            case MybatisPlus:
                templateSet.addAll(Arrays.asList("MybatisPlus-ENT.java.tpl", "MybatisPlus-Mapper.java.tpl"));
                templateSet.addAll(Arrays.asList("MybatisPlus-Service.java.tpl", "MybatisPlus-ServiceImpl.java.tpl"));
                templateSet.addAll(Arrays.asList("MybatisPlus-Controller.java.tpl"));
                break;
            case JPA:
            case Hibernate:
                templateSet.addAll(Arrays.asList("Jpa-ENT.java.ftl", "Jpa-ENT-PK.java.ftl", "Jpa-Repository.java.tpl"));
                templateSet.addAll(Arrays.asList("Jpa-Service.java.tpl", "Jpa-ServiceImpl.java.tpl"));
                templateSet.addAll(Arrays.asList("Jpa-Controller.java.tpl"));
                break;
            case Mybatis:
                templateSet.addAll(Arrays.asList("Mybatis-ENT.java.tpl", "Mybatis-Mapper.xml.tpl", "Mybatis-Mapper.java.tpl"));
                templateSet.addAll(Arrays.asList("Mybatis-Service.java.tpl", "Mybatis-ServiceImpl.java.tpl"));
                templateSet.addAll(Arrays.asList("Mybatis-Controller.java.tpl"));
                break;
            case TkMybatis:
                templateSet.addAll(Arrays.asList("Mybatis-ENT.java.tpl", "Mybatis-TK-Mapper.xml.tpl", "Mybatis-TK-Mapper.java.tpl"));
                templateSet.addAll(Arrays.asList("Mybatis-Service.java.tpl", "Mybatis-TK-ServiceImpl.java.tpl"));
                templateSet.addAll(Arrays.asList("Mybatis-Controller.java.tpl"));
                break;
            default:
                templateSet.addAll(Arrays.asList("Service.java.tpl", "ServiceImpl.java.tpl", "Jpa-ENT.java.tpl"));
                templateSet.addAll(Arrays.asList("Controller.java.tpl"));
        }

        generatorConfigModel.getSettingModel().setTemplateNameSet(templateSet);
    }

    @SneakyThrows
    public ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel) {
        setDefault(settingModel);
        List<GeneratorBehavior> generatorBehaviorList = getGeneratorBehaviorList(connectionModel, settingModel);

        if (CollectionUtils.isEmpty(generatorBehaviorList)) {
            throw new RuntimeException("generatorBehaviorList is empty");
        }

        generatorBehaviorList.forEach(GeneratorBehavior::generate);

        String downloadPath = PathHelper.getDownloadPath(settingModel.getDownloadPath());
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
