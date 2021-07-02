package com.plume.code.lib.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Locale;
import java.util.Map;

@Component
public class FreemarkerTemplate extends TemplateBehavior {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @SneakyThrows
    @Override
    public String render(Map<String, Object> context, String templateName) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Locale locale = new Locale("zh");
        Template template = configuration.getTemplate(templateName, locale, "UTF-8");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
    }
}
