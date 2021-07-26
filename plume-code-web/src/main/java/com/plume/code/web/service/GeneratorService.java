package com.plume.code.web.service;



import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.model.ContextModel;
import com.plume.code.core.database.model.ResultModel;
import com.plume.code.core.generator.GeneratorBehavior;

import java.util.List;

public interface GeneratorService {

    ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel);

    List<GeneratorBehavior> getGeneratorBehaviorList(ConnectionModel connectionModel, SettingModel settingModel);

    List<ContextModel> getContextModelList(ConnectionModel connectionModel, SettingModel settingModel);

    void clear();
}
