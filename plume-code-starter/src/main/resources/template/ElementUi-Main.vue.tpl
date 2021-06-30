<template>
  <d2-container>
    <Search :search-form="searchForm" @search="load" @show-add-modal="showAddModal" @batch-del="batchDelete"/>
    <Table :search-form="searchForm" ref="table" @show-edit-modal="showEditModal"/>
    <EditModal ref="dialog" @load-data="load"/>
  </d2-container>
</template>

<script>
import Search from './components/Search'
import Table from './components/Table'
import EditModal from './components/EditModal'
import { searchForm, ${ClassName}ENT } from './components/object'

#set( $refs = "$refs")

export default {
  name: '${className}',
  components: {
    Search,
    Table,
    EditModal
  },
  data () {
    return {
      searchForm
    }
  },
  mounted: function () {
    this.load(true)
  },
  methods: {
    load (isSearch) {
      this.${refs}.table.load(isSearch)
    },
    showAddModal () {
      this.${refs}.dialog.open(new ${ClassName}ENT())
    },
    batchDelete () {
      this.${refs}.table.batchDelete()
    },
    showEditModal (row) {
      this.${refs}.dialog.open({ ...row }, true)
    },
    release () {
      this.${refs}.table.release()
    }
  }
}
</script>
