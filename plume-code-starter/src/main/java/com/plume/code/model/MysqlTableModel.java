package com.plume.code.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

import static com.plume.code.common.helper.GeneratorHepler.removePrefix;
import static com.plume.code.common.helper.GeneratorHepler.removeUnderline;

/**
 * @author yinyansheng
 * mysql table model
 */
@Data
public class MysqlTableModel extends BaseTableModel {
    private String tableSchema;
    private String tableName;
    private String tableComment;
    private Timestamp createTime;

    @Override
    public void initialize(SettingModel settingModel) {
        if (StringUtils.isNotEmpty(settingModel.getTablePrefix()) && tableName.startsWith(settingModel.getTablePrefix())) {
            tableName = removePrefix(tableName, settingModel.getTablePrefix().split(","));
        }

        this.className = removeUnderline(tableName);
        this.classComment = tableComment;
    }
}
