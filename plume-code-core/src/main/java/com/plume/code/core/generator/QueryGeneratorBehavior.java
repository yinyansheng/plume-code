package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        return String.format("%s%s.java", StringHelper.upperFirstCase(classModel.getName()), settingModel.getQueryPostfix());
    }


}
