package com.plume.code.web.controller;


import cn.hutool.core.io.FileUtil;
import com.plume.code.core.GeneratorFacade;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.web.controller.vo.GenerateVO;
import com.plume.code.web.controller.vo.R;
import com.plume.code.core.common.helper.PathHelper;
import com.plume.code.core.common.model.TreeNodeModel;
import com.plume.code.core.database.model.ResultModel;
import com.plume.code.web.service.GeneratorService;
import lombok.SneakyThrows;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GeneratorFacade generatorFacade;

    @PostMapping("generate")
    public R<ResultModel> generate(@RequestBody GenerateVO generateVO) {
        ResultModel result = generatorFacade.generate(generateVO.getConnectionModel(), generateVO.getSettingModel());
        return R.ok(result);
    }

    @SneakyThrows
    @RequestMapping("/download")
    public R<Object> downLoad(String batchNo, HttpServletResponse response) {
        String downloadPath = PathHelper.getDownloadPath();
        String filePath = downloadPath.concat(batchNo).concat(".zip");
        File file = new File(filePath);
        if (!file.exists()) {
            return R.fail("file not exists");
        }

        download(response, file);
        return null;
    }

    @GetMapping("/tree")
    public R<TreeNodeModel> tree(String batchNo) {
        String downloadPath = PathHelper.getDownloadPath();
        String filePath = downloadPath.concat(batchNo);
        return R.ok(PathHelper.tree(filePath));
    }

    @SneakyThrows
    @RequestMapping("/content")
    public R<Object> content(String filePath) {
        if (!FileUtil.exist(filePath) || FileUtil.isDirectory(filePath)) {
            return R.fail("file not exists");
        }

        String content = FileUtil.readString(new File(filePath), StandardCharsets.UTF_8);
        return R.ok(content);
    }

    private void download(HttpServletResponse response, File file) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            OutputStream os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer);
                i = bis.read(buffer);
            }
        } finally {
            if (null != bis) {
                bis.close();
            }

            if (null != fis) {
                fis.close();
            }
        }
    }

}
