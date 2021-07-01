package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.common.bean.PathHandler;
import com.plume.code.common.model.TreeNodeModel;
import org.junit.Test;

public class CommonTests {

    public static void main(String[] args) {
        TreeNodeModel tree = PathHandler.tree("/Users/yinyansheng/gitee.com/plume-code/plume-code-starter/target/test-classes/downloads/1625109697899/plume-code");
        Gson gson = new Gson();
        System.out.println(gson.toJson(tree));

    }
}
