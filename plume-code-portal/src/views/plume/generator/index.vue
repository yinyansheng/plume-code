<template>
  <d2-container>
    <template slot="header">
      <d2-icon name="diamond"/>
      代码生成
    </template>
    <el-row>
      <el-col :span="4">
        <el-select style="width: 100%;" v-model="value" placeholder="请选择" @change="handleSelectDatabase">
          <el-option
            v-for="item in databases"
            :key="item.label"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <div style="padding:10px">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="checkedTables" @change="handleCheckedChange">
            <el-checkbox style="display: block" v-for="table in tables" :label="table.tableName" :key="table.name">{{`${table.tableName} - ${table.comment}` }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
      </el-col>
      <el-col :span="20" style="padding-left:10px">
        <el-form :model="settingsForm" :rules="rules" ref="settingsForm" label-width="100px" class="demo-settingsForm">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>项目信息</span>
            </div>
            <div>
              <el-form-item label="项目名称" prop="projectName">
                <el-input v-model="settingsForm.projectName" placeholder="项目名：plume_code"></el-input>
              </el-form-item>
              <el-form-item label="包名" prop="basePackageName">
                <el-input v-model="settingsForm.basePackageName" placeholder="报名：com.github.plume"></el-input>
              </el-form-item>
            </div>
          </el-card>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>项目信息</span>
            </div>
            <div>
              <el-form-item label="页面层" prop="name">
                <el-tooltip style="margin-right: 10px" effect="dark" content="生成api.js,CURD.vue结构" placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox v-model="settingsForm.portalMode" :true-label="1" :false-label="0"><img
                  :src="`${$baseUrl}image/logo/d2.png`" width="20px" height="20px"/>d2admin
                </el-checkbox>
                <el-checkbox v-model="settingsForm.portalMode" :true-label="2" :false-label="0"><img
                  :src="`${$baseUrl}image/logo/iview.svg`" width="20px" height="20px"/>iview
                </el-checkbox>
              </el-form-item>
              <el-form-item label="控制层" prop="region">
                <el-tooltip style="margin-right: 10px" effect="dark" content="生成controller类" placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox :true-label="1" :false-label="0" v-model="settingsForm.controllerMode_api">api
                </el-checkbox>
                <el-checkbox :true-label="2" :false-label="0" v-model="settingsForm.controllerMode_admin">admin
                </el-checkbox>
              </el-form-item>
              <el-form-item label="服务层">
                <el-tooltip style="margin-right: 10px" effect="dark" content="生成service接口与service实现， DTO类"
                            placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox :true-label="1" :false-label="0" v-model="settingsForm.serviceMode">service</el-checkbox>
              </el-form-item>
              <el-form-item label="持久层" prop="delivery">
                <el-tooltip style="margin-right: 10px" effect="dark" content="生成Mapper接口与表对应的实体类" placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox :true-label="1" :false-label="0" v-model="settingsForm.repositoryMode"><img
                  :src="`${$baseUrl}image/logo/mybatis.jpg`" height="20px" width="20px"/>mybatis
                </el-checkbox>
                <el-checkbox :true-label="2" :false-label="0" v-model="settingsForm.repositoryMode"><img
                  :src="`${$baseUrl}image/logo/mybatis-plus.svg`" height="20px" width="20px"/>mybatis plus
                </el-checkbox>
                <el-checkbox :true-label="3" :false-label="0" v-model="settingsForm.repositoryMode"><img
                  :src="`${$baseUrl}image/logo/spring.png`" width="20px" height="20px"/>jpa
                </el-checkbox>
              </el-form-item>
            </div>
          </el-card>
          <el-card class="box-card">
            <el-collapse v-model="open" accordion>
              <el-collapse-item title="高级配置" name="1">
                <el-form-item label="作者：" prop="author">
                  <el-input v-model="settingsForm.author" placeholder="作者"></el-input>
                </el-form-item>
                <el-form-item label="" prop="swaggerState">
                  <el-checkbox :true-label="1" :false-label="0" v-model="settingsForm.swaggerState"><img
                    :src="`${$baseUrl}image/logo/swagger.png`" width="20px" height="20px"/>swagger
                  </el-checkbox>
                  <el-checkbox :true-label="1" :false-label="0" v-model="settingsForm.lombokState"><img
                    :src="`${$baseUrl}image/logo/lombok.png`" width="20px" height="20px"/>lombok
                  </el-checkbox>
                </el-form-item>
                <el-form-item label="表名前缀：" prop="tablePrefix">
                  <el-input v-model="settingsForm.tablePrefix" placeholder="例如：plume_"></el-input>
                </el-form-item>
                <el-form-item label="字段前缀：" prop="columnPrefix">
                  <el-input v-model="settingsForm.columnPrefix" placeholder="例如：n_,s_,d_"></el-input>
                </el-form-item>
              </el-collapse-item>
            </el-collapse>
          </el-card>
          <el-row>
            <el-button type="primary" @click="submitForm('settingsForm')">一键下载</el-button>
            <el-button @click="resetForm('settingsForm')">查看</el-button>
          </el-row>
        </el-form>
      </el-col>
    </el-row>
  </d2-container>
</template>

<script>
import api from '@api'
export default {
  name: 'generator',

  data () {
    return {
      open: '0',
      databases: [],
      value: '',
      checkAll: false,
      checkedTables: [],
      tables: [],
      isIndeterminate: true,
      settingsForm: {
        portalMode: 1,
        tablePrefix: '',
        columnPrefix: '',
        projectName: 'plume_code',
        basePackageName: 'com.github.plume',
        author: '',
        swaggerState: 0,
        lombokState: 0,
        controllerMode_api: 0,
        controllerMode_admin: 2,
        controllerMode: 2,
        serviceMode: 1,
        repositoryMode: 2
      },
      rules: {
        projectName: [
          {
            required: true,
            message: '请输入项目名称',
            trigger: 'blur'
          },
          {
            min: 2,
            max: 50,
            message: '长度在 2 到 50 个字符',
            trigger: 'blur'
          }
        ],
        basePackageName: [
          {
            required: true,
            message: '请输入包名',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted () {
    this.loadSettings()
  },
  methods: {
    handleSelectDatabase (setting) {
      api.listTable(setting).then(res => {

      })
    },
    handleCheckAllChange (val) {
      this.checkedTables = val ? this.tables : []
      this.isIndeterminate = false
    },
    handleCheckedChange (value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.tables.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.tables.length
    },
    async loadSettings () {
      const settings = await this.getSettings()
      this.databases = settings.map(s => ({
        label: s.name,
        value: s
      }))
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const req = { ...this.settingsForm }
          if (req.controllerMode_api > 0 && req.controllerMode_admin > 0) {
            req.controllerMode = 3
          } else if (req.controllerMode_api === 1) {
            req.controllerMode = 1
          } else if (req.controllerMode_admin === 2) {
            req.controllerMode = 2
          }
          req.tables = this.checkedTables
          console.log(req)
        } else {
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    getSettings () {
      return this.$store.dispatch('d2admin/db/get', {
        dbName: 'sys',
        path: 'database.settings',
        defaultValue: []
      }, { root: true })
    }
  }
}
</script>
<style lang="scss" scoped>
.box-card {
  margin-bottom: 10px;
}
</style>
