package com.plume.code.core.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.core.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class DTOGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "DTO.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service.dto");
    }

    @Override
    protected String getFileName() {
        return String.format("%s%s.java", upperFirstCase(classModel.getName()), settingModel.getDtoPostfix());
    }


}
