package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.common.helper.PathHelper;
import com.plume.code.common.model.TreeNodeModel;

public class CommonTests {

    public static void main(String[] args) {
        TreeNodeModel tree = PathHelper.tree("/Users/yinyansheng/gitee.com/plume-code/plume-code-starter/target/test-classes/downloads/1625109697899/plume-code");
        Gson gson = new Gson();
        System.out.println(gson.toJson(tree));

    }
}
