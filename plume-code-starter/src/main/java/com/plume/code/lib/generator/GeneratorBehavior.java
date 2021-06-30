package com.plume.code.lib.generator;

import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.FieldModel;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

/**
 * generator base service
 */
public abstract class GeneratorBehavior {
    protected SettingModel settingModel;
    protected ClassModel classModel;
    protected List<FieldModel> fieldModelList;

    void initialize(ContextModel contextModel) {
        this.settingModel = contextModel.getSettingModel();
        this.classModel = contextModel.getClassModel();
        this.fieldModelList = contextModel.getFieldModelList();

        if (null == settingModel) {
            throw new IllegalArgumentException("settingModel must be not null");
        }

        if (StringUtils.isEmpty(settingModel.getBasePackageName())) {
            throw new IllegalArgumentException("package name must be not empty");
        }
    }

    public static final String BASE_FILE_PATH = "src/main/java/";
    public static final String BASE_TEMPLATE_PATH = "template/";

    /**
     * @return like 'MybatisPlus-ENT.java.tpl'
     */
    protected abstract String getTemplateName();

    /**
     * @return like 'userService.java'
     */
    protected abstract String getFileName();

    /**
     * @return full file path
     */
    protected abstract String getFilePath();

    @SneakyThrows
    public void generate() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(BASE_TEMPLATE_PATH.concat(getTemplateName()), StandardCharsets.UTF_8.name());
        VelocityContext velocityContext = getVelocityContext();

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        String content = stringWriter.toString();

        String filePath = getFilePath();
        FileUtils.write(new File(filePath), content, StandardCharsets.UTF_8);
    }

    protected VelocityContext getVelocityContext() {
        VelocityContext velocityContext = new VelocityContext();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        velocityContext.put("createTime", simpleDateFormat.format(new Date()));
        velocityContext.put("className", classModel.getName());
        velocityContext.put("ClassName", upperFirstCase(classModel.getName()));
        velocityContext.put("tableName", classModel.getTableName());
        velocityContext.put("author", settingModel.getAuthor());
        velocityContext.put("comment", classModel.getComment());
        velocityContext.put("lombok", settingModel.getLombokState());
        velocityContext.put("fieldModelList", fieldModelList);

        return velocityContext;
    }
}
