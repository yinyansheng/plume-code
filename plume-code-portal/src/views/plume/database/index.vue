<template>
  <d2-container>
    <template slot="header">
      <d2-icon name="database"/>
      数据库配置
    </template>
    <el-row>
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </el-row>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column width="350">
        <template slot-scope="{$index, row}">
          <el-button type="danger" @click="handleRemove($index, row)">删 除</el-button>
          <el-button type="primary" @click="handleShowEdit($index, row)">编 辑</el-button>
          <el-button type="primary" style="margin-right: 10px;" @click="handleTest(row)">测试连接</el-button>

          <i v-if="row.isValid" style="color:green" class="el-icon-check"></i>
          <i v-else style="color: red" class="el-icon-close"></i>

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
        width="180">
      </el-table-column>
      <el-table-column
        prop="driver"
        label="驱动"
        width="180">
      </el-table-column>
      <el-table-column
        prop="url"
        label="url">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="password"
        label="密码">
      </el-table-column>
    </el-table>
    <el-dialog title="数据库配置" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="数据库名称" prop="name">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="数据库类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择数据库类型">
            <el-option label="MySQL" value="mysql"></el-option>
            <el-option label="H2" value="h2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据库驱动" prop="driver">
          <el-select v-model="form.driver" placeholder="请选择数据库驱动">
            <el-option label="com.mysql.cj.jdbc.Driver" value="com.mysql.cj.jdbc.Driver"></el-option>
            <el-option label="com.mysql.jdbc.Driver" value="com.mysql.jdbc.Driver"></el-option>
            <el-option label="org.h2.Driver" value="org.h2.Driver"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="url" prop="url">
          <el-input v-model="form.url" placeholder="例如：jdbc:mysql://127.0.0.1:3306/plume_code?characterEncoding=utf-8"/>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password"/>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: left">
        <el-button :loading="testing" type="primary" @click="test">{{ testName }}</el-button>
        <el-button @click="submit">保存</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>

import api from '@/api'

export default {
  name: 'database',
  data () {
    return {
      testing: false,
      testName: '测试连接',
      dialogFormVisible: false,
      tableData: [],
      form: {
        index: -1,
        name: 'plume_code',
        driver: 'com.mysql.cj.jdbc.Driver',
        type: 'mysql',
        url: '',
        username: '',
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
        ],
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted () {
    this.load()
  },
  methods: {
    async load () {
      this.tableData = await this.getSettings()
    },
    handleAdd () {
      this.form.isValid = false
      this.form.index = -1
      this.dialogFormVisible = true
    },
    async handleRemove (index) {
      const settings = await this.getSettings()
      settings.splice(index, 1)
      this.doSave(settings)
      this.tableData = settings
    },
    handleShowEdit (index, row) {
      this.form = {
        ...row,
        index
      }
      this.dialogFormVisible = true
    },
    handleTest (row) {
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
    test () {
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
    doTestConnection (data) {
      return api.test_connection(data)
    },
    submit () {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.save({ ...this.form })
          this.$refs.formRef.resetFields()
          this.dialogFormVisible = false
        }
      })
    },
    async save (form) {
      if (form.index > -1) {
        this.doEdit(form)
      } else {
        const settings = await this.getSettings()
        settings.push(form)
        this.doSave(settings)
        this.tableData = settings
      }
    },
    async doEdit (form) {
      const s = form
      const index = s.index
      delete s.index
      const settings = await this.getSettings()
      settings[index] = s
      this.doSave(settings)
      this.tableData = settings
    },
    doSave (settings) {
      this.$store.dispatch('d2admin/db/set', {
        dbName: 'sys',
        path: 'database.settings',
        value: settings
      }, { root: true })
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
