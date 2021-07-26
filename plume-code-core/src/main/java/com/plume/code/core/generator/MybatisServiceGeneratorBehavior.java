package com.plume.code.core.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.core.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class MybatisServiceGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Mybatis-Service.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service");
    }

    @Override
    protected String getFileName() {
        return String.format("%sService.java", upperFirstCase(classModel.getName()));
    }

}
