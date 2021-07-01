package com.plume.code.lib.generator;

import com.plume.code.common.bean.FreemarkerHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaENTGeneratorBehavior extends JavaGeneratorBehavior {

    @Autowired
    private FreemarkerHandler freemarkerHandler;

    @Override
    protected String getTemplateName() {
        return "Jpa-ENT.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".repository.entity");
    }

    @Override
    protected String getFileName() {
        return String.format("%sENT.java", upperFirstCase(classModel.getName()));
    }

    @Override
    @SneakyThrows
    protected String render(Map<String, Object> templateContext) {
        return freemarkerHandler.render(getTemplateName(), getTemplateContext());
    }
}
