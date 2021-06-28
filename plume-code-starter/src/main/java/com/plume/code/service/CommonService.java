package com.plume.code.service;

import com.plume.code.common.model.ConnectionModel;

public interface CommonService {
    /**
     * test jdbc connection
     *
     * @param connectionModel parameters
     * @return
     */
    boolean testConnection(ConnectionModel connectionModel);
}
