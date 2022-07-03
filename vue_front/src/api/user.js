import request from '@/utils/request'
import o11Request from '@/utils/o11Request'

/**
 * 第三方登录
 * @param params
 */
export function login(params) {
  return request({
    url: '/oauth/render',
    method: 'post',
    params
  })
}

export function authVerify(params) {
  return request({
    url: '/oauth/verify/' + params,
    method: 'get',
  })
}

export function editUser(params) {
  return request({
    url: '/oauth/editUser',
    method: 'post',
    data: params
  })
}

/**
 * 更新用户密码
 * @param params
 */
export function updateUserPwd(params) {
  return request({
    url: '/oauth/updateUserPwd',
    method: 'post',
    data: params
  })
}

/**
 * 获取用户反馈
 * @param params
 */
export function getFeedbackList(params) {
  return request({
    url: '/oauth/getFeedbackList',
    method: 'get',
    params
  })
}

/**
 * 新增反馈
 * @param params
 */
export function addFeedback(params) {
  return request({
    url: '/oauth/addFeedback',
    method: 'post',
    data: params
  })
}

export function replyBlogLink(params) {
  return request({
    url: '/oauth/replyBlogLink',
    method: 'post',
    data: params
  })
}

export function deleteUserAccessToken(params) {
  return request({
    url: '/oauth/delete/' + params,
    method: 'post',
  })
}

/**
 * 本地登录
 * @param params
 */
export function localLogin(email, password) {
  let params = new URLSearchParams()
  params.append('email', email);
  params.append('password', password);
  return o11Request({
    url: '/auth/login',
    method: 'post',
    data: params
  })
}

/**
 * 本地注册
 * @param params
 */
export function localRegister(email, password) {
  let params = new URLSearchParams()
  params.append('email', email);
  params.append('password', password);
  return o11Request({
    url: '/auth/register',
    method: 'post',
    params
  })
}

export function logout(params) {
  return request({
    url: '/user/logout',
    method: 'post',
    data: params
  })
}

/**
 * 获取微信公众号登录二维码
 * @param params
 * @returns {*}
 */
export function getWechatOrCodeTicket(params) {
  return request({
    url: '/wechat/getWechatOrCodeTicket',
    method: 'get',
    data: params
  })
}

export function getUserLoginStatus(params) {
  return request({
    url: '/wechat/getUserLoginStatus',
    method: 'get',
    params
  })
}
