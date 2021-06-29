package com.plume.code.lib.generator;

import com.plume.code.common.helper.GeneratorHelper;
import com.sun.tools.internal.ws.processor.generator.GeneratorUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * generator base service
 */
public abstract class VueGeneratorBehavior extends GeneratorBehavior {

    @Override
    protected String getPackageName() {
        return StringUtils.EMPTY;
    }

    @SneakyThrows
    @Override
    protected String getFilePath() {
        URL resource = this.getClass().getClassLoader().getResource("");

        if (null == resource) {
            throw new RuntimeException("can't find the resource URL");
        }

        String downloadPath = resource.getPath().concat("download");
        File downloadPathFile = new File(downloadPath);

        if (!downloadPathFile.exists()) {
            boolean mkdirs = downloadPathFile.mkdirs();
            if (!mkdirs) {
                throw new FileNotFoundException(downloadPath);
            }
        }

        String projectName = settingModel.getProjectName();

        return String.format("%s/%s/%s/%s/%s/%s",
                downloadPath,
                settingModel.getBatchNo(),
                projectName,
                "portal/src/views",
                classModel.getName(),
                getFileName());
    }

    /**
     * @return like 'table.vue.tpl' or 'search.vue.tpl'
     */
    @Override
    protected String getTemplatePath() {
        String templateFileName = GeneratorHelper.lowerFirstCase(this.getClass().getSimpleName())
                .replace("GeneratorBehavior", "").concat(".vue.tpl");

        return BASE_TEMPLATE_PATH.concat(templateFileName);
    }
}
