package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.common.model.*;
import com.plume.code.lib.database.DatabaseBehavior;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.sql.JDBCType.BIT;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MysqlTests {

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

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Test
    public void test() {
        SettingModel settingModel = SettingModel.builder()
                .author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("test_")
                .basePackageName("com.plume.code")
                .projectName("plume-code")
                .build();

        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);

        String schema = databaseBehavior.getDatabaseName();
        System.out.println(schema);

        Gson gson = new Gson();

        List<ClassModel> tableModels = databaseBehavior.listTableModel();
        System.out.println(gson.toJson(tableModels));

        List<FieldModel> columnModels = databaseBehavior.listFieldModel("test_user");
        System.out.println(gson.toJson(columnModels));

    }
}
