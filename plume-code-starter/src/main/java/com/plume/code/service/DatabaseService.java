package com.plume.code.service;

import com.plume.code.common.model.ConnectionModel;

import java.util.List;

public interface DatabaseService {
    /**
     * test jdbc connection
     *
     * @param connectionModel parameters
     * @return
     */
    boolean testConnection(ConnectionModel connectionModel);

    List<String> listTableName(ConnectionModel connectionModel);
}
