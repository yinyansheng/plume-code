package com.plume.code.lib.generator;

import com.plume.code.lib.database.model.FieldModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaRepositoryGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Jpa-Repository.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".repository");
    }

    @Override
    protected String getFileName() {
        return String.format("%sRepository.java", upperFirstCase(classModel.getName()));
    }

    @Override
    protected Map<String, Object> getTemplateContext() {
        Map<String, Object> templateContext = super.getTemplateContext();

        Optional<FieldModel> first = fieldModelList.stream().filter(FieldModel::isPk).findFirst();
        first.ifPresent(fieldModel -> templateContext.put("pkType", fieldModel.getType()));
        return templateContext;
    }
}
