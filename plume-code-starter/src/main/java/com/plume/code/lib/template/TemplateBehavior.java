package com.plume.code.lib.template;

import java.util.Map;

public abstract class TemplateBehavior {
    protected static final String BASE_TEMPLATE_PATH = "template/";

    public abstract String render(Map<String, Object> context, String templateName);
}
