package com.plume.code.common.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URL;

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
}
