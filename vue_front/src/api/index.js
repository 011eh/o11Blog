import request from '@/utils/request'

export function getBlogByLevel (params) {
  return request({
    url: '/index/getBlogByLevel',
    method: 'get',
    params
  })
}

export function getNewBlog (params) {
  return request({
    url: '/index/getNewBlog',
    method: 'get',
    params
  })
}

export function getBlogByTime (params) {
  return request({
    url: '/index/getBlogByTime',
    method: 'get',
    params
  })
}

export function getHotBlog (params) {
  return request({
    url: '/index/getHotBlog',
    method: 'get',
    params
  })
}

export function getHotTag (params) {
  return request({
    url: '/index/getHotTag',
    method: 'get',
    params
  })
}

export function getLink (params) {
  return request({
    url: '/index/getLink',
    method: 'get',
    params
  })
}

export function addLinkCount (params) {
  return request({
    url: '/index/addLinkCount',
    method: 'get',
    params
  })
}

export function getWebConfig (params) {
  return request({
    url: '/index/getWebConfig',
    method: 'get',
    params
  })
}

export function getWebNavbar (params) {
  return request({
    url: '/index/getWebNavbar',
    method: 'get',
    params
  })
}

export function recorderVisitPage (params) {
  return request({
    url: '/index/recorderVisitPage',
    method: 'get',
    params
  })
}
