package com.plume.code.lib.generator;

import com.plume.code.common.helper.GeneratorHelper;
import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;

/**
 * generator base service
 */
public abstract class JavaGeneratorBehavior extends GeneratorBehavior {

    protected abstract String getPackageName();

    /**
     * java文件的path 可以由packageName 转换的来
     * 例子 com.example.order.service  对应的文件夹位 {sourcePath}/com/example/order/service
     *
     * @return the generate file path
     */
    @SneakyThrows
    @Override
    protected String getFilePath() {
        String packagePath = BASE_FILE_PATH.concat(getPackageName().replace(".", "/"));

        return String.format("%s/%s/%s",
                projectPath,
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

        String servicePackageName = settingModel.getBasePackageName().concat(".service");
        velocityContext.put("servicePackageName", servicePackageName);

        velocityContext.put("typePackageNameList", getTypePackageNameList());
        return velocityContext;
    }

    protected List<String> getTypePackageNameList() {
        List<String> typePackageNameList = new ArrayList<>();
        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("Date"))) {
            typePackageNameList.add("import java.util.Date;");
        }

        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("BigDecimal"))) {
            typePackageNameList.add("import java.math.BigDecimal;");
        }

        if (fieldModelList.stream().anyMatch(r -> r.getType().equals("Timestamp"))) {
            typePackageNameList.add("import java.sql.Timestamp;");
        }


        return typePackageNameList;
    }
}
