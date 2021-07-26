package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class MybatisPlusMapperGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "MybatisPlus-Mapper.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".mapper");
    }

    @Override
    protected String getFileName() {
        return String.format("%sMapper.java", StringHelper.upperFirstCase(classModel.getName()));
    }

}
