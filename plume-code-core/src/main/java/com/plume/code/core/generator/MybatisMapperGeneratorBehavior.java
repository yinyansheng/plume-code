package com.plume.code.core.generator;

import com.plume.code.core.database.model.FieldModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.plume.code.core.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class MybatisMapperGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Mybatis-Mapper.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".mapper");
    }

    @Override
    protected String getFileName() {
        return String.format("%sMapper.java", upperFirstCase(classModel.getName()));
    }

    @Override
    protected Map<String, Object> getTemplateContext() {
        Map<String, Object> templateContext = super.getTemplateContext();
        templateContext.put("primaryKeyList", fieldModelList.stream().filter(FieldModel::isPk).collect(Collectors.toList()));
        return templateContext;
    }
}
