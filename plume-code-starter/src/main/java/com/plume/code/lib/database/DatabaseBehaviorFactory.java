package com.plume.code.lib.database;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DatabaseBehaviorFactory {

    private final Map<String, DatabaseBehavior> databaseBehaviorMap;

    public DatabaseBehaviorFactory(Map<String, DatabaseBehavior> databaseBehaviorMap) {
        this.databaseBehaviorMap = databaseBehaviorMap;
    }

    public DatabaseBehavior getDatabaseBehavior(ConnectionModel connectionModel, SettingModel settingModel) {
        String beanName = connectionModel.getType().toLowerCase().concat("DatabaseBehavior");
        if (!databaseBehaviorMap.containsKey(beanName)) {
            throw new NotImplementedException("not support:%s", beanName);
        }

        DatabaseBehavior databaseBehavior = databaseBehaviorMap.get(beanName);
        databaseBehavior.initialize(connectionModel, settingModel);
        return databaseBehavior;
    }
}
