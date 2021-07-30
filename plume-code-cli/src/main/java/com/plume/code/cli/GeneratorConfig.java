package com.plume.code.cli;

import com.plume.code.cli.enums.ControllerOption;
import com.plume.code.cli.enums.PortalOption;
import com.plume.code.cli.enums.RepositoryOption;
import com.plume.code.cli.enums.ServiceOption;
import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.SettingModel;
import lombok.Data;

@Data
public class GeneratorConfig {
    private ConnectionModel connectionModel;
    private SettingModel settingModel;

    private List<PortalOption> portal;
    private List<ServiceOption> service;
    private List<ControllerOption> controller;
    private RepositoryOption repository;
}
