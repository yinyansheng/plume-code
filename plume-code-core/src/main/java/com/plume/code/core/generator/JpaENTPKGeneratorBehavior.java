package com.plume.code.core.generator;

import com.plume.code.core.database.model.FieldModel;
import com.plume.code.core.template.FreemarkerTemplate;
import com.plume.code.core.template.TemplateBehavior;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.core.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class JpaENTPKGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Jpa-ENT-PK.java.ftl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".repository.entity");
    }

    @Override
    protected String getFileName() {
        return String.format("%s%sPK.java", upperFirstCase(classModel.getName()), settingModel.getEntPostfix());
    }

    @Override
    protected TemplateBehavior getTemplateBehavior() {
        return templateFactory.getTemplateBehavior(FreemarkerTemplate.class);
    }

    @Override
    public void generate() {
        //如果非联合主键，则不生成该模板文件
        if (fieldModelList.stream().filter(FieldModel::isPk).count() <= 1) {
            return;
        }
        super.generate();
    }
}
