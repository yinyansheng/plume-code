package com.plume.code.common.helper;

import java.net.URL;

/**
 * @author yinyansheng
 */
public class PathHelper {
    public static String getDownloadPath() {
        if ("prod".contains(System.getProperty("spring.profiles.active"))) {
            String userDirPath = System.getProperty("user.dir");
            return userDirPath.concat("/downloads");
        }

        URL resource = PathHelper.class.getClassLoader().getResource("");

        if (null == resource) {
            throw new RuntimeException("can't find the resource URL");
        }

        return resource.getPath().concat("downloads");
    }
}
