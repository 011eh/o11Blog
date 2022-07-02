import request from '@/utils/request'

export function getTagList(params) {
  return request({
    url: '/tag/getTagList',
    method: 'get',
    params
  })
}

export function getArticleByTagUid(params) {
  return request({
    url: '/tag/getArticleByTagUid',
    method: 'get',
    params
  })
}
