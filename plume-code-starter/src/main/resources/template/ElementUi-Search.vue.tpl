<template>
  <el-form label-width="110px" label-position="right" :model="searchForm" ref="searchForm" size="mini">
    <el-row>
      <el-col :span="6">
        <el-form-item label="彩店" prop="shopId">
          <el-input v-model="searchForm.shopId"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="状态" prop="status">
          <yes-no-select v-model="searchForm.status" tip="状态"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-button size="mini" type="primary" @click="search">
          查询
        </el-button>
        <el-button size="mini" @click="resetSearch">
          重置
        </el-button>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
import {searchForm} from "./object";
export default {
  inject: ['loadData'],
  data() {
    return {
      searchForm: searchForm
    }
  },
  methods: {
    search() {
       this.loadData(true)
    },
    //折中方案
    getSearchForm() {
      return {...this.searchForm}
    },
    resetSearch() {
      this.$refs.searchForm.resetFields()
    },
  }
}
</script>
