<template>
  <el-dialog :title="isEdit ? 'Edit': 'Add'" width="45%" :visible.sync="visible" :before-close="close" label-position="right"
            close-on-press-escape="false" :close-on-click-modal="false">
    <el-form ref="ent" :model="ent" :rules="rules" label-width="110px">
#foreach(${fieldModel} in ${fieldModelList})
      <el-form-item label="${fieldModel.comment}" prop="${fieldModel.name}">
        <el-input v-model="ent.${fieldModel.name}"></el-input>
      </el-form-item>
#end
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
} from '@/api/${className}'
import {${ClassName}Ent, formRules} from "./object";

export default {
  inject: ['loadData'],
  data() {
    return {
      ent: new ${ClassName}Ent(),
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
