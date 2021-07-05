<template>
  <el-form label-width="110px" label-position="right" :model="searchForm" ref="searchForm" size="mini">
    <el-row>
    <el-col :span="6">
        <el-form-item label="" prop="id">
          <el-input v-model="searchForm.id"></el-input>
        </el-form-item>
  </el-col>
    <el-col :span="6">
        <el-form-item label="" prop="name">
          <el-input v-model="searchForm.name"></el-input>
        </el-form-item>
  </el-col>
    <el-col :span="6">
        <el-form-item label="" prop="password">
          <el-input v-model="searchForm.password"></el-input>
        </el-form-item>
  </el-col>
    <el-col :span="6">
        <el-form-item label="version" prop="version">
          <el-input v-model="searchForm.version"></el-input>
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
