package com.plume.code.cli.test;

import com.plume.code.cli.PlumeCodeCliApplication;
import com.plume.code.core.GeneratorFacade;
import com.plume.code.core.common.enums.ControllerOption;
import com.plume.code.core.common.enums.PortalOption;
import com.plume.code.core.common.enums.RepositoryOption;
import com.plume.code.core.common.enums.ServiceOption;
import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.GeneratorConfigModel;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.model.ResultModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeCliApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeGenerator {

    @Autowired
    private GeneratorFacade generatorFacade;

    private GeneratorConfigModel setConnectionModel;

    @Before
    public void prepare() {
        ConnectionModel connectionModel = ConnectionModel.builder()
                .type("h2")
                .driver("org.h2.Driver")
                .url("jdbc:h2:~/h2_test")
                .username("sa")
                .password("")
                .build();

        SettingModel settingModel = SettingModel.builder().author("yinyansheng")
                .columnPrefix("s_")
                .tablePrefix("plume_")
                .basePackageName("com.plume.code.demo")
                .projectName("plume-code-demo")
                .tableNameSet(new HashSet<>(Arrays.asList("RIGHTS", "VIEWS")))
                .templateNameSet(new HashSet<>(Arrays.asList("Jpa-ENT.java.ftl", "Controller.java.tpl")))
                .build();

        setConnectionModel = new GeneratorConfigModel();
        setConnectionModel.setConnectionModel(connectionModel);
        setConnectionModel.setSettingModel(settingModel);
        setConnectionModel.setPortal(Arrays.asList(PortalOption.ElementUI));
        setConnectionModel.setController(Arrays.asList(ControllerOption.Controller, ControllerOption.Query, ControllerOption.VO));
        setConnectionModel.setService(Arrays.asList(ServiceOption.Service, ServiceOption.DTO));
        setConnectionModel.setRepository(RepositoryOption.MybatisPlus);
    }

    @Test
    public void generate() {
        ResultModel generate = generatorFacade.generate(setConnectionModel);
    }
}
