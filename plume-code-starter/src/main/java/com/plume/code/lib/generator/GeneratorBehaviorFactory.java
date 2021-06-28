package com.plume.code.lib.generator;

import com.plume.code.common.constrant.SettingConstant;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ContextModel;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeneratorBehaviorFactory {

    private final Map<String, GeneratorBehavior> generatorBehaviorMap;

    public GeneratorBehaviorFactory(Map<String, GeneratorBehavior> databaseBehaviorMap) {
        this.generatorBehaviorMap = databaseBehaviorMap;
    }

    public List<GeneratorBehavior> getGeneratorBehaviorList(ContextModel contextModel) {
        return getTypeSet(contextModel.getSettingModel())
                .stream()
                .map(type -> getGeneratorBehavior(type, contextModel)).collect(Collectors.toList());
    }

    public GeneratorBehavior getGeneratorBehavior(String type, ContextModel contextModel) {
        String beanName = type.concat("GeneratorBehavior");
        if (!generatorBehaviorMap.containsKey(beanName)) {
            throw new NotImplementedException("not support:%s", beanName);
        }

        GeneratorBehavior generatorBehavior = generatorBehaviorMap.get(beanName);
        generatorBehavior.initialize(contextModel);
        return generatorBehavior;
    }

    public Set<String> getTypeSet(SettingModel settingModel) {
        Set<String> set = new HashSet<>();
        
        if (settingModel.getServiceMode().equals(SettingConstant.ServiceMode.SERVICE)) {
            set.add("service");
            set.add("serviceImpl");
        }

        return set;
    }


}
