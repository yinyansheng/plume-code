package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.lib.database.model.ContextModel;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.FieldModel;
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
import java.util.HashSet;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H2GeneratorTests {

    private ConnectionModel connectionModel;

    private SettingModel settingModel;

    @Before
    public void contextLoads() {
        connectionModel = ConnectionModel.builder()
                .type("h2")
                .driver("org.h2.Driver")
                .url("jdbc:h2:~/plume_test")
                .username("sa")
                .password("")
                .build();

        settingModel = SettingModel.builder()
                .batchNo(String.valueOf(System.currentTimeMillis()))
                .author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("plume_")
                .basePackageName("com.plume.code")
                .projectName("plume-code")
                .tableNameSet(new HashSet<>(Arrays.asList("SMART_USER")))
                .serviceMode(1)
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
    public void test5() {
        List<String> tableNameList = databaseService.listTableName(connectionModel);
        System.out.println(tableNameList);
    }

    @Test
    public void test4() {
        generatorService.generate(connectionModel, settingModel);
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
