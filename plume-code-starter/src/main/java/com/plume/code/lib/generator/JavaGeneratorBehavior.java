package com.plume.code.lib.generator;

import com.plume.code.common.helper.GeneratorHelper;
import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * generator base service
 */
public abstract class JavaGeneratorBehavior extends GeneratorBehavior {

    protected abstract String getPackageName();

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
        String packagePath = BASE_FILE_PATH.concat(getPackageName().replace(".", "/"));

        return String.format("%s/%s/%s/%s/%s",
                downloadPath,
                settingModel.getBatchNo(),
                projectName,
                packagePath,
                getFileName());
    }

    @Override
    protected VelocityContext getVelocityContext() {
        VelocityContext velocityContext = super.getVelocityContext();

        velocityContext.put("basePackageName", settingModel.getBasePackageName());
        velocityContext.put("packageName", getPackageName());

        String entityPackageName = settingModel.getBasePackageName()
                .concat(String.format(".entity.%sENT;", GeneratorHelper.upperFirstCase(classModel.getName())));
        velocityContext.put("entityPackageName", entityPackageName);

        String interfacePackageName = settingModel.getBasePackageName().concat(".service");
        velocityContext.put("interfacePackageName", interfacePackageName);

        velocityContext.put("typePackageNameList", getTypePackageNameList());
        return velocityContext;
    }

    protected List<String> getTypePackageNameList() {
        List<String> extraPackageNameList = new ArrayList<>();
        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("Date"))) {
            extraPackageNameList.add("import java.util.Date;");
        }

        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("BigDecimal"))) {
            extraPackageNameList.add("import java.math.BigDecimal;");
        }

        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("Timestamp"))) {
            extraPackageNameList.add("import java.sql.Timestamp;");
        }


        return extraPackageNameList;
    }
}
