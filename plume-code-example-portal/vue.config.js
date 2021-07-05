// 拼接路径
const resolve = dir => require('path').join(__dirname, dir)

module.exports = {
  lintOnSave: true,
  devServer: {
    port: 9090
  },
  // 设置config文件夹index.js中productionSourceMap的值为false，也就是设置webpack配置中devtool为false，打包后文件体积可以减少百分之八十！！！！！！！
  // 14m -> 4m
  productionSourceMap: false,
  // 默认设置: https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-service/lib/config/base.js
  chainWebpack: config => {
    // 解决 cli3 热更新失效 https://github.com/vuejs/vue-cli/issues/1559
    config.resolve
      .symlinks(true)
    // 重新设置 alias
    config.resolve.alias
      .set('@', resolve('src'))
    // babel-polyfill 加入 entry
    const entry = config.entry('app')
    entry
      .add('babel-polyfill')
      .end()
    // config.module.transformToRequire = {
    //     "audio": "src"
    // }
  }
}
