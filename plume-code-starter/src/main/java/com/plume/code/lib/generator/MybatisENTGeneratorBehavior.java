package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class MybatisENTGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Mybatis-ENT.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".mapper.entity");
    }

    @Override
    protected String getFileName() {
        return String.format("%s%s.java", upperFirstCase(classModel.getName()), settingModel.getEntPostfix());
    }

}
