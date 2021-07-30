package com.plume.code.core.common.model;

import com.plume.code.core.common.enums.ControllerOption;
import com.plume.code.core.common.enums.PortalOption;
import com.plume.code.core.common.enums.RepositoryOption;
import com.plume.code.core.common.enums.ServiceOption;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
public class GeneratorConfigModel {
    private ConnectionModel connectionModel;
    private SettingModel settingModel;

    private List<PortalOption> portal;
    private List<ServiceOption> service;
    private List<ControllerOption> controller;
    private RepositoryOption repository;
}
