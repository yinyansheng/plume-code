package com.plume.code.core.template;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TemplateFactory implements InitializingBean {

    @Autowired
    private List<TemplateBehavior> templateBehaviorList;

    private Map<Class<?>, TemplateBehavior> templateBehaviorMap;

    public <T extends TemplateBehavior> TemplateBehavior getTemplateBehavior(Class<T> clazz) {
        return templateBehaviorMap.get(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        templateBehaviorMap = templateBehaviorList.stream().collect(Collectors.toMap(TemplateBehavior::getClass, r -> r));
    }
}
