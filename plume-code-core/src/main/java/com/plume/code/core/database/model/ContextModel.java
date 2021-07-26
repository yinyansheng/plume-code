package com.plume.code.core.database.model;

import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.SettingModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContextModel {
    private ConnectionModel connectionModel;
    private SettingModel settingModel;
    private ClassModel classModel;
    private List<FieldModel> fieldModelList;
}
