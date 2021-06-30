package com.plume.code.lib.generator;

import lombok.SneakyThrows;

/**
 * generator base service
 */
public abstract class VueGeneratorBehavior extends GeneratorBehavior {

    @SneakyThrows
    @Override
    protected String getFilePath() {
        return String.format("%s/%s/%s/%s",
                projectPath,
                "portal/src/views",
                classModel.getName(),
                getFileName());
    }

}
