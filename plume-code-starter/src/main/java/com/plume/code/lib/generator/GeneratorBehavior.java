package com.plume.code.lib.generator;

import com.plume.code.common.bean.PathHandler;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

/**
 * generator base service
 */
public abstract class GeneratorBehavior {
    protected SettingModel settingModel;
    protected ClassModel classModel;
    protected List<FieldModel> fieldModelList;
    protected String projectPath;

    @Autowired
    protected PathHandler pathHandler;

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

        projectPath = getProjectPath();
    }

    @SneakyThrows
    private String getProjectPath() {
        String downloadPath = pathHandler.getDownloadPath();
        File downloadPathFile = new File(downloadPath);

        if (!downloadPathFile.exists()) {
            boolean mkdirs = downloadPathFile.mkdirs();
            if (!mkdirs) {
                throw new FileNotFoundException(downloadPath);
            }
        }

        String projectName = settingModel.getProjectName();

        return String.format("%s/%s/%s",
                downloadPath,
                settingModel.getBatchNo(),
                projectName);
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
        Map<String, Object> templateContext = getTemplateContext();
        String content = render(templateContext);
        String filePath = getFilePath();
        FileUtils.write(new File(filePath), content, StandardCharsets.UTF_8);
    }

    protected String render(Map<String, Object> templateContext) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(BASE_TEMPLATE_PATH.concat(getTemplateName()), StandardCharsets.UTF_8.name());
        VelocityContext velocityContext = new VelocityContext(templateContext);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        return stringWriter.toString();
    }

    protected Map<String, Object> getTemplateContext() {
        Map<String, Object> templateContext = new HashMap<>(32);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        templateContext.put("createTime", simpleDateFormat.format(new Date()));
        templateContext.put("className", classModel.getName());
        templateContext.put("ClassName", upperFirstCase(classModel.getName()));
        templateContext.put("tableName", classModel.getTableName());
        templateContext.put("author", settingModel.getAuthor());
        templateContext.put("comment", classModel.getComment());
        templateContext.put("lombok", settingModel.getLombokState());
        templateContext.put("fieldModelList", fieldModelList);

        return templateContext;
    }
}
