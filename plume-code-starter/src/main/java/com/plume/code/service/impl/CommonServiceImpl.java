package com.plume.code.service.impl;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.service.CommonService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class CommonServiceImpl implements CommonService {

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
}
