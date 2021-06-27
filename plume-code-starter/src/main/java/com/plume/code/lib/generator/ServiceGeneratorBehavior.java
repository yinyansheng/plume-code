package com.plume.code.lib.generator;

import com.plume.code.common.model.SettingModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("serviceGeneratorBehavior")
@Scope("prototype")
class ServiceGeneratorBehavior extends GeneratorBehavior {

    @Override
    protected String getPackageName() {
        SettingModel settingModel = generatorContext.getSettingModel();
        if (StringUtils.isEmpty(settingModel.getPackageName())) {
            throw new IllegalArgumentException("package name must be not empty");
        }
        return settingModel.getPackageName().concat(".service");
    }
}
