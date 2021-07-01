package com.plume.code.controller;


import com.plume.code.common.bean.PathHandler;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.controller.vo.GenerateVO;
import com.plume.code.controller.vo.R;
import com.plume.code.lib.database.model.CodeFileTreeModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.GeneratorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("plume")
public class PlumeController {

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private PathHandler pathHandler;

    @PostMapping("/test")
    public R<Object> test(@RequestBody ConnectionModel connectionModel) {
        databaseService.testConnection(connectionModel);
        return R.OK;
    }

    @PostMapping("listTables")
    public R<List<String>> listTable(@RequestBody ConnectionModel connectionModel) {
        return R.ok(databaseService.listTableName(connectionModel));
    }

    @PostMapping("generate")
    public R<ResultModel> generate(@RequestBody GenerateVO generateVO) {
        generateVO.getSettingModel().setBatchNo(String.valueOf(System.currentTimeMillis()));
        ResultModel result = generatorService.generate(generateVO.getConnectionModel(), generateVO.getSettingModel());
        return R.ok(result);
    }

    @GetMapping("codeTree")
    public R<Object> codeTree(String batchNo) {
        CodeFileTreeModel codeFileTree =  generatorService.getCodeFileTree(batchNo);
        return R.ok(codeFileTree);
    }

    @GetMapping("codeInfo")
    public R<Object> codeInfo(String filePath) {
        return R.OK;
    }

    @SneakyThrows
    @RequestMapping("/download")
    public R<Object> downLoad(String batchNo, HttpServletResponse response) {
        String downloadPath = pathHandler.getDownloadPath();
        String filePath = downloadPath.concat(batchNo).concat(".zip");
        File file = new File(filePath);
        if (!file.exists()) {
            return R.fail("file not exists");
        }

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        OutputStream os = response.getOutputStream();
        fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis);
        int i = bis.read(buffer);
        while (i != -1) {
            os.write(buffer);
            i = bis.read(buffer);
        }

        bis.close();
        fis.close();
        return R.ok("success");
    }
}
