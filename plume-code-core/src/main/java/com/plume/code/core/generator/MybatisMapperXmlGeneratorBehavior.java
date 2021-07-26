package com.plume.code.core.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.core.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class MybatisMapperXmlGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Mybatis-Mapper.xml.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".mapper");
    }

    @Override
    protected String getFileName() {
        return String.format("%sMapper.xml", upperFirstCase(classModel.getName()));
    }
    
}
