import request from '@/utils/request'

export function getBlogSortList(params) {
  return request({
    url: '/classify/getBlogSortList',
    method: 'get',
    params
  })
}

export function getArticleByBlogSortUid(params) {
  return request({
    url: '/classify/getArticleByBlogSortUid',
    method: 'get',
    params
  })
}
