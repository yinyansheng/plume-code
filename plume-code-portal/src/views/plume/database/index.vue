<template>
  <d2-container>
    <template slot="header">
      <d2-icon name="database"/>
      数据库配置
      <el-tooltip style="margin-right: 10px" effect="dark"
                  content="配置存储在浏览器Local Storage 【key: d2admin-1.20.1 value: database/public/database/settings】"
                  placement="right-end">
        <i class="el-icon-warning-outline"></i>
      </el-tooltip>
    </template>
    <el-row>
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-popconfirm
        placement="top"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        confirm-button-type="danger"
        title="确定清空配置吗？"
        @onConfirm="handleClearSetting"
      >
        <el-button style="margin-left: 10px" type="danger" slot="reference">清空配置</el-button>
      </el-popconfirm>
    </el-row>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column width="350">
        <template slot-scope="{$index, row}">
          <el-popconfirm
            placement="top"
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            confirm-button-type="danger"
            title="确定删除吗？"
            @onConfirm="handleRemove($index, row)"
          >
            <el-button style="margin-right: 10px" type="danger" slot="reference">删 除</el-button>
          </el-popconfirm>
          <el-button type="primary" @click="handleShowEdit($index, row)">编 辑</el-button>
          <el-button type="primary" style="margin-right: 10px;" @click="handleTest(row)">测试连接</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="数据库名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="type"
        label="数据库类型"
        width="120">
      </el-table-column>
      <el-table-column
        prop="driver"
        label="驱动"
        width="200">
      </el-table-column>
      <el-table-column
        prop="url"
        label="jdbc url">
      </el-table-column>
      <el-table-column
        prop="isValid"
        label="状态"
        width="100"
      >
        <template slot-scope="{$index, row}">
          <i v-if="row.isValid" style="color:green" class="el-icon-success"></i>
          <i v-else style="color: red" class="el-icon-error"></i>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="200">
      </el-table-column>
      <el-table-column
        prop="password"
        label="密码"
        width="200">
      </el-table-column>

    </el-table>
    <el-dialog title="数据库配置" width="30%" :visible.sync="dialogFormVisible" :close-on-press-escape="false"
               :close-on-click-modal="false">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="连接名称" prop="name">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="数据库类型" prop="type">
          <el-select style="width: 100%;" v-model="form.type" placeholder="请选择数据库类型">
            <el-option label="MySQL" value="mysql"></el-option>
            <el-option label="H2" value="h2"></el-option>
            <el-option label="PostgreSQL" value="postgreSQL"></el-option>
            <el-option label="SqlServer" value="sqlServer"></el-option>
            <el-option label="Oracle" value="oracle"></el-option>
            <el-option label="MariaDB" value="mariaDB"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据库驱动" prop="driver">
          <el-select style="width: 100%;" v-model="form.driver" placeholder="请选择数据库驱动">
            <el-option label="com.mysql.cj.jdbc.Driver" value="com.mysql.cj.jdbc.Driver"></el-option>
            <el-option label="com.mysql.jdbc.Driver" value="com.mysql.jdbc.Driver"></el-option>
            <el-option label="org.h2.Driver" value="org.h2.Driver"></el-option>
            <el-option label="org.postgresql.Driver" value="org.postgresql.Driver"></el-option>
            <el-option label="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                       value="com.microsoft.sqlserver.jdbc.SQLServerDriver"></el-option>
            <el-option label="oracle.jdbc.driver.OracleDriver" value="oracle.jdbc.driver.OracleDriver"></el-option>
            <el-option label="org.mariadb.jdbc.Driver" value="org.mariadb.jdbc.Driver"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="jdbc url" prop="url">
          <el-input v-model="form.url" placeholder="例如：jdbc:mysql://127.0.0.1:3306/plume_code?characterEncoding=utf-8"/>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password"/>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button :loading="testing" type="primary" @click="test">{{ testName }}</el-button>
        <el-button @click="submit">保存</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>

import api from '@/api'

class DBSetting {
  index = -1
  name = 'plume_code'
  driver = 'com.mysql.cj.jdbc.Driver'
  type = 'mysql'
  url
  username
  password
  isValid = false
}

export default {
  name: 'database',
  data() {
    return {
      testing: false,
      testName: '测试连接',
      dialogFormVisible: false,
      tableData: [],
      form: {
        index: -1,
        name: 'h2_test',
        driver: 'org.h2.Driver',
        type: 'h2',
        url: 'jdbc:h2:~/h2_test',
        username: 'sa',
        password: '',
        isValid: false
      },
      rules: {
        name: [
          {
            required: true,
            message: '请输入数据库名',
            trigger: 'blur'
          }
        ],
        type: [
          {
            required: true,
            message: '请选择数据库类型',
            trigger: 'change'
          }
        ],
        url: [
          {
            required: true,
            message: '请输入url',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    async load() {
      this.tableData = await this.getSettings()
    },
    handleClearSetting() {
      this.tableData = []
      this.doSave([])
    },
    handleAdd() {
      this.form = new DBSetting()
      this.dialogFormVisible = true
    },
    async handleRemove(index) {
      const settings = await this.getSettings()
      settings.splice(index, 1)
      this.doSave(settings)
      this.tableData = settings
    },
    handleShowEdit(index, row) {
      this.form = {
        ...row,
        index
      }
      this.dialogFormVisible = true
    },
    handleTest(row) {
      this.doTestConnection(row).then(res => {
        if (res.success) {
          this.$message.success('测试通过')
          row.isValid = res.success
        } else {
          this.$message.warning(`测试未通过: ${res.message}`)
          row.isValid = res.success
        }
      })
    },
    test() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.testing = true
          this.testName = '测试中...'
          this.doTestConnection(this.form).then(res => {
            if (res.success) {
              this.$message.success('测试通过')
              this.form.isValid = res.success
              this.testing = false
              this.testName = '测试通过'
            } else {
              this.$message.warning(`测试未通过: ${res.message}`)
              this.form.isValid = res.success
              this.testing = false
              this.testName = '测试连接'
            }
          })
        }
      })
    },
    doTestConnection(data) {
      return api.testConnection(data)
    },
    submit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.save({...this.form})
          this.$refs.formRef.resetFields()
          this.dialogFormVisible = false
        }
      })
    },
    async save(form) {
      if (form.index > -1) {
        this.doEdit(form)
      } else {
        const settings = await this.getSettings()
        settings.push(form)
        this.doSave(settings)
        this.tableData = settings
      }
    },
    async doEdit(form) {
      const s = form
      const index = s.index
      delete s.index
      const settings = await this.getSettings()
      settings[index] = s
      this.doSave(settings)
      this.tableData = settings
    },
    doSave(settings) {
      this.$store.dispatch('d2admin/db/set', {
        dbName: 'database',
        path: 'database.settings',
        value: settings
      }, {root: true})
    },
    getSettings() {
      return this.$store.dispatch('d2admin/db/get', {
        dbName: 'database',
        path: 'database.settings',
        defaultValue: [{
          index: -1,
          name: 'h2_test',
          driver: 'org.h2.Driver',
          type: 'h2',
          url: 'jdbc:h2:~/h2_test',
          username: 'sa',
          password: '',
          isValid: true
        }]
      }, {root: true})
    }
  }
}
</script>
<style lang="scss" scoped>

</style>
