package com.plume.code.web.service;

import com.plume.code.web.core.common.model.ConnectionModel;

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
