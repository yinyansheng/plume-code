package com.plume.code.core.database.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultModel {
    private String directoryPath;
    private String zipPath;
    private String batchNo;
}
