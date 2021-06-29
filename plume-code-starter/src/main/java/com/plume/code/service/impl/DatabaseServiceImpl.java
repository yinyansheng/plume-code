package com.plume.code.service.impl;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Override
    public boolean testConnection(ConnectionModel connectionModel) {
        try {
            Class.forName(connectionModel.getDriver());
            Connection connection = DriverManager.getConnection(
                    connectionModel.getUrl(),
                    connectionModel.getUsername(),
                    connectionModel.getPassword());

            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> listTableName(ConnectionModel connectionModel) {
        return databaseBehaviorFactory.getDatabaseBehavior(connectionModel).listTableName();
    }
}
