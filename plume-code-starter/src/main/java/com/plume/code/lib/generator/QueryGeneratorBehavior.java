package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.StringHelper.upperFirstCase;

@Component
@Scope("prototype")
class QueryGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getTemplateName() {
        return "Query.java.tpl";
    }

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".admin.controller.query");
    }

    @Override
    protected String getFileName() {
        return String.format("%s%s.java", upperFirstCase(classModel.getName()), settingModel.getQueryPostfix());
    }


}
