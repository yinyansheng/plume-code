<template>
    <div>
      <div class="table-container">
        <el-table stripe border :data="data" v-loading="loading" highlight-current-row>
          <el-table-column prop="id" label="ID" width="300" align="center"/>
          <el-table-column prop="name" label="姓名" width="300" align="center"/>
          <el-table-column prop="password" label="密码" width="300" align="center"/>
          <el-table-column prop="version" label="version" width="300" align="center"/>
          <el-table-column label="操作" width="100">
            <template slot-scope="{row}">
              <el-button @click="handleShowEdit(row)" type="text" size="small">编辑</el-button>
              <el-button @click="deleteRow(row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="page-container">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageInfo.page"
            :page-sizes="[10, 20, 30]"
            :page-size="pageInfo.limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageInfo.total">
        </el-pagination>
      </div>
    </div>
</template>
<script>
import {
  Page,
  Delete,
} from '@/api/smartUser'
export default {
  name: "smartUser",
  inject: ['showEdit'],
  data() {
    return {
      loading: false,
      data: [],
      pageInfo: {
        page: 1,
        limit: 10,
        total: 0
      }
    }
  },
  methods: {
    loadData(firstPage = false, searchForm) {
      firstPage && (this.pageInfo.page = 1)
      let req = {...searchForm, ...this.pageInfo}
      this.doLoadData(req)
    },
    doLoadData(req) {
      this.loading = true
      Page(req).then(res => {
        this.data = res.records
        this.pageInfo.total = res.total
        this.loading = false
      })
    },
    deleteRow(row) {
      this.$confirm('确定删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Delete({id: row.id}).then(res => {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.loadData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleSizeChange(v) {
      this.pageInfo.limit = v
      this.loadData()
    },
    handleCurrentChange(v) {
      this.pageInfo.page = v
      this.loadData()
    },
    handleShowEdit(row) {
      this.showEdit({...row})
    },
  }
}
</script>

<style scoped>

</style>
