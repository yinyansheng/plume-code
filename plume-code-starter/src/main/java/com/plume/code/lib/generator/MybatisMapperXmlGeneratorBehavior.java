package com.plume.code.lib.generator;

import com.plume.code.lib.database.model.FieldModel;
import org.apache.velocity.VelocityContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

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
