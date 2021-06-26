package com.plume.code.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionModel {
    /**
     * mysql
     * h2
     */
    private String type;
    private String driver;
    private String url;
    private String username;
    private String password;
}
