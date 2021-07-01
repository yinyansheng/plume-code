package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class MybatisServiceImplGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Mybatis-ServiceImpl.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service.impl");
    }

    @Override
    protected String getFileName() {
        return String.format("%sServiceImpl.java", upperFirstCase(classModel.getName()));
    }

}
