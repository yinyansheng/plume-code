package com.plume.code.controller;


import com.plume.code.common.model.ConnectionModel;
import com.plume.code.controller.vo.R;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("plume")
public class GreetingController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "1") String name, Model model) {
        //这里返回的数据类型是String，但实际上更多的数据通过本函数中Model model传给了前端。返回值String也会被SpringMVC整合为一个ModelAndView，以供前端使用。（Controller可以返回多种数值，比如void、String、ModelAndView。同学们可以自行搜索学习）
        return "greeting";
    }

    @PostMapping("/test")
    public R<Object> test(@RequestBody ConnectionModel connectionModel) {
        commonService.testConnection(connectionModel);
        return R.OK;
    }

    @PostMapping("listTables")
    public List<ClassModel> listTable(@RequestBody ConnectionModel connectionModel) {

        return Collections.emptyList();
    }
}
