<template>
  <d2-container>
    <template slot="header"><d2-icon name="database"/> 数据库配置</template>
    <row>
      <el-button type="primary">新增</el-button>
    </row>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column width="250">
        <template slot-scope="{row}">
           <el-button type="primary">编 辑</el-button>
           <el-button type="primary">测试连接</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="数据库名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="url"
        label="url">
      </el-table-column>
    </el-table>
  </d2-container>
</template>

<script>
import Template from '@/views/plume/template'
export default {
  name: 'database',
  components: { Template },
  data () {
    return {
      tableData: [{
        name: 'tcsso',
        url: 'mysql://'
      }, {
        name: 'xc_crm',
        url: 'mysql://'
      }, {
        name: 'tcpoi',
        url: 'mysql://'
      }, {
        name: 'xc_srm',
        url: 'mysql://'
      }]
    }
  }
}
</script>
