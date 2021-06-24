package com.plume.code.service;

import com.plume.code.model.ColumnModel;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.TableModel;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * database service
 * get jdbc template
 *
 * @author yinyansheng
 */
public abstract class DatabaseService {
    protected ConnectionModel connectionModel;

    protected DatabaseService(ConnectionModel connectionModel) {
        this.connectionModel = connectionModel;
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        //设置相应的参数
        //1、数据库驱动类
        dataSource.setDriverClassName(connectionModel.getDriver());
        //2、url，用户名，密码
        dataSource.setUrl(connectionModel.getUrl());
        dataSource.setUsername(connectionModel.getUsername());
        dataSource.setPassword(connectionModel.getPassword());
        //3、初始化连接大小
        dataSource.setInitialSize(1);
        //4、连接池最大数据量
        dataSource.setMaxTotal(500);
        //5、连接池最大小空闲
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(20);
        //6、最大等待时间 单位毫秒
        dataSource.setMaxWaitMillis(20_000);
        //7、指明连接是否被空闲连接回收器(如果有)进行检验
        dataSource.setPoolPreparedStatements(true);
        //8、运行一次空闲连接回收器的时间间隔（60秒）
        dataSource.setTimeBetweenEvictionRunsMillis(60_000);
        //9、验证时使用的SQL语句
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        //10、借出连接时不要测试，否则很影响性能
        //11、申请连接的时候检测，如果空闲时间大于  timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(false);
        return dataSource;
    }

    public abstract String getSchema();

    public abstract List<TableModel> listTableModel();

    public abstract List<ColumnModel> listColumnModel(String tableName);

}
