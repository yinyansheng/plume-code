<template>
  <div>
    <Search ref="search" />
    <el-row style="text-align: left">
      <el-button @click="showAdd">新 增</el-button>
    </el-row>
    <div class="table-container">
        <el-table stripe border :data="data" v-loading="loading" highlight-current-row>
#foreach(${fieldModel} in ${fieldModelList})
            <el-table-column prop="${fieldModel.name}" label="${fieldModel.comment}" width="300" align="center"/>
#end
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
        :current-page="pageInfo.pageIndex"
        :page-sizes="[10, 20, 30]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
    </el-pagination>
  </div>
  <OptionDialog ref="dialog"/>
  </div>
</template>

#set($refs="$refs")

<script>
import Search from './Search'
import OptionDialog from './OptionDialog'
import {${ClassName}Ent} from "./object";
import {
  Page,
  Delete,
} from '@/api/${className}'
export default {
  name: "${className}",
  components: {Search, OptionDialog},
  provide: function () {
    return {
      loadData: this.loadData,
      showEdit: this.showEdit
    }
  },
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
      firstPage && (this.pageInfo.pageIndex = 1)
      const searchForm = this.$refs.search.getSearchForm()
      let req = {...searchForm, ...this.pageInfo}
      this.doLoadData(req)
    },
    doLoadData(req) {
      this.loading = true
      Page(req).then(res => {
        this.data = res.data.records
        this.pageInfo.total = res.data.total
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
      this.pageInfo.pageSize = v
      this.loadData()
    },
    handleCurrentChange(v) {
      this.pageInfo.pageIndex = v
      this.loadData()
    },
    handleShowEdit(row) {
      this.showEdit({...row})
    },
    showEdit(row) {
      this.${refs}.dialog.show(true, {...row})
    },
    showAdd() {
      this.${refs}.dialog.show(false, new ${ClassName}Ent())
    },
  }
}
</script>

<style scoped>

</style>
