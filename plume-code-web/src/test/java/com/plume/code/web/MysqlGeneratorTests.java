package com.plume.code.web;

import com.google.gson.Gson;
import com.plume.code.web.core.common.model.ConnectionModel;
import com.plume.code.web.core.common.model.SettingModel;
import com.plume.code.web.core.database.DatabaseBehavior;
import com.plume.code.web.core.database.DatabaseBehaviorFactory;
import com.plume.code.web.core.database.model.ClassModel;
import com.plume.code.web.core.database.model.ContextModel;
import com.plume.code.web.core.database.model.FieldModel;
import com.plume.code.web.core.database.model.ResultModel;
import com.plume.code.web.core.generator.GeneratorBehavior;
import com.plume.code.web.core.generator.GeneratorBehaviorFactory;
import com.plume.code.web.service.DatabaseService;
import com.plume.code.web.service.GeneratorService;
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
@SpringBootTest(classes = PlumeCodeWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MysqlGeneratorTests {

    private ConnectionModel connectionModel;

    private SettingModel settingModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .type("mysql")
                .driver("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://rm-bp1k858sp7ycl11guho.mysql.rds.aliyuncs.com/TCPOI?characterEncoding=utf-8")
                .username("mysqltest_user")
                .password("JswkFxrOy52#4yX1GNAd7")
                .build();

        settingModel = SettingModel.builder()
                .batchNo(String.valueOf(System.currentTimeMillis()))
                .author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("xc_crm_")
                .basePackageName("com.plume.code")
                .projectName("plume-code")
                .tableNameSet(new HashSet<>(Arrays.asList("xc_crm_admin")))
                .templateNameSet(new HashSet<>(Arrays.asList(
                        "Jpa-Repository.java.tpl"
//                        "ElementUi-api.js.tpl",
//                        "ElementUi-Dialog.vue.tpl",
//                        "ElementUi-index.js.tpl",
//                        "Mybatis-Mapper.xml.tpl"
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
        List<FieldModel> fieldModels = databaseBehavior.listFieldModel(settingModel, "test_student");

        ContextModel contextModel = ContextModel.builder()
                .settingModel(settingModel)
                .classModel(classModels.get(0))
                .fieldModelList(fieldModels)
                .build();

//        GeneratorBehavior serviceImplGeneratorBehavior = generatorBehaviorFactory
//                .getGeneratorBehavior("serviceImpl", contextModel);
//
//        GeneratorBehavior serviceGeneratorBehavior = generatorBehaviorFactory
//                .getGeneratorBehavior("service", contextModel);
//
//        serviceGeneratorBehavior.generate();
//        serviceImplGeneratorBehavior.generate();

        GeneratorBehavior mybatisMapper = generatorBehaviorFactory.getGeneratorBehavior("Mybatis-ENT.java.tpl", contextModel);
        mybatisMapper.generate();

    }


}
