package com.plume.code.lib.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CodeFileTreeModel {
    private String fileName;
    private String filePath;
    private List<CodeFileTreeModel> children = new ArrayList<>();
}
