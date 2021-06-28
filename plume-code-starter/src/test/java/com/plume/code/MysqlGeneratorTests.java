package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.common.constrant.SettingConstant;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.ResultModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import com.plume.code.lib.generator.GeneratorBehaviorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MysqlGeneratorTests {

    private ConnectionModel connectionModel;

    private SettingModel settingModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .type("mysql")
                .driver("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://10.102.126.18:3306/yugo_test?characterEncoding=utf-8")
                .username("root")
                .password("nice!123")
                .build();

        settingModel = SettingModel.builder()
                .batchNo(String.valueOf(System.currentTimeMillis()))
                .author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("test_")
                .basePackageName("com.plume.code")
                .projectName("plume-code")
                .tableNameSet(new HashSet<>(Arrays.asList("test_user")))
                .serviceMode(SettingConstant.ServiceMode.SERVICE)
                .lombokState(true)
                .build();
    }

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    private GeneratorBehaviorFactory generatorBehaviorFactory;

    @Test
    public void test4() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);
        ResultModel resultModel = databaseBehavior.generate();
        System.out.println(resultModel);
    }

    @Test
    public void test3() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);
        List<GeneratorBehavior> generatorBehaviorList = databaseBehavior.getGeneratorBehaviorList();
        generatorBehaviorList.forEach(GeneratorBehavior::generate);
    }

    @Test
    public void test2() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);
        List<ContextModel> contextModelList = databaseBehavior.getContextModelList();

        contextModelList.forEach(contextModel -> {
            List<GeneratorBehavior> generatorBehaviorList = generatorBehaviorFactory.getGeneratorBehaviorList(contextModel);
            generatorBehaviorList.forEach(GeneratorBehavior::generate);
        });
    }

    @Test
    public void test() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);
        System.out.println(databaseBehavior);

        String schema = databaseBehavior.getDatabaseName();
        System.out.println(schema);

        Gson gson = new Gson();

        List<ClassModel> classModels = databaseBehavior.listTableModel();
        List<FieldModel> fieldModels = databaseBehavior.listFieldModel("SMART_USER");

        ContextModel contextModel = ContextModel.builder()
                .settingModel(settingModel)
                .classModel(classModels.get(0))
                .fieldModelList(fieldModels)
                .build();

        GeneratorBehavior serviceImplGeneratorBehavior = generatorBehaviorFactory
                .getGeneratorBehavior("serviceImpl", contextModel);

        GeneratorBehavior serviceGeneratorBehavior = generatorBehaviorFactory
                .getGeneratorBehavior("service", contextModel);

        serviceGeneratorBehavior.generate();
        serviceImplGeneratorBehavior.generate();

    }
}
