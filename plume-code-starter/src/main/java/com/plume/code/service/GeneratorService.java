package com.plume.code.service;

import cn.hutool.core.util.ZipUtil;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface GeneratorService {


    @SneakyThrows
    ResultModel generate(ConnectionModel connectionModel, SettingModel settingModel);

    List<GeneratorBehavior> getGeneratorBehaviorList(ConnectionModel connectionModel, SettingModel settingModel);

    List<ContextModel> getContextModelList(ConnectionModel connectionModel, SettingModel settingModel);
}
