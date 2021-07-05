import Vue from 'vue'
import VueRouter from 'vue-router'

// 进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
// 路由数据
import routes from './router'
Vue.use(VueRouter)
console.log(routes)
// 导出路由 在 main.js 里使用
const router = new VueRouter({
  routes
})

/**
 * 路由拦截
 * 权限验证
 */
router.beforeEach((to, from, next) => {
  // 进度条
  NProgress.start()
  next()
})

router.afterEach(to => {
  // 进度条
  NProgress.done()
})


export default router
