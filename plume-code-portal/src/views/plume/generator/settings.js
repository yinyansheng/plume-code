export const templateMap = {
  mybatisPlus: ['MybatisPlus-ENT.java.tpl', 'MybatisPlus-Mapper.java.tpl'],
  jpa: ['JPA-ENT.java.tpl', 'JPA-Mapper.java.tpl'],
  mybatis: ['Mybatis-ENT.java.tpl','Mybatis-Mapper.xml.tpl'],
  service: {
    0: ['Service.java.tpl', 'ServiceImpl.java.tpl'],
    mybatisPlus: ['MybatisPlus-Service.java.tpl', 'MybatisPlus-ServiceImpl.java.tpl'],
    jpa: ['JPA-Service.java.tpl', 'JPA-ServiceImpl.java.tpl'],
    mybatis: ['Mybatis-Service.java.tpl', 'Mybatis-ServiceImpl.java.tpl']
  },
  controller: {
    0: ['Controller.java.tpl'],
    mybatisPlus: ['MybatisPlus-Controller.java.tpl'],
    jpa: [],
    mybatis: ['Mybatis-Controller.java.tpl']
  },
  portal: ['ElementUi-api.js.tpl', 'ElementUi-Dialog.vue.tpl', 'ElementUi-index.js.tpl', 'ElementUi-Main.vue.tpl', 'ElementUi-object.js.tpl', 'ElementUi-Search.vue.tpl', 'ElementUi-Table.vue.tpl'],
  VO: ['VO.java.tpl'],
  DTO: ['DTO.java.tpl'],
  Query: ['Query.java.tpl']
}
