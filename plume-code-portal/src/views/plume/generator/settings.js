export const templateMap = {
  mybatisPlus: ['MybatisPlus-ENT.java.tpl', 'MybatisPlus-Mapper.java.tpl'],
  jpa: ['Jpa-ENT.java.tpl', 'Jpa-Repository.java.tpl'],
  mybatis: ['Mybatis-ENT.java.tpl', 'Mybatis-Mapper.xml.tpl'],
  service: {
    default: ['Service.java.tpl', 'ServiceImpl.java.tpl'],
    mybatisPlus: ['MybatisPlus-Service.java.tpl', 'MybatisPlus-ServiceImpl.java.tpl'],
    jpa: ['Jpa-Service.java.tpl', 'Jpa-ServiceImpl.java.tpl'],
    mybatis: ['Mybatis-Service.java.tpl', 'Mybatis-ServiceImpl.java.tpl']
  },
  controller: {
    default: ['Controller.java.tpl'],
    mybatisPlus: ['MybatisPlus-Controller.java.tpl'],
    jpa: ['Jpa-Controller.java.tpl'],
    mybatis: ['Mybatis-Controller.java.tpl']
  },
  portal: ['ElementUi-api.js.tpl', 'ElementUi-Dialog.vue.tpl', 'ElementUi-index.js.tpl', 'ElementUi-Main.vue.tpl', 'ElementUi-object.js.tpl', 'ElementUi-Search.vue.tpl', 'ElementUi-Table.vue.tpl'],
  VO: ['VO.java.tpl'],
  DTO: ['DTO.java.tpl'],
  Query: ['Query.java.tpl']
}
