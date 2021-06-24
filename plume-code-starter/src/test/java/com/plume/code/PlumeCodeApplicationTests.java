package com.plume.code;

import com.google.gson.Gson;
import com.plume.code.model.DatabaseConnectionModel;
import com.plume.code.service.DatabaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlumeCodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlumeCodeApplicationTests {

    private DatabaseConnectionModel databaseConnectionModel;

    @Before
    public void contextLoads() {
        databaseConnectionModel = DatabaseConnectionModel.builder()
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
        JdbcTemplate jdbcTemplate = DatabaseService.instance().getJdbcTemplate(databaseConnectionModel);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from test_user");

        Gson gson = new Gson();
        System.out.println(gson.toJson(maps));
    }
}
