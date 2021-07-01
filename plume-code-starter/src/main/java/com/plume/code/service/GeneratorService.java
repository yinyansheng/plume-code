package com.plume.code.service;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.CodeFileTreeModel;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import lombok.SneakyThrows;

import java.util.List;

public interface GeneratorService {


    @SneakyThrows
    ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel);

    List<GeneratorBehavior> getGeneratorBehaviorList(ConnectionModel connectionModel, SettingModel settingModel);

    List<ContextModel> getContextModelList(ConnectionModel connectionModel, SettingModel settingModel);

    CodeFileTreeModel getCodeFileTree(String batchNo);
}
