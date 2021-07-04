package com.plume.code.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeNodeModel {

    public TreeNodeModel(File file) {
        this.path = file.getPath();
        this.isDirectory = file.isDirectory();
        this.name = file.getName();
    }

    private String path;
    private String name;
    private String icon;
    private boolean isDirectory;

    private List<TreeNodeModel> children;
}
