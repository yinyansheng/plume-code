package com.plume.code.lib.database.model;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
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
