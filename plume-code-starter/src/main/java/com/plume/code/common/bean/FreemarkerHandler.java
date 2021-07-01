package com.plume.code.common.bean;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

/**
 * @Author yys
 * @Date 2017/11/6
 * @Description
 */
@Component
public class FreemarkerHandler {

    @Autowired
    private Configuration configuration;

    private static final String TEMPLATE_FILE_PATH = "template";

    @Bean
    @SneakyThrows
    public Configuration configuration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        ClassTemplateLoader loader = new ClassTemplateLoader(FreemarkerHandler.class, TEMPLATE_FILE_PATH);
        cfg.setTemplateLoader(loader);
        return cfg;
    }


    public String render(String templateName, Object model) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

}
