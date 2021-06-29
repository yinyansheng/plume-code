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
        Connection connection = null;
        try {
            Class.forName(connectionModel.getDriver());
            connection = DriverManager.getConnection(
                    connectionModel.getUrl(),
                    connectionModel.getUsername(),
                    connectionModel.getPassword());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
