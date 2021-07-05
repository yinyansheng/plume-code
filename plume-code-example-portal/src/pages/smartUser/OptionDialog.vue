<template>
  <el-dialog :title="isEdit ? '编 辑': '新 增'" width="45%" :visible.sync="visible" :before-close="close" label-position="right"
            :close-on-press-escape="false" :close-on-click-modal="false">
    <el-form ref="ent" :model="ent" :rules="rules" label-width="110px">
      <el-form-item label="ID" prop="id">
        <el-input v-model="ent.id"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="ent.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ent.password"></el-input>
      </el-form-item>
      <el-form-item label="version" prop="version">
        <el-input v-model="ent.version"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
            <el-button @click="close">Cancel</el-button>
            <el-button type="primary" @click="submit">Submit</el-button>
    </span>
  </el-dialog>
</template>
<script>
import {
  Add,
  Update
} from '@/api/smartUser'
import {SmartUserEnt, formRules} from "./object";

export default {
  inject: ['loadData'],
  data() {
    return {
      ent: new SmartUserEnt(),
      rules: formRules,
      isEdit: false,
      visible: false,
      cityList: [],
    }
  },
  methods: {
    somethingFunction() {
        console.log("xxxx");
    },
    submit() {
      this.$refs.ent.validate((validate) => {
        if (validate) {
          this.isEdit ? this.updateRow() : this.addRow()
        }
      })
    },
    addRow() {
      let req = {...this.ent}
      Add(req).then(res => {
        this.$message({
          message: '保存成功',
          type: 'success'
        })
        this.loadData(false)
        this.close()
      })
    },
    updateRow() {
      let req = {...this.ent}
      Update(req).then(res => {
        this.$message({
          message: '修改成功',
          type: 'success'
        })
        this.loadData(false)
        this.close()
      })
    },
    close() {
      this.visible = false
      this.$nextTick(() => {
        this.$refs.ent.resetFields()
      })
    },
    show(isEdit = false, ent) {
      this.ent = ent
      this.isEdit = isEdit
      this.visible = true
      this.$nextTick(() => {
        if (this.isEdit) {

        }
      })
    },
  }
}
</script>
