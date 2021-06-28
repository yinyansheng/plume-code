package com.plume.code.lib.database;

import cn.hutool.core.util.ZipUtil;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import com.plume.code.lib.generator.GeneratorBehaviorFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.sql.JDBCType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * database service
 *
 * @author yinyansheng
 */
public abstract class DatabaseBehavior {
    protected ConnectionModel connectionModel;

    protected SettingModel settingModel;

    @Autowired
    private GeneratorBehaviorFactory generatorBehaviorFactory;

    void initialize(ConnectionModel connectionModel, SettingModel settingModel) {
        if (null == connectionModel) {
            throw new IllegalArgumentException("connectionModel must be not null");
        }

        if (null == settingModel) {
            throw new IllegalArgumentException("settingModel must be not null");
        }

        this.connectionModel = connectionModel;
        this.settingModel = settingModel;
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    private DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(connectionModel.getDriver());
        dataSource.setJdbcUrl(connectionModel.getUrl());
        dataSource.setUsername(connectionModel.getUsername());
        dataSource.setPassword(connectionModel.getPassword());
        return dataSource;
    }

    public abstract String getDatabaseName();

    public abstract List<ClassModel> listTableModel();

    public abstract Set<String> getPrimaryKeySet(String tableName);

    public abstract List<FieldModel> listFieldModel(String tableName);

    @SneakyThrows
    public ResultModel generate() {
        List<GeneratorBehavior> generatorBehaviorList = getGeneratorBehaviorList();
        generatorBehaviorList.forEach(GeneratorBehavior::generate);

        URL resource = this.getClass().getClassLoader().getResource("");

        if (null == resource) {
            throw new RuntimeException("can't find the resource URL");
        }

        String directoryPath = resource.getPath().concat("download/").concat(settingModel.getBatchNo());

        if (!(new File(directoryPath).exists())) {
            throw new NotDirectoryException(directoryPath);
        }

        File zip = ZipUtil.zip(directoryPath);

        return ResultModel.builder()
                .directoryPath(directoryPath)
                .zipPath(zip.getPath())
                .build();
    }

    public List<GeneratorBehavior> getGeneratorBehaviorList() {
        List<ContextModel> contextModelList = getContextModelList();

        return contextModelList.stream().map(contextModel ->
                generatorBehaviorFactory.getGeneratorBehaviorList(contextModel)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<ContextModel> getContextModelList() {
        if (CollectionUtils.isEmpty(settingModel.getTableNameSet())) {
            return Collections.emptyList();
        }

        List<ClassModel> classModels = listTableModel();
        List<ContextModel> contextModels = new ArrayList<>();

        for (ClassModel classModel : classModels) {
            if (!settingModel.getTableNameSet().contains(classModel.getTableName())) {
                continue;
            }

            List<FieldModel> fieldModels = listFieldModel(classModel.getTableName());

            ContextModel contextModel = ContextModel.builder()
                    .connectionModel(connectionModel)
                    .settingModel(settingModel)
                    .classModel(classModel)
                    .fieldModelList(fieldModels)
                    .build();

            contextModels.add(contextModel);
        }

        return contextModels;
    }

    protected String getFieldType(JDBCType jdbcType) {
        switch (jdbcType) {
            case BIT:
            case BOOLEAN:
                return "Boolean";
            case TINYINT:
            case SMALLINT:
            case INTEGER:
                return "Integer";
            case BIGINT:
                return "Long";
            case REAL:
                return "Float";
            case FLOAT:
            case DOUBLE:
                return "Double";
            case DECIMAL:
            case NUMERIC:
                return "BigDecimal";
            case VARCHAR:
            case CHAR:
            case NCHAR:
            case NVARCHAR:
            case LONGVARCHAR:
            case LONGNVARCHAR:
                return "String";
            case DATE:
            case TIME:
            case TIMESTAMP:
                return "Date";
            case CLOB:
            case NCLOB:
            case BLOB:
            case BINARY:
            case VARBINARY:
            case LONGVARBINARY:
                return "byte[]";
            default:
                return "Object";
        }
    }
}
