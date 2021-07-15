# PlumeCode
一个简单易用且功能强大的代码生成器(Java & D2Admin(Base on Vue、ElementUI)),熟练使用你就是CURD代码挖掘机！！！

PlumeCode 不仅简单、而且强大
- 支持多数据库Mysql、H2（SqlServer、Oracle、PostgreSQL TBD...）
- 支持多种持久层框架MybatisPlus、Mybatis、Jpa (Hibernate、tk-Mybatis TBD...)
- 一键下载拥有portal、controller、service、repository、mapper、dto、entity、vo、query、mapper.xml等前后端代码
- 良好的项目结构，让二次开发变得像喝水一样简单

# 使用文档
![add database connection](/plume-code-starter/src/main/resources/images/img1.png)
![generator setting](/plume-code-starter/src/main/resources/images/img2.png)
![download and preview](/plume-code-starter/src/main/resources/images/img3.png)

# 生成代码结构
## 三层架构
- controller layer(controller、vo、query)
- servicer layer(service、serviceImpl、dto)
- dao layer(repository/mapper/dao、repositoryImpl/daoImpl、entity)

## 前端框架
- [d2admin](https://d2.pub/zh/)
- [element-ui](https://element.eleme.cn/2.15/#/zh-CN)



# Road Map
## 0.0.1 version
- 支持 mysql、h2   --done
- 支持 element-ui --done
- 支持mybatis、mybatis-plus、jpa    --done
- 支持预览代码、获取代码   --done
## 0.0.2 version
- 支持联合主键
- 支持sqlserver、oracle
- 支持hibernate、tk-Mybatis
- 支持Query 后缀配置
- 支持VO 后缀配置
- 支持DTO后缀配置
- 支持Entity后缀配置
## 0.0.3 version
- 支持docker部署（部署plume-code）
- 支持docker compose 部署（含所有数据库类型）
## 0.0.4 version
- 支持包路径修改
- 支持swagger、lombok
- 配置基于xml、yml配置
- 编写dtd约束文件
- 用户自定义配置文件


# 二次开发说明
TBD...
