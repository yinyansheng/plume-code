package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.common.context.GeneratorContext;
import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.DatabaseBehavior;
import com.plume.code.lib.database.DatabaseBehaviorFactory;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.generator.GeneratorBehavior;
import com.plume.code.lib.generator.GeneratorBehaviorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneratorTests {

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
                .lombokState(true)
                .build();
    }

    @Autowired
    private DatabaseBehaviorFactory databaseBehaviorFactory;

    @Autowired
    private GeneratorBehaviorFactory generatorBehaviorFactory;

    @Test
    public void test() {
        DatabaseBehavior databaseBehavior = databaseBehaviorFactory.getDatabaseBehavior(connectionModel, settingModel);
        System.out.println(databaseBehavior);

        String schema = databaseBehavior.getDatabaseName();
        System.out.println(schema);

        Gson gson = new Gson();

        List<ClassModel> classModels = databaseBehavior.listTableModel();
        List<FieldModel> fieldModels = databaseBehavior.listFieldModel("SMART_USER");

        GeneratorContext generatorContext = GeneratorContext.builder()
                .settingModel(settingModel)
                .classModel(classModels.get(0))
                .fieldModelList(fieldModels)
                .build();

        GeneratorBehavior serviceImplGeneratorBehavior = generatorBehaviorFactory
                .getGeneratorBehavior("serviceImpl", generatorContext);

        GeneratorBehavior serviceGeneratorBehavior = generatorBehaviorFactory
                .getGeneratorBehavior("service", generatorContext);

        serviceGeneratorBehavior.generate();
        serviceImplGeneratorBehavior.generate();

    }
}
