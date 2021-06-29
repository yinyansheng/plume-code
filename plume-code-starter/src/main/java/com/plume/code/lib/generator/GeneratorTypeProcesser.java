package com.plume.code.lib.generator;

import com.plume.code.common.constrant.GeneratorConstant;
import com.plume.code.common.constrant.SettingConstant;
import com.plume.code.common.model.SettingModel;

import java.util.HashSet;
import java.util.Set;

public class GeneratorTypeProcesser {
    public static Set<String> getTypeSet(SettingModel settingModel) {
        Set<String> typeSet = new HashSet<>();

        if (settingModel.getServiceMode().equals(SettingConstant.ServiceMode.SERVICE)) {
            typeSet.add(GeneratorConstant.Type.service);
            typeSet.add(GeneratorConstant.Type.serviceImpl);
        }

        if (settingModel.getRepositoryMode().equals(SettingConstant.RepositoryMode.MYBATIS_PLUS)) {
            typeSet.add(GeneratorConstant.Type.mybatisPlusEnt);
        }

        if (settingModel.getPortalMode().equals(SettingConstant.PortalMode.ELEMENT_UI)) {
            typeSet.add(GeneratorConstant.Type.mainVue);
            typeSet.add(GeneratorConstant.Type.elementTableVue);
        }

//        if (settingModel.getPortalMode().equals(SettingConstant.PortalMode.IVIEW)) {
//            typeSet.add(GeneratorConstant.Type.mainVue);
//        }

        return typeSet;
    }

}
