package com.plume.code.lib.generator;

import com.plume.code.lib.template.FreemarkerTemplate;
import com.plume.code.lib.template.TemplateBehavior;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaENTGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Jpa-ENT.java.ftl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".repository.entity");
    }

    @Override
    protected String getFileName() {
        return String.format("%s%s.java", upperFirstCase(classModel.getName()), settingModel.getEntPostfix());
    }

    @Override
    protected TemplateBehavior getTemplateBehavior() {
        return templateFactory.getTemplateBehavior(FreemarkerTemplate.class);
    }
}
