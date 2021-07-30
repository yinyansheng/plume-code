package com.plume.code.core.common.helper;

import cn.hutool.core.io.FileUtil;
import com.plume.code.core.common.model.TreeNodeModel;
import org.h2.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinyansheng
 */
public class PathHelper {

    public static String getDownloadPath(String downloadPath) {
        if (StringUtils.isNullOrEmpty(downloadPath)) {
            return getDownloadPath();
        }

        return downloadPath;
    }

    public static String getDownloadPath() {
        if (!"dev".equals(System.getProperty("env"))) {
            String userDirPath = System.getProperty("user.dir");
            return userDirPath.concat("/downloads/");
        }

        URL resource = PathHelper.class.getClassLoader().getResource("");

        if (null == resource) {
            throw new RuntimeException("can't find the resource URL");
        }

        return resource.getPath().concat("downloads/");
    }

    public static TreeNodeModel tree(String directoryPath) {
        if (FileUtil.exist(directoryPath) && !FileUtil.isDirectory(directoryPath)) {
            throw new RuntimeException("directory not exists :" + directoryPath);
        }

        TreeNodeModel root = new TreeNodeModel(new File(directoryPath));
        tree(directoryPath, root);
        return root;
    }

    private static void tree(String path, TreeNodeModel parent) {
        if (!FileUtil.isDirectory(path) || FileUtil.isEmpty(new File(path))) {
            return;
        }

        List<TreeNodeModel> list = new ArrayList<>();
        for (File file : FileUtil.ls(path)) {
            TreeNodeModel treeNodeModel = new TreeNodeModel(file);
            list.add(treeNodeModel);
            tree(file.getPath(), treeNodeModel);
        }

        parent.setChildren(list);
    }
}
