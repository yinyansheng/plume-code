package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

@Component
@Scope("prototype")
class VOGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "VO.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".admin.controller.vo");
    }

    @Override
    protected String getFileName() {
        return String.format("%sVO.java", upperFirstCase(classModel.getName()));
    }


}
