package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author : pdl
 * @date : 2021/6/29 17:28
 */

@Component
@Scope("prototype")
public class ElementUiDialogGeneratorBehavior extends VueGeneratorBehavior {
    @Override
    protected String getFileName() {
        return "OptionDialog.vue";
    }

    @Override
    protected String getTemplateName() {
        return "ElementUi-Dialog.vue.tpl";
    }
}
