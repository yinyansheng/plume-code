package com.plume.code.lib.generator;

import com.plume.code.lib.template.FreemarkerTemplate;
import com.plume.code.lib.template.TemplateBehavior;
import com.plume.code.lib.template.TemplateFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaENTGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Jpa-ENT.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".repository.entity");
    }

    @Override
    protected String getFileName() {
        return String.format("%sENT.java", upperFirstCase(classModel.getName()));
    }

    @Override
    protected TemplateBehavior getTemplateBehavior() {
        return templateFactory.getTemplateBehavior(FreemarkerTemplate.class);
    }
}
