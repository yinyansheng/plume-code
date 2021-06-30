package com.plume.code.lib.generator;

import com.plume.code.lib.database.model.ContextModel;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeneratorBehaviorFactory implements InitializingBean {

    @Autowired
    private List<GeneratorBehavior> generatorBehaviorList;

    private Map<String, GeneratorBehavior> generatorBehaviorMap;


    public List<GeneratorBehavior> getGeneratorBehaviorList(ContextModel contextModel) {
        return contextModel.getSettingModel().getTemplateNameSet()
                .stream()
                .map(templateName -> getGeneratorBehavior(templateName, contextModel)).collect(Collectors.toList());
    }

    public GeneratorBehavior getGeneratorBehavior(String templateName, ContextModel contextModel) {
        if (!generatorBehaviorMap.containsKey(templateName)) {
            throw new NotImplementedException(String.format("not support:%s", templateName));
        }

        GeneratorBehavior generatorBehavior = generatorBehaviorMap.get(templateName);
        generatorBehavior.initialize(contextModel);
        return generatorBehavior;
    }

    @Override
    public void afterPropertiesSet() {
        generatorBehaviorMap = generatorBehaviorList.stream().collect(Collectors.toMap(GeneratorBehavior::getTemplateName, r -> r));
    }
}
