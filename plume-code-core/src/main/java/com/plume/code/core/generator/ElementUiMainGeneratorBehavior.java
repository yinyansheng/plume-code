package com.plume.code.core.generator;

import com.plume.code.core.common.helper.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author : pdl
 * @date : 2021/6/29 17:28
 */

@Component
@Scope("prototype")
public class ElementUiMainGeneratorBehavior extends VueGeneratorBehavior {
    @Override
    protected String getFileName() {
        return StringHelper.upperFirstCase(classModel.getName()).concat("Main.vue");
    }

    @Override
    protected String getTemplateName() {
        return "ElementUi-Main.vue.tpl";
    }
}
