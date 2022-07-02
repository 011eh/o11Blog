import request from '@/utils/request'

export function getBlogByUid (params) {
  return request({
    url: '/content/getBlogByUid',
    method: 'get',
    params
  })
}

export function getSameBlogByTagUid (params) {
  return request({
    url: '/content/getSameBlogByTagUid',
    method: 'get',
    params
  })
}

export function getSameBlogByBlogUid (params) {
  return request({
    url: '/content/getSameBlogByBlogUid',
    method: 'get',
    params
  })
}

export function praiseBlogByUid (params) {
  return request({
    url: '/content/praiseBlogByUid',
    method: 'get',
    params
  })
}

export function getBlogPraiseCountByUid (params) {
  return request({
    url: '/content/getBlogPraiseCountByUid',
    method: 'get',
    params
  })
}
