package com.plume.code.common.bean;

import cn.hutool.core.io.FileUtil;
import com.plume.code.common.model.TreeNodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinyansheng
 */
@Component
public class PathHandler {

    @Autowired
    private Environment env;

    public String getDownloadPath() {
        if ("prod".contains(env.getProperty("spring.profiles.active"))) {
            String userDirPath = System.getProperty("user.dir");
            return userDirPath.concat("/downloads/");
        }

        URL resource = PathHandler.class.getClassLoader().getResource("");

        if (null == resource) {
            throw new RuntimeException("can't find the resource URL");
        }

        return resource.getPath().concat("downloads/");
    }

    public static TreeNodeModel tree(String directoryPath) {
        if (FileUtil.exist(directoryPath) && !FileUtil.isDirectory(directoryPath)) {
            throw new RuntimeException("directory not exists :" + directoryPath);
        }

        String name = FileUtil.getName(directoryPath);
        TreeNodeModel root = TreeNodeModel.builder()
                .path(directoryPath)
                .isDirectory(true)
                .name(name)
                .build();

        tree(directoryPath, root);
        return root;
    }

    private static void tree(String path, TreeNodeModel parent) {
        if (!FileUtil.isDirectory(path) || FileUtil.isEmpty(new File(path))) {
            return;
        }

        List<TreeNodeModel> list = new ArrayList<>();
        for (File file : FileUtil.ls(path)) {
            TreeNodeModel treeNodeModel = TreeNodeModel.builder()
                    .path(file.getPath())
                    .isDirectory(file.isDirectory())
                    .name(file.getName())
                    .build();

            list.add(treeNodeModel);
            tree(file.getPath(), treeNodeModel);
        }

        parent.setSubTreeNodeModelList(list);
    }
}
