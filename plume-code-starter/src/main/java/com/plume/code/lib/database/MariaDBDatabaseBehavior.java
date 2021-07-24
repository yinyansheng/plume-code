package com.plume.code.lib.database;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class MariaDBDatabaseBehavior extends MetaDataDatabaseBehavior {

    @Override
    protected String getDatabaseNameSql() {
        return "SELECT database()";
    }
}
