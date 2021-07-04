package com.plume.code.controller;


import com.plume.code.common.model.ConnectionModel;
import com.plume.code.controller.vo.R;
import com.plume.code.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/testConnection")
    public R<Object> testConnection(@RequestBody ConnectionModel connectionModel) {
        databaseService.testConnection(connectionModel);
        return R.OK;
    }

    @PostMapping("listTableName")
    public R<List<String>> listTableName(@RequestBody ConnectionModel connectionModel) {
        return R.ok(databaseService.listTableName(connectionModel));
    }

}
