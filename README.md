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
TBD...

## 前端框架
- [d2admin](https://d2.pub/zh/)
- [element-ui](https://element.eleme.cn/2.15/#/zh-CN)





# 二次开发说明
TBD...


# Road Map
## 0.0.1 version
- 支持 mysql、h2   --done
- 支持 element-ui --done
- 支持mybatis、mybatis-plus、jpa    --done
- 支持预览代码、获取代码   --done
## 0.0.2 version
- 支持swagger、lombok
- 支持sqlserver、oracle
- 支持hibernate、tk-Mybatis
- 支持包路径修改(TBD)
- 支持Query 后缀配置
- 支持VO 后缀配置
- 支持DTO后缀配置（DTO,Dto,PoJo）
- 支持Entity后缀配置（PO、ENT、Entity）
## 0.0.3 version
- 支持docker部署（部署plume-code）
- 支持docker compose 部署（含所有数据库类型）
