package com.plume.code.service.impl;

import com.plume.code.common.context.GeneratorContext;
import com.plume.code.model.SettingModel;
import com.plume.code.service.GeneratorService;
import org.apache.commons.lang3.StringUtils;


public class ServiceGeneratorService extends GeneratorService {
    private ServiceGeneratorService(GeneratorContext generatorContext) {
        super(generatorContext);
    }

    public static ServiceGeneratorService instance(GeneratorContext generatorContext) {
        return new ServiceGeneratorService(generatorContext);
    }

    @Override
    protected String getPackageName() {
        SettingModel settingModel = generatorContext.getSettingModel();
        if (StringUtils.isEmpty(settingModel.getPackageName())) {
            throw new IllegalArgumentException("package name must be not empty");
        }
        return settingModel.getPackageName().concat(".service");
    }
}
