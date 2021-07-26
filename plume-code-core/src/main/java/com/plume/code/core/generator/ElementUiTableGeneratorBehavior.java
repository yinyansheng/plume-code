package com.plume.code.core.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author : pdl
 * @date : 2021/6/29 17:28
 */

@Component
@Scope("prototype")
public class ElementUiTableGeneratorBehavior extends VueGeneratorBehavior {
    @Override
    protected String getFileName() {
        return "Table.vue";
    }

    @Override
    protected String getTemplateName() {
        return "ElementUi-Table.vue.tpl";
    }
}
