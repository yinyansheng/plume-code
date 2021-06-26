package com.plume.code.service;

import com.plume.code.common.context.GeneratorContext;

import java.util.Collections;
import java.util.Map;

/**
 * generator base service
 */
public abstract class GeneratorService {
    protected GeneratorContext generatorContext;

    protected GeneratorService(GeneratorContext generatorContext) {
        this.generatorContext = generatorContext;
    }
    
    protected abstract String getPackageName();

    protected String getPath() {
        return "src\\main\\java\\".concat(getPackageName().replace(".", "\\"));
    }

    protected String getTemplate() {
        return this.getClass().getName().replace("GeneratorService", "").concat(".vm");
    }

    protected Map<String, String> getSpecificParameters() {
        return Collections.emptyMap();
    }

    public void generate() {

    }
}
