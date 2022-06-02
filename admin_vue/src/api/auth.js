import request from '@/utils/request'

export function login(data) {
  let params = new URLSearchParams()
  params.append('username', data.username)
  params.append('password', data.password)
  return request({
    url: '/auth/login',
    method: 'post',
    params
  })
}

export function getInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'get'
  })
}
