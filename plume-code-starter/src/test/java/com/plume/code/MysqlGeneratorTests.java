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
import com.plume.code.service.DatabaseService;
import com.plume.code.service.GeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
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
                .tableNameSet(new HashSet<>(Arrays.asList("test_user", "test_student", "test_url")))
                .templateNameSet(new HashSet<>(Arrays.asList(
//                        "ElementUi-api.js.tpl",
//                        "ElementUi-Dialog.vue.tpl",
//                        "ElementUi-index.js.tpl",
                        "ElementUi-Main.vue.tpl"
//                        "ElementUi-object.js.tpl",
//                        "ElementUi-Search.vue.tpl",
//                        "ElementUi-Table.vue.tpl",
//                        "MybatisPlus-ENT.java.tpl",
//                        "Service.java.tpl",
//                        "ServiceImpl.java.tpl",
//                        "MybatisPlus-Mapper.java.tpl",
//                        "MybatisPlus-Service.java.tpl",
//                        "MybatisPlus-ServiceImpl.java.tpl",
//                        "MybatisPlus-Mapper.xml.tpl",
//                        "DTO.java.tpl"
                )))
                .lombokState(true)
                .build();
    }

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    private GeneratorBehaviorFactory generatorBehaviorFactory;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void test6() {
        List<String> tableNameList = databaseService.listTableName(connectionModel);
        System.out.println(tableNameList);
    }

    @Test
    public void test4() {
        ResultModel resultModel = generatorService.generate(connectionModel, settingModel);
        System.out.println(resultModel);
    }

    @Test
    public void test5() {
        System.out.println(new Date());
        boolean connect = databaseService.testConnection(connectionModel);
        System.out.println(new Date());
        boolean connect2 = databaseService.testConnection(connectionModel);
        System.out.println(new Date());
    }


    @Test
    public void test3() {
        List<GeneratorBehavior> generatorBehaviorList = generatorService.getGeneratorBehaviorList(connectionModel, settingModel);
        generatorBehaviorList.forEach(GeneratorBehavior::generate);
    }

    @Test
    public void test2() {
        List<ContextModel> contextModelList = generatorService.getContextModelList(connectionModel, settingModel);

        contextModelList.forEach(contextModel -> {
            List<GeneratorBehavior> generatorBehaviorList = generatorBehaviorFactory.getGeneratorBehaviorList(contextModel);
            generatorBehaviorList.forEach(GeneratorBehavior::generate);
        });
    }

    @Test
    public void test() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel);
        System.out.println(databaseBehavior);

        String schema = databaseBehavior.getDatabaseName();
        System.out.println(schema);

        Gson gson = new Gson();

        List<ClassModel> classModels = databaseBehavior.listClassModel(settingModel);
        List<FieldModel> fieldModels = databaseBehavior.listFieldModel(settingModel, "SMART_USER");

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
