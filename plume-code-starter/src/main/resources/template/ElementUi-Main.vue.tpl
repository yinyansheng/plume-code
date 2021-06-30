<template>
  <div>
    <Search ref="search" />
    <el-row style="text-align: left">
      <el-button @click="showAdd">Add</el-button>
    </el-row>
    <Table ref="table" />
    <OptionDialog ref="dialog"/>
  </div>
</template>

#set($refs="$refs")

<script>
import Search from './Search'
import Table from './Table'
import OptionDialog from './OptionDialog'
import {${ClassName}Ent} from "./object";
export default {
  name: "Bankcard",
  components: {Search,Table, OptionDialog},
  provide: function () {
    return {
      loadData: this.loadData,
      showEdit: this.showEdit
    }
  },
  methods: {
    loadData(isFirst) {
      const searchForm = this.$refs.search.getSearchForm()
      this.$refs.table.loadData(isFirst, searchForm)
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
