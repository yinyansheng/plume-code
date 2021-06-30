package com.plume.code.lib.database.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultModel {
    private String directoryPath;
    private String zipPath;
}
