package com.plume.code.core.generator;

import com.plume.code.core.template.FreemarkerTemplate;
import com.plume.code.core.template.TemplateBehavior;
import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        return String.format("%s%s.java", StringHelper.upperFirstCase(classModel.getName()), settingModel.getEntPostfix());
    }

    @Override
    protected TemplateBehavior getTemplateBehavior() {
        return templateFactory.getTemplateBehavior(FreemarkerTemplate.class);
    }
}
