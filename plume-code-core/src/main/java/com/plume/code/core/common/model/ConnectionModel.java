package com.plume.code.core.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
