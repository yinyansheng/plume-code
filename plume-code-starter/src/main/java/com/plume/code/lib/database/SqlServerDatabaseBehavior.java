package com.plume.code.lib.database;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class SqlServerDatabaseBehavior extends MetaDataDatabaseBehavior {
    private static final String DATABASE_SQL = "Select Name From Master..SysDataBases Where DbId=(Select Dbid From Master..SysProcesses Where Spid = @@spid)";

    @Override
    public String getDatabaseName() {
        String databaseName = getJdbcTemplate().queryForObject(DATABASE_SQL, String.class);

        if (StringUtils.isEmpty(databaseName)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return databaseName;
    }
}
