package com.plume.code.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.plume.code.common.bean.PathHandler;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.*;
import com.plume.code.lib.generator.GeneratorBehavior;
import com.plume.code.lib.generator.GeneratorBehaviorFactory;
import com.plume.code.service.GeneratorService;
import lombok.SneakyThrows;
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

    @Autowired
    protected DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    protected GeneratorBehaviorFactory generatorBehaviorFactory;

    @Autowired
    private PathHandler pathHandler;

    @SneakyThrows
    @Override
    public ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel) {
        List<GeneratorBehavior> generatorBehaviorList = getGeneratorBehaviorList(connectionModel, settingModel);

        if (CollectionUtils.isEmpty(generatorBehaviorList)) {
            throw new RuntimeException("generatorBehaviorList is empty");
        }

        generatorBehaviorList.forEach(GeneratorBehavior::generate);

        String downloadPath = pathHandler.getDownloadPath();
        String directoryPath = downloadPath.concat(settingModel.getBatchNo());

        if (!(new File(directoryPath).exists())) {
            throw new FileNotFoundException(directoryPath);
        }

        File zip = ZipUtil.zip(directoryPath);

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

    @SneakyThrows
    @Override
    public CodeFileTreeModel getCodeFileTree(String batchNo) {
        String downloadPath = pathHandler.getDownloadPath();
        String directoryPath = downloadPath.concat(batchNo);

        if (!(new File(directoryPath).exists())) {
            throw new FileNotFoundException(directoryPath);
        }
        CodeFileTreeModel tree = new CodeFileTreeModel();
        buildTree(tree, directoryPath);
        return tree;
    }

    private void buildTree(CodeFileTreeModel tree, String currentPath) {
        File currentFile = new File(currentPath);
        tree.setFileName(currentFile.getName());
        tree.setFilePath(currentFile.getAbsolutePath());
        File[] files = FileUtil.ls(currentPath);
        for (File file : files) {
            if (file.isDirectory()) {
                CodeFileTreeModel f = new CodeFileTreeModel();
                f.setFileName(file.getName());
                f.setFilePath(file.getAbsolutePath());
                tree.getChildren().add(f);
                buildTree(f, currentPath.concat("/").concat(file.getName()));
            } else {
                CodeFileTreeModel f = new CodeFileTreeModel();
                f.setFileName(file.getName());
                f.setFilePath(file.getAbsolutePath());
                tree.getChildren().add(f);
            }
        }
    }


}
