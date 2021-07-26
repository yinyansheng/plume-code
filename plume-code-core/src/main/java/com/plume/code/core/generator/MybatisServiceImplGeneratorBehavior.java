package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        return String.format("%sServiceImpl.java", StringHelper.upperFirstCase(classModel.getName()));
    }

}
