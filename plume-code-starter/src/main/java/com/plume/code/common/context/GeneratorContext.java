package com.plume.code.common.context;

import com.plume.code.model.ClassModel;
import com.plume.code.model.FieldModel;
import com.plume.code.model.SettingModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratorContext {
    private ClassModel classModel;
    private List<FieldModel> fieldModelList;
    private SettingModel settingModel;
}
