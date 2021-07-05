package com.plume.code.lib.generator;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author : pdl
 * @date : 2021/6/29 17:28
 */

@Component
@Scope("prototype")
public class ElementUiApiGeneratorBehavior extends VueGeneratorBehavior {
    @SneakyThrows
    @Override
    protected String getFilePath() {

        return String.format("%s/%s/%s",
                projectPath,
                "portal/api",
                getFileName());
    }

    @Override
    protected String getFileName() {
        return classModel.getName().concat(".js");
    }

    @Override
    protected String getTemplateName() {
        return "ElementUi-api.js.tpl";
    }
}
