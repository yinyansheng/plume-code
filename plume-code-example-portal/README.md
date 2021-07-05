## 说明

框架来源: https://gitee.com/icarusion/iview-admin

iview: https://www.iviewui.com/docs/introduce

### vue技能提升

Vue官方文档： https://cn.vuejs.org/v2/guide/index.html

VueX官方文档：https://vuex.vuejs.org/zh/

VueRouter官方文档：https://router.vuejs.org/zh/guide/

Vue插件库： https://www.vue365.cn/


## 对接后台：

改造如下：

1. 环境加入
   .env.dev
   .env.prod
   .env.qa
   .env.stage

2. package.json修改build参数

3. axios适配API
   token,mchId信息

4. 菜单vuex
   store/module/app.js
   menuList: (state, getters, rootState) => getMenuByRouter(loadMenu(), rootState.user.access),

5. 动态路由(vuex,vue router)
   router/index.js router.beforeEach

## 为什么这么做



## 规则说明：

包结构
```
Bank
    components
        ButtonGroup.vue
        object.js
        OptionDialog.vue
        Search.vue
        Table.vue
Bankcard.vue
index.js
```

index.js作为组件入口；这样在进行import的时候不用写到具体的Bankcard.vue

components中的.vue相当于我们写的java类；

当我们在主组件进行 Import components后在页面进行组件使用的时候相当于 new了一个实例；

```
props: {
    ent: Object,
    formRules: Object,d
    loadData: Function
  },
```
相当于类中的属性；

当你使用的时候：<OptionDialog ref="dialog" :ent="ent" :form-rules="formRules" :load-data="doLoadData" />
:ent, :form-rules, :loadData 相当于构造函数进行传参数；

```
methods: {
    submit() {
      this.$refs.ent.validate((validate) => {
        if (validate) {
          this.isEdit ? this.updateRow() : this.addRow()
        }
      })
    },
    show(isEdit = false) {
      this.isEdit = isEdit
      this.visible = true
      this.$nextTick(() => {
        if (this.isEdit) {

        }
      })
    },
  }
```
相当于类中写的方法；

ref="dialog" 可以通过this.$refs获取对应变量dialog的实例；

this.$refs.dialog.show() 相当于调用实例的方法；


