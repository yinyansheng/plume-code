package com.plume.code.core.database;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class OracleDatabaseBehavior extends MetaDataDatabaseBehavior {

    @Override
    protected String getDatabaseNameSql() {
        return "select name from v$database;";
    }
}
