package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class ServiceGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service");
    }

    @Override
    protected String getTemplateName() {
        return "Service.java.tpl";
    }

    @Override
    protected String getFileName() {
        return String.format("%sService.java", upperFirstCase(classModel.getName()));
    }
}
