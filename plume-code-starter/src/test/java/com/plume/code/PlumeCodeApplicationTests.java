package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.model.ColumnModel;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.TableModel;
import com.plume.code.service.DatabaseService;
import com.plume.code.service.MysqlDatabaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


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
        System.out.println("1111");
    }

    @Test
    public void test() {
        DatabaseService databaseService = MysqlDatabaseService.instance(connectionModel);

        String schema = databaseService.getSchema();
        System.out.println(schema);

        Gson gson = new Gson();

        List<TableModel> tableModels = databaseService.listTableModel();
        System.out.println(gson.toJson(tableModels));

        List<ColumnModel> columnModels = databaseService.listColumnModel("test_user");
        System.out.println(gson.toJson(columnModels));
    }
}
