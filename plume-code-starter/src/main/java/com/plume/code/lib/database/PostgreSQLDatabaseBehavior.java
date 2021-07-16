package com.plume.code.lib.database;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class PostgreSQLDatabaseBehavior extends MetaDataDatabaseBehavior {
    private static final String DATABASE_SQL = "SELECT current_database();";

    @Override
    public String getDatabaseName() {
        String databaseName = getJdbcTemplate().queryForObject(DATABASE_SQL, String.class);

        if (StringUtils.isEmpty(databaseName)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return databaseName;
    }
}
