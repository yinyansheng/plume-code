package com.plume.code.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratorContext {
    private BaseTableModel tableModel;
    private List<BaseColumnModel> columnModelList;
    private SettingModel settingModel;
}
