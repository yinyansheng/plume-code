package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class MybatisPlusMapperXmlGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "MybatisPlus-Mapper.xml.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".mapper.xml");
    }

    @Override
    protected String getFileName() {
        return String.format("%sMapper.xml", StringHelper.upperFirstCase(classModel.getName()));
    }

}
