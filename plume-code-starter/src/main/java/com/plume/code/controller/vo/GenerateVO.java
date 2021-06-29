package com.plume.code.controller.vo;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import lombok.Data;

/**
 * @author : pdl
 * @date : 2021/6/29 14:23
 */
@Data
public class GenerateVO {
    private ConnectionModel connectionModel;
    private SettingModel settingModel;
}
