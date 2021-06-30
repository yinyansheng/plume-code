package com.plume.code.lib.generator;

import org.apache.velocity.VelocityContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.plume.code.common.helper.GeneratorHelper.upperFirstCase;

@Component
@Scope("prototype")
class ServiceImplGeneratorBehavior extends JavaGeneratorBehavior {

    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".service.impl");
    }

    @Override
    protected String getTemplateName() {
        return "ServiceImpl.java.tpl";
    }

    @Override
    protected String getFileName() {
        return String.format("%sServiceImpl.java", upperFirstCase(classModel.getName()));
    }

    @Override
    protected VelocityContext getVelocityContext() {
        VelocityContext velocityContext = super.getVelocityContext();

        String interfacePackageName = settingModel.getBasePackageName().concat(".service");
        velocityContext.put("interfacePackageName", interfacePackageName);
        return velocityContext;
    }

}
