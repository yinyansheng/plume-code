package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

@Component
@Scope("prototype")
class ServiceGeneratorBehavior extends GeneratorBehavior {

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service");
    }

    @Override
    protected String getFileName() {
        return String.format("%sService.java", upperFirstCase(classModel.getName()));
    }
}
