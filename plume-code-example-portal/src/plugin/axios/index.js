import axios from 'axios'
import { Message } from 'element-ui'

// 创建一个错误
function errorCreat (msg) {
  const err = new Error(msg)
  errorLog(err)
  throw err
}

// 记录和显示错误
function errorLog (err) {
  // 显示提示
  Message({
    message: err.message,
    type: 'error',
    duration: 5 * 1000
  })
}

function getBaseUrl() {
  if(process.env.NODE_ENV === 'production') {
    return location.origin;
  } else {
    return process.env.VUE_APP_API
  }
}

// 创建一个 axios 实例
const service = axios.create({
  baseURL: getBaseUrl(),
  timeout: 20000, // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {

    return config
  },
  error => {
    // 发送失败
    console.log(error)
    Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // dataAxios 是 axios 返回数据中的 data
    const dataAxios = response.data
    // 这个状态码是和后端约定的
      if(dataAxios.sessionout) {
          window.location.href = getBaseUrl()
          return Promise.reject(new Error('过期'))
      }
    const { code, success } = dataAxios
    // 根据 code 进行判断
    if (code === undefined) {
      // 如果没有 code 代表这不是项目后端开发的接口 比如可能是 D2Admin 请求最新版本
      if(success === undefined) {
        return dataAxios
      }
      if(!success) {
        console.log(dataAxios);
        errorCreat("服务器异常，请查看控制台")
      } else {
        return dataAxios
      }

    } else {
      // 有 code 代表这是一个后端接口 可以进行进一步的判断
      switch (code) {
        case 0:
          // [ 示例 ] code === 0 代表没有错误
          return dataAxios
        case -302:
          // session out
          // [ 示例 ] code === 0 代表没有错误
          window.location.href = getBaseUrl()
          return Promise.reject(new Error('过期'))
        default:
          // 不是正确的 code
          errorCreat(`${dataAxios.msg}`)
          break
      }
    }
  },
  error => {
    if (error && error.response) {
      switch (error.response.status) {
        case 400:
          error.message = '请求错误'
          break
        case 401:
          error.message = '未授权，请登录'
          break
        case 403:
          error.message = '拒绝访问'
          break
        case 404:
          error.message = `请求地址出错: ${error.response.config.url}`
          break
        case 408:
          error.message = '请求超时'
          break
        case 500:
          error.message = '服务器内部错误'
          break
        case 501:
          error.message = '服务未实现'
          break
        case 502:
          error.message = '网关错误'
          break
        case 503:
          error.message = '服务不可用'
          break
        case 504:
          error.message = '网关超时'
          break
        case 505:
          error.message = 'HTTP版本不受支持'
          break
        default:
          break
      }
    }
    errorLog(error)
    return Promise.reject(error)
  }
)

export default service
