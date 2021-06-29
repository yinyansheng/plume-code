<template>
  <div class="table-container">
    <el-table ref="table" :data="data" :loading="loading" @on-selection-change="handleSelectChange" highlight-row>
#foreach(${fieldModel} in ${fieldModelList})
    <el-table-column prop="${fieldModel.name}" label="${fieldModel.comment}" width="300" align="center"/>
#end
    <el-table-column prop="id" label="操作" width="250" align="center">
      <template slot-scope="scope">
        <el-button size="mini" @click="showEditModel(scope.row)">编辑</el-button>
        <el-popconfirm
          placement="top"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          confirm-button-type="danger"
          title="确定删除吗？"
          @confirm="disable(scope.row)"
        >
          <el-button style="margin-left: 10px" type="danger" size="mini" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
    </el-table>
    <div class="page-container">
      <el-pagination
        background
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page="pageInfo.pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageInfo.pageSize"
        layout="total,sizes, prev, pager, next"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import api from '@/api'

export default {
  props: {
    searchForm: {
      type: Object,
      required: true
    }
  },
  data: function () {
    return {
      loading: false,
      data: [],
      pageInfo: {
        pageIndex: 1,
        pageSize: 10,
        total: 0
      }
    }
  },
  mounted: function () {

  },
  methods: {
    load (isSearch = false) {
      isSearch && (this.pageInfo.pageIndex = 1)
      this.loading = true
      api.config.page({ ...this.searchForm, ...this.pageInfo }).then(res => {
        this.data = res.records
        this.pageInfo.total = res.total
        this.loading = false
      })
    },
    disable (row) {
      api.config.disable({ id: row.id }).then(res => {
        this.$message.success('删除成功！')
        this.load(true)
      })
    },
    batchDelete () {

    },
    handleSelectChange (selection) {
      this.selectedRows = selection
    },
    showEditModel (row) {
      this.$emit('show-edit-modal', row)
    },
    sizeChange (v) {
      this.pageInfo.pageSize = v
      this.load(false)
    },
    currentChange (v) {
      this.pageInfo.pageIndex = v
      this.load(false)
    }
  }
}
</script>
