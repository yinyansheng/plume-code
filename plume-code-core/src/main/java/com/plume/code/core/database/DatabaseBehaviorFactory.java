package com.plume.code.core.database;

import com.plume.code.core.common.helper.StringHelper;
import com.plume.code.core.common.model.ConnectionModel;
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

    public DatabaseBehavior getDatabaseBehavior(ConnectionModel connectionModel) {
        String beanName = StringHelper.lowerFirstCase(connectionModel.getType()).concat("DatabaseBehavior");
        if (!databaseBehaviorMap.containsKey(beanName)) {
            throw new NotImplementedException(String.format("not support:%s", beanName));
        }

        DatabaseBehavior databaseBehavior = databaseBehaviorMap.get(beanName);
        databaseBehavior.initialize(connectionModel);
        return databaseBehavior;
    }
}
