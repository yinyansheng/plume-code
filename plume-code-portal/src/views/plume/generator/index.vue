<template>
  <d2-container>
    <template slot="header">
      <d2-icon name="diamond"/>
      代码生成
    </template>
    <el-row>
      <el-col :span="4">
        <!--  数据库配置-->
        <el-select style="width: 100%;" v-model="value" placeholder="请选择" @change="handleSelectDatabase" clearable>
          <el-option
            v-for="item in databases"
            :key="item.label"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <!--  数据库表-->
        <div style="padding:10px">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选
          </el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="checkedTables" @change="handleCheckedChange">
            <el-checkbox style="display: block" v-for="table in tables" :label="table" :key="table">{{ table }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
      </el-col>
      <el-col :span="20" style="padding-left:10px">
        <el-form :model="settingsForm" :rules="rules" ref="settingsForm" label-width="100px" class="demo-settingsForm">
          <el-card class="box-card">
            <div slot="header" class="clearfix"><span>项目信息</span></div>
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
            <div slot="header" class="clearfix"><span>项目信息</span></div>
            <div>
              <el-form-item label="页面层" prop="portal">
                <el-tooltip style="margin-right: 10px" effect="dark" content="生成后台管理页面" placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox v-model="settingsForm.portal" label="ElementUI"/>
              </el-form-item>
              <el-form-item label="控制层" prop="controller">
                <el-tooltip style="margin-right: 10px" effect="dark" content="Controller,Query,VO"
                            placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox v-model="settingsForm.controller">Controller
                </el-checkbox>
                <el-checkbox v-model="settingsForm.Query">Query</el-checkbox>
                <el-checkbox v-model="settingsForm.VO">VO</el-checkbox>
              </el-form-item>
              <el-form-item label="服务层" prop="service">
                <el-tooltip style="margin-right: 10px" effect="dark"
                            content="生成UserService、UserServiceImpl、UserDTO"
                            placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox v-model="settingsForm.service">Service</el-checkbox>
                <el-checkbox v-model="settingsForm.DTO">DTO</el-checkbox>
              </el-form-item>
              <el-form-item label="持久层" prop="repositoryMode">
                <el-tooltip style="margin-right: 10px" effect="dark" content="Mapper、Entity、XML" placement="top">
                  <i class="el-icon-warning-outline"></i>
                </el-tooltip>
                <el-checkbox true-label="mybatisPlus" false-label="none" v-model="settingsForm.repositoryMode"
                             label="MybatisPlus"/>
                <el-checkbox true-label="jpa" false-label="none" v-model="settingsForm.repositoryMode" label="JPA"/>
                <el-checkbox true-label="mybatis" false-label="none" v-model="settingsForm.repositoryMode"
                             label="Mybatis"/>
                <el-checkbox true-label="tkMybatis" false-label="none" v-model="settingsForm.repositoryMode"
                             label="tk-Mybatis"/>
              </el-form-item>
            </div>
          </el-card>
          <el-card class="box-card">
            <div slot="header" class="clearfix"><span>高级配置</span></div>
            <div>
              <el-form-item label="作者" prop="author">
                <el-input v-model="settingsForm.author" placeholder="作者"></el-input>
              </el-form-item>
              <el-form-item label="表名前缀" prop="tablePrefix">
                <el-input v-model="settingsForm.tablePrefix" style="display: inline-block;"
                          placeholder="填写前缀生成的文件将移除前缀，例如：plume_"></el-input>
              </el-form-item>
              <el-form-item label="字段前缀" prop="columnPrefix">
                <el-input v-model="settingsForm.columnPrefix" placeholder="填写前缀生成的文件将移除前缀，例如：n_,s_,d_"></el-input>
              </el-form-item>
            </div>
          </el-card>
        </el-form>
      </el-col>
    </el-row>
    <template slot="footer">
      <el-row>
        <el-col :span="20" :offset="4">
          <el-button :loading="loading" type="primary" @click="submitForm('settingsForm')">一键下载</el-button>
          <el-button :loading="loading" type="primary" @click="submitForm('settingsForm', true)">代码预览</el-button>
        </el-col>
      </el-row>
    </template>
    <show-content ref="showContent"/>
  </d2-container>
</template>

<script>
import api from '@api'
import {templateMap} from './settings'
import showContent from './showContent'

export default {
  name: 'generator',
  components: {showContent},
  data() {
    return {
      loading: false,
      apiurl: process.env.VUE_APP_API,
      open: '0',
      databases: [],
      selectedSetting: null,
      value: '',
      checkAll: false,
      checkedTables: [],
      tables: [],
      isIndeterminate: true,
      settingsForm: {
        portal: true,
        tablePrefix: '',
        columnPrefix: '',
        projectName: 'plume_code',
        basePackageName: 'com.plume.code',
        author: 'plume-code',
        swaggerState: false,
        lombokState: false,
        controller: true,
        Query: true,
        VO: true,
        service: true,
        serviceImpl: true,
        DTO: true,
        repositoryMode: 'mybatisPlus'
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
        basePackageName: [{
          required: true,
          message: '请输入包名',
          trigger: 'blur'
        }
        ]
      }
    }
  },
  mounted() {
    this.loadSettings()
  },
  methods: {
    handleSelectDatabase(setting) {
      if (setting) {
        this.selectedSetting = setting
        api.listTableName(setting).then(res => {
          this.tables = res.data
        })
      } else {
        this.selectedSetting = null
        this.tables = []
        this.checkedTables = []
      }
    },
    handleCheckAllChange(val) {
      this.checkedTables = val ? this.tables : []
      this.isIndeterminate = false
    },
    handleCheckedChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.tables.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.tables.length
    },
    async loadSettings() {
      const settings = await this.getSettings()
      this.databases = settings.map(s => ({
        label: s.name,
        value: s
      }))
    },
    submitForm(formName, showCode = false) {
      if (this.checkedTables.length === 0) {
        this.$message.warning('请选择要生成的表')
        return
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let req = this.buildRequestBody(this.settingsForm)
          req = {...req, ...this.settingsForm}
          this.loading = true
          api.generate({
            connectionModel: this.selectedSetting,
            settingModel: req
          }).then(res => {
            if (res.success) {
              if (showCode) {
                this.$refs.showContent.show(res.data.batchNo)
              } else {
                window.open(`${this.apiurl}generator/download?batchNo=${res.data.batchNo}`)
              }
            } else {
              this.$message.warning(res.message)
            }
          }).finally(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    },
    buildRequestBody(settings) {
      const templateNameSet = []

      if (settings.repositoryMode) {
        templateNameSet.push(...templateMap[settings.repositoryMode])
        settings.service && templateNameSet.push(...templateMap.service[settings.repositoryMode])
        settings.controller && templateNameSet.push(...templateMap.controller[settings.repositoryMode])
      } else {
        settings.service && templateNameSet.push(...templateMap.service.default)
        settings.controller && templateNameSet.push(...templateMap.controller.default)
      }

      settings.portal && templateNameSet.push(...templateMap.portal)

      settings.VO && templateNameSet.push(...templateMap.VO)
      settings.DTO && templateNameSet.push(...templateMap.DTO)
      settings.Query && templateNameSet.push(...templateMap.Query)
      return {
        templateNameSet,
        tableNameSet: this.checkedTables
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    getSettings() {
      return this.$store.dispatch('d2admin/db/get', {
        dbName: 'database',
        path: 'database.settings',
        defaultValue: []
      }, {root: true})
    }
  }
}
</script>
<style lang="scss" scoped>
.box-card {
  margin-bottom: 10px;
}

.el-checkbox {
  width: 120px
}
</style>
