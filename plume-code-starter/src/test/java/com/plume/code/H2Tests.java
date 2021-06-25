package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.model.BaseColumnModel;
import com.plume.code.model.BaseTableModel;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.SettingModel;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.impl.H2DatabaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H2Tests {

    private ConnectionModel connectionModel;

    private SettingModel settingModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .driver("org.h2.Driver")
                .url("jdbc:h2:~/test")
                .username("sa")
                .password("")
                .build();

        settingModel = SettingModel.builder().author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("plume_")
                .packageName("com.plume.code.demo")
                .projectName("plume-code-demo")
                .build();
    }

    @Test
    public void test() {
        DatabaseService databaseService = H2DatabaseService.instance(connectionModel, settingModel);

        String schema = databaseService.getSchema();
        System.out.println(schema);

        Gson gson = new Gson();

        List<BaseTableModel> tableModels = databaseService.listTableModel();
        System.out.println(gson.toJson(tableModels));

        List<BaseColumnModel> columnModels = databaseService.listColumnModel("TEST");
        System.out.println(gson.toJson(columnModels));
    }
}
