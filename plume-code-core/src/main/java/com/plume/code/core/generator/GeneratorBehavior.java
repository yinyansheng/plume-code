package com.plume.code.core.generator;

import com.plume.code.core.common.helper.PathHelper;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.model.ClassModel;
import com.plume.code.core.database.model.ContextModel;
import com.plume.code.core.database.model.FieldModel;
import com.plume.code.core.template.TemplateBehavior;
import com.plume.code.core.template.TemplateFactory;
import com.plume.code.core.template.VelocityBehavior;
import com.plume.code.core.common.helper.StringHelper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * generator base service
 */
public abstract class GeneratorBehavior {
    protected SettingModel settingModel;
    protected ClassModel classModel;
    protected List<FieldModel> fieldModelList;
    protected String projectPath;

    @Autowired
    protected TemplateFactory templateFactory;

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
        String downloadPath = PathHelper.getDownloadPath(settingModel.getDownloadPath());
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
        String content = getTemplateBehavior().render(templateContext, getTemplateName());
        String filePath = getFilePath();
        FileUtils.write(new File(filePath), content, StandardCharsets.UTF_8);
    }

    protected TemplateBehavior getTemplateBehavior() {
        return templateFactory.getTemplateBehavior(VelocityBehavior.class);
    }

    protected Map<String, Object> getTemplateContext() {
        Map<String, Object> templateContext = new HashMap<>(32);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        templateContext.put("setting", settingModel);
        templateContext.put("createTime", simpleDateFormat.format(new Date()));
        templateContext.put("className", classModel.getName());
        templateContext.put("ClassName", StringHelper.upperFirstCase(classModel.getName()));
        templateContext.put("tableName", classModel.getTableName());
        templateContext.put("author", settingModel.getAuthor());
        templateContext.put("comment", classModel.getComment());
        templateContext.put("lombok", settingModel.getLombokState());
        templateContext.put("fieldModelList", fieldModelList);
        templateContext.put("isMultiplePK", fieldModelList.stream().filter(FieldModel::isPk).count() > 1);
        templateContext.put("primaryKeyList", fieldModelList.stream().filter(FieldModel::isPk).collect(Collectors.toList()));

        return templateContext;
    }
}
