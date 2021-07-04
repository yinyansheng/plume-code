<template>
  <el-dialog top="10px" title="预览" width="80%" :visible.sync="dialogFormVisible" :close-on-press-escape="false"
             :close-on-click-modal="false">
    <el-row>
      <el-col :span="6" style="height: 80vh;  overflow: auto">
        <el-tree :data="tree" :props="defaultProps" default-expand-all @node-click="handleNodeClick">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>
                <i v-if="data.directory" class="el-icon-folder-opened"></i>
                <i v-else class="el-icon-document"></i>
              {{ data.name }}
            </span>
        </span>
        </el-tree>
      </el-col>
      <el-col :span="18">
        <codemirror style="height: 80vh;" v-model="content" :options="cmOptions"></codemirror>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
import api from '@api'
import util from '@/libs/util'
import 'codemirror/mode/vue/vue.js'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/theme/base16-dark.css'

const postfixModeMap = {
  js: 'text/javascript',
  vue: 'text/javascript',
  java: 'text/x-java',
  xml: 'text/xml'
}

export default {
  name: 'showContent',
  data() {
    this.defaultProps = {
      children: 'children',
      label: 'name'
    }
    return {
      dialogFormVisible: false,
      batchNo: '',
      tree: [],
      content: '',
      cmOptions: {
        auto: '300px',
        tabSize: 4,
        foldGutter: true,
        styleActiveLine: true,
        lineNumbers: true,
        line: true,
        readOnly: true,
        mode: 'text/x-java', // "text/x-vue" text/x-java
        theme: 'base16-dark'
      },
      loading: false
    }
  },
  methods: {
    handleNodeClick(node) {
      if (!node.directory) {
        api.content({filePath: node.path}).then(res => {
          const fileName = node.name
          this.cmOptions.mode = postfixModeMap[util.postfix(fileName)]
          this.content = res.data
        })
      }
    },
    loadTree() {
      api.tree({batchNo: this.batchNo}).then(res => {
        this.tree = [...res.data.children]
      })
    },
    show(batchNo) {
      this.batchNo = batchNo
      this.content = ''
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.loadTree()
      })
    }
  }
}
</script>

<style scoped lang="scss">
.vue-codemirror {
  ::v-deep .CodeMirror {
    height: 100%;
    font-family: monospace, Arial;
    font-size: 18px;
  }
}
</style>
