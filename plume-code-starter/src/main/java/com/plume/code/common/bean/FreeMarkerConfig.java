package com.plume.code.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FreeMarkerConfig {

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("classpath:/template");
        Map<String, Object> variables = new HashMap<>(1 << 1);
        variables.put("xml_escape", "fmXmlEscape");
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }

}
