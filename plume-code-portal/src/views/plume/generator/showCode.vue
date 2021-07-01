<template>
  <el-dialog title="预览" width="50%" :visible.sync="dialogFormVisible">

    <el-row >
      <el-col :span="8" style="height: 500px;  overflow: auto">
        <el-tree :data="codeTree" :props="defaultProps" default-expand-all  @node-click="handleNodeClick"></el-tree>
      </el-col>
      <el-col :span="16">
        <codemirror style="height: 500px;" v-model="code" :options="cmOptions"></codemirror>
      </el-col>
    </el-row>

    <div slot="footer" style="text-align: center">
      <el-button :loading="loading" type="primary" @click="submitForm()">一键下载</el-button>
    </div>
  </el-dialog>
</template>

<script>
import api from '@api'
import 'codemirror/mode/vue/vue.js'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/theme/base16-dark.css'
export default {
  name: 'showCode',
  data () {
    this.defaultProps = {
      children: 'children',
      label: 'fileName'
    }
    return {
      dialogFormVisible: false,
      batchNo: '',
      codeTree: [],
      code: '',
      cmOptions: {
        auto: '300px',
        tabSize: 4,
        foldGutter: true,
        styleActiveLine: true,
        lineNumbers: true,
        line: true,
        readOnly: true,
        mode: 'text/x-java', // "text/x-vue"
        theme: 'base16-dark'
      },
      loading: false
    }
  },
  methods: {
    submitForm () {

    },
    handleNodeClick () {

    },
    loadCodeTree () {
      api.codeTree({ batchNo: this.batchNo }).then(res => {
        this.codeTree = [res.data]
      })
    },
    show (batchNo) {
      this.batchNo = '1625109162146'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.loadCodeTree()
      })
    }
  }
}
</script>

<style scoped>

</style>
