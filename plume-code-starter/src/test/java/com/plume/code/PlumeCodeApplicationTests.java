package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.model.*;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.impl.MysqlDatabaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.sql.JDBCType.BIT;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlumeCodeApplicationTests {

    private ConnectionModel connectionModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .driver("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://10.102.126.18:3306/yugo_test?characterEncoding=utf-8")
                .username("root")
                .password("nice!123")
                .build();
    }

    @Test
    public void test2() {
        System.out.println(BIT.getName());
    }

    @Test
    public void test() {
        SettingModel settingModel = SettingModel.builder().author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("test_")
                .packageName("com.plume.code")
                .projectName("plume-code")
                .build();

        DatabaseService databaseService = MysqlDatabaseService.instance(connectionModel, settingModel);

        String schema = databaseService.getSchema();
        System.out.println(schema);

        Gson gson = new Gson();

        List<BaseTableModel> tableModels = databaseService.listTableModel();
        System.out.println(gson.toJson(tableModels));

        List<BaseColumnModel> columnModels = databaseService.listColumnModel("test_user");
        System.out.println(gson.toJson(columnModels));

    }
}
