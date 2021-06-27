package com.plume.code.lib.generator;

import com.plume.code.common.context.GeneratorContext;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeneratorBehaviorFactory {

    private final Map<String, GeneratorBehavior> generatorBehaviorMap;

    public GeneratorBehaviorFactory(Map<String, GeneratorBehavior> databaseBehaviorMap) {
        this.generatorBehaviorMap = databaseBehaviorMap;
    }

    public GeneratorBehavior getGeneratorBehavior(String type, GeneratorContext generatorContext) {
        String beanName = type.concat("GeneratorBehavior");
        if (!generatorBehaviorMap.containsKey(beanName)) {
            throw new NotImplementedException("not support:%s", beanName);
        }

        GeneratorBehavior generatorBehavior = generatorBehaviorMap.get(beanName);
        generatorBehavior.initialize(generatorContext);
        return generatorBehavior;
    }
}
