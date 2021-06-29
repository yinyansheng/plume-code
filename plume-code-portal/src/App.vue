<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import util from '@/libs/util'

export default {
  name: 'app',
  watch: {
    '$i18n.locale': 'i18nHandle'
  },
  created() {
    this.i18nHandle(this.$i18n.locale)
  },
  methods: {
    i18nHandle(val, oldVal) {
      util.cookies.set('lang', val)
      document.querySelector('html').setAttribute('lang', val)
    }
  }
}
</script>

<style lang="scss">
@import '~@/assets/style/public-class.scss';

.el-table {
  padding-bottom: 10px !important;
}

.el-divider--horizontal {
  margin: 10px 0;
}

/* switch按钮款式 */
.switch .el-switch__label {
  position: absolute;
  display: none;
  color: #fff !important;
}
/*关上时文字地位设置*/
.switch .el-switch__label--right {
  z-index: 1;
}
/* 调整关上时文字的显示位子 */
.switch .el-switch__label--right span{
  margin-left: 12px;
}
/*敞开时文字地位设置*/
.switch .el-switch__label--left {
  z-index: 1;
}
/* 调整敞开时文字的显示位子 */
.switch .el-switch__label--left span{
  margin-left: 22px;
}
/*显示文字*/
.switch .el-switch__label.is-active {
  display: block;
}
/* 调整按钮的宽度 */
.switch.el-switch .el-switch__core,
.el-switch .el-switch__label {
  width: 60px !important;
  margin: 0;
}

.el-drawer {
  outline: none;
  &__body {
    height: 100%;
    overflow: hidden;
  }
  &__header {
    padding: 10px 10px 10px 10px;
    margin-bottom: 0;
    border-bottom: 1px solid #DCDFE6;
  }
}

.drawer {
  display: flex;
  flex-direction: column;
  height: 100%;
  &__header {
    flex: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    padding: 15px 20px;
    border-bottom: 1px solid #d3d4d5;
    &__title {
      flex: auto;
      font-family: PingFangSC-Medium;
      font-size: 16px;
      color: #333333;
      letter-spacing: 0;
    }
    &__close {
      color: #979797;
      font-size: 22px;
      flex: none;
      cursor: pointer;
    }
  }
  &__body {
    ::-webkit-scrollbar { width: 0 !important }
    flex: auto;
    overflow-x: hidden;
    overflow-y: auto;
    box-sizing: border-box;
    padding: 10px;
  }
  &__footer {
    flex: none;
    box-sizing: border-box;
    padding: 10px 20px;
    box-shadow: 0 -1px 4px 1px rgba(0, 0, 0, 0.14);
  }
}

.el-pagination {
  float: right !important;
}

.table-container {
  margin: 10px 0;
}

.page-container {
  margin: 10px 0;
  text-align: right;
}


.w100 {
  width: 100%
}

.green {
  color: green;
  font-weight: bold;
}

.red {
  color: red;
  font-weight: bold;
}

</style>
