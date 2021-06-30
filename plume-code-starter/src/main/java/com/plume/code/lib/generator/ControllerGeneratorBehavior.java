package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

@Component
@Scope("prototype")
class ControllerGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Controller.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat("admin.controller");
    }

    @Override
    protected String getFileName() {
        return String.format("%sController.java", upperFirstCase(classModel.getName()));
    }


}
