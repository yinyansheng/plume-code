package com.plume.code.lib.generator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

/**
 * @author : pdl
 * @date : 2021/6/29 17:28
 */

@Component
@Scope("prototype")
public class ElementTableVueGeneratorBehavior extends VueGeneratorBehavior{
    @Override
    protected String getFileName() {
        return "components/Table.vue";
    }
}
