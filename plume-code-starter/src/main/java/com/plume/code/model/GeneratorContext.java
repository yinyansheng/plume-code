package com.plume.code.model;

import com.plume.code.common.helper.GeneratorHepler;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@Builder
public class GeneratorContext {
    private ConnectionModel connectionModel;
    private TableModel tableModel;
    private List<ColumnModel> columnModelList;

    private SettingModel settingModel;

    public String getClassName() {
        String tableName = tableModel.getTableName();

        if (StringUtils.isNotEmpty(settingModel.getTablePrefix()) && tableName.startsWith(settingModel.getTablePrefix())) {
            tableName = GeneratorHepler.removePrefixAndUnderline(tableName, settingModel.getTablePrefix().split(","));
        }

        return tableName;
    }

}
