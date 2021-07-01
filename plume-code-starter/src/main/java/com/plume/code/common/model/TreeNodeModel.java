package com.plume.code.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeNodeModel {
    private String path;
    private String name;
    private boolean isDirectory;

    private List<TreeNodeModel> subTreeNodeModelList;
}
