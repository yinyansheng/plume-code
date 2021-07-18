# Plume-Code

一个简单易用且功能强大的代码生成器(Java & ElementUI),熟练使用你就是CURD代码挖掘机！！！

     **⭐⭐⭐新项目，需要大家的支持，欢迎Star、PR、Issue⭐⭐⭐** 

PlumeCode 不仅简单、而且强大

- 支持多数据库Mysql、H2、SqlServer、Oracle、PostgreSQL
- 支持多种持久层框架MybatisPlus、Mybatis、Jpa、tk-Mybatis、Hibernate
- 一键下载拥有portal、controller、service、repository、mapper、dto、entity、vo、query、mapper.xml等前后端代码
- 良好的项目结构，让二次开发变得像喝水一样简单

## 快速开始

### 下载文件
- 发行版[下载](https://gitee.com/yansheng/plume-code/releases/0.0.2)
- 直接[下载](https://gitee.com/yansheng/plume-code/raw/main/assemble/plume-code-starter-0.0.2.jar)

### 启动项目
`java -Dspring.profiles.active=prod -jar plume-code-starter-0.0.2.jar`   
参数说明：jar打包方式，文件位置不能使用ResourcePath或ClassPath,所以要加prod来区别 
![start](/image/0.png)

### 功能简介

- 打开网页
  http://localhost:8844/web/index.html
- 数据库配置
  ![start](/image/1.png)
  ![database](/image/3.png)
- 生成代码
  ![generator](/image/2.png)
- 代码预览
  ![preview](/image/4.png)

## 生成代码示例
[plume-code-sample](https://gitee.com/yansheng/plume-code-sample)

## 生成代码结构

### 后端框架
对于java后端项目框架设计，目前主要有两种：  
-- 面向领域（DDD）
-- 面向数据的传统三层框架  

一般而言互联网后端C端业务并不复杂（相对B端或企业级项目），且通常也做了微服务，业务边界划分良好的话，DDD并不是一个好的选择，所以本项目是基于传统三层框架的代码生成下列后端代码  
- controller层(controller、vo、query)
- servicer层(service、serviceImpl、dto)
- repository层(repository/mapper/dao、repositoryImpl/daoImpl、entity)  

下面是我常用的系统分层设计（参考自阿里巴巴代码规约）  
![project layer](/image/6.png)  
以及我常用的包结构设计  
![package](/image/7.png)  
**以上都是个人理解，对于系统设计，没有银弹，适合的，符合场景的实践才是好的**


### 前端框架
由于前端代码公司间差异比较大，这里只是做了ElementUI的示例，如有需要，可根据实际前端技术选型，做二次开发集成  
- [d2admin](https://d2.pub/zh/)
- [element-ui](https://element.eleme.cn/2.15/#/zh-CN)

## Road Map

### 0.0.1 version

- 支持 mysql、h2 --done
- 支持 element-ui --done
- 支持mybatis、mybatis-plus、jpa --done
- 支持预览代码、获取代码 --done

### 0.0.2 version

- 支持联合主键 --done
- 支持sqlserver、oracle、PostgreSQL --done
- 支持hibernate、tk-Mybatis --done
- 支持Query 后缀配置   --done
- 支持VO 后缀配置  --done
- 支持DTO后缀配置  --done
- 支持Entity后缀配置   --done

### 0.0.3 version
- 支持包路径修改
- 基于xml(dtd)配置执行
- 用户自定义配置文件

### 0.0.4 version

- 支持docker部署（部署plume-code）
- 支持docker compose 部署（含所有数据库类型）


## 项目结构

 ![framework](/image/5.png)


## 二开说明

### 新增数据库

项目本身是基于JDBC获取数据库信息,数据库需要有JDBC实现

获取数据信息有两种方式

- 通过JDBC Connection 获取DatabaseMetaData,从而获取数据库、表、字段、主键信息（推荐），只需要继承MetaDataDatabaseBehavior，且提供一个获取数据库名称的SQL即可
- 通过JDBC执行Raw SQL获取数据库、表、字段、主键信息

![database behavior class](/image/10.png)

Sample：新增PostgreSQL

```java
@Component
@Scope(value = "prototype")
public class PostgreSQLDatabaseBehavior extends MetaDataDatabaseBehavior {

    @Override
    protected String getDatabaseNameSql() {
        return "SELECT current_database();";
    }
}
```

### 新增模板
![tempalte behavior class](/image/11.png)
默认使用Velocity模板引擎、FreeMarker可选  
新增模板文件  
![template file](/image/12.png)

新增后端java、xml模板实现类，继承JavaGeneratorBehavior类
```java
@Component
@Scope("prototype")
class JpaControllerGeneratorBehavior extends JavaGeneratorBehavior {
	
    //获取模板文件名
    @Override
    protected String getTemplateName() {
        return "Jpa-Controller.java.tpl";
    }
	
    //获取文件包名（也是文件相对路径）
    @Override
    protected String getPackageName() {
        return settingModel.getBasePackageName().concat(".admin.controller");
    }

    //获取文件名
    @Override
    protected String getFileName() {
        return String.format("%sController.java", upperFirstCase(classModel.getName()));
    }
}
```



新增前端js、vue模板实现类，继承VueGeneratorBehavior类
```java
@Component
@Scope("prototype")
public class ElementUiTableGeneratorBehavior extends VueGeneratorBehavior {
    //文件路径在基类中已经实现，如有调整需要重写父类getFilePath方法
    
    //获取文件名
    @Override
    protected String getFileName() {
        return "Table.vue";
    }
	
    //获取模板文件名
    @Override
    protected String getTemplateName() {
        return "ElementUi-Table.vue.tpl";
    }
}
```

### 模板上下文变量

```java
protected Map<String, Object> getTemplateContext() {
    Map<String, Object> templateContext = new HashMap<>(32);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    templateContext.put("setting", settingModel);
    templateContext.put("createTime", simpleDateFormat.format(new Date()));
    templateContext.put("className", classModel.getName());
    templateContext.put("ClassName", upperFirstCase(classModel.getName()));
    templateContext.put("tableName", classModel.getTableName());
    templateContext.put("author", settingModel.getAuthor());
    templateContext.put("comment", classModel.getComment());
    templateContext.put("lombok", settingModel.getLombokState());
    templateContext.put("fieldModelList", fieldModelList);
    templateContext.put("isMultiplePK", fieldModelList.stream().filter(FieldModel::isPk).count() > 1);
    templateContext.put("primaryKeyList", fieldModelList.stream().filter(FieldModel::isPk).collect(Collectors.toList()));

    return templateContext;
}
```

如果有额外的变量，可以在模板实现类中重写基类getTemplateContext方法

```java
@Override
protected Map<String, Object> getTemplateContext() {
    Map<String, Object> templateContext = super.getTemplateContext();

    templateContext.put("basePackageName", settingModel.getBasePackageName());
    templateContext.put("packageName", getPackageName());

    String servicePackageName = settingModel.getBasePackageName().concat(".service");
    templateContext.put("servicePackageName", servicePackageName);

    templateContext.put("typePackageNameList", getTypePackageNameList());
    return templateContext;
}
```

### 修改portal页面

略





