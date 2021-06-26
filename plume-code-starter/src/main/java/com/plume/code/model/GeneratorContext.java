package com.plume.code.model;

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
