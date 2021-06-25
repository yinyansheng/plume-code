package com.plume.code.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorSettingModel {
    private String tablePrefix;
    private String columnPrefix;
    private String projectName;
    private String packageName;

    private Boolean swagger;
    private Boolean lombok;
}
