package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class MybatisPlusServiceGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "MybatisPlus-Service.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service");
    }

    @Override
    protected String getFileName() {
        return String.format("%sService.java", StringHelper.upperFirstCase(classModel.getName()));
    }

}
