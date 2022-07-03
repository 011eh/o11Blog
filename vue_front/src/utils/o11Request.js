import axios from 'axios'
import router from '@/router/index'
import {getCookie} from "@/utils/cookieUtils";
import {getCode} from "./codeUtil";
// 创建axios实例
const service = axios.create({
  baseURL: '/api', // api 的 base_url
  timeout: 20000 // 请求超时时间 10秒
})

service.defaults.headers.common['satoken'] = getCookie("token")

// request拦截器
service.interceptors.request.use(
  config => {
    if (getCookie("token") != undefined) {
      config.headers.Authorization = getCookie("token") // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)


// response 拦截器
service.interceptors.response.use(
  response => {
    // return response.data
    const res = response.data
    const code = res.code
    if (getCode(code) === 200) {
      return res
    } else {
      return Promise.reject('error')
    }
  },
  error => {
    // 出现网络超时
    router.push('500')
    return Promise.reject(error)
  }
)

export default service
