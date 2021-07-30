package com.plume.code.core.common.model;

import com.plume.code.core.common.enums.ControllerOption;
import com.plume.code.core.common.enums.PortalOption;
import com.plume.code.core.common.enums.RepositoryOption;
import com.plume.code.core.common.enums.ServiceOption;
import lombok.Data;

import java.util.List;

@Data
public class GeneratorConfigModel {

    public GeneratorConfigModel() {

    }

    public GeneratorConfigModel(ConnectionModel connectionModel, SettingModel settingModel) {
        this.connectionModel = connectionModel;
        this.settingModel = settingModel;
    }
    
    private ConnectionModel connectionModel;
    private SettingModel settingModel;

    private List<PortalOption> portal;
    private List<ServiceOption> service;
    private List<ControllerOption> controller;
    private RepositoryOption repository;
}
