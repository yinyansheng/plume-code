package com.plume.code.factory;

import com.plume.code.model.ConnectionModel;
import com.plume.code.model.SettingModel;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.impl.H2DatabaseService;
import com.plume.code.service.impl.MysqlDatabaseService;
import org.apache.commons.lang3.NotImplementedException;

public class DatabaseServiceFactory {

    public static DatabaseService getDatabaseService(ConnectionModel connectionModel, SettingModel settingModel) {
        switch (connectionModel.getType().toLowerCase()) {
            case "h2":
                return H2DatabaseService.instance(connectionModel, settingModel);
            case "mysql":
                return MysqlDatabaseService.instance(connectionModel, settingModel);
            default:
                throw new NotImplementedException("not support:%s", connectionModel.getType());
        }
    }
}
