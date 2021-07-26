package com.plume.code.cli.test;

import com.google.gson.Gson;
import com.plume.code.cli.PlumeCodeCliApplication;
import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.DatabaseBehavior;
import com.plume.code.core.database.DatabaseBehaviorFactory;
import com.plume.code.core.database.model.ClassModel;
import com.plume.code.core.database.model.FieldModel;
import lombok.SneakyThrows;
import oracle.jdbc.OracleDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeCliApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeGenerator {

    private ConnectionModel connectionModel;

    private SettingModel settingModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .type("PostgreSQL")
                .driver("org.postgresql.Driver")
                .url("jdbc:postgresql://127.0.0.1:5432/plume_pg")
                .username("postgres")
                .password("123456")
                .build();

        settingModel = SettingModel.builder().author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("plume_")
                .basePackageName("com.plume.code.demo")
                .projectName("plume-code-demo")
                .build();
    }

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Test
    public void test() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel);
        System.out.println(databaseBehavior);

        String schema = databaseBehavior.getDatabaseName();
        System.out.println(schema);

        Gson gson = new Gson();

        List<String> tableNames = databaseBehavior.listTableName();
        System.out.println(gson.toJson(tableNames));

        List<ClassModel> tableModels = databaseBehavior.listClassModel(settingModel);
        System.out.println(gson.toJson(tableModels));

        Set<String> primaryKeySet = databaseBehavior.getPrimaryKeySet("test_user");
        System.out.println(gson.toJson(primaryKeySet));

        List<FieldModel> columnModels = databaseBehavior.listFieldModel(settingModel, "test_user");
        System.out.println(gson.toJson(columnModels));
    }

    @Test
    @SneakyThrows
    public void test2() {
        Driver driver = new OracleDriver();
        DriverManager.deregisterDriver(driver);
    }

}
