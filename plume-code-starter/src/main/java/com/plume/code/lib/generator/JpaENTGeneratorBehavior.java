package com.plume.code.lib.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Locale;
import java.util.Map;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaENTGeneratorBehavior extends JavaGeneratorBehavior {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

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
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Locale locale = new Locale("zh");
        Template template = configuration.getTemplate(getTemplateName(), locale, "UTF-8");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, getTemplateContext());
    }
}
