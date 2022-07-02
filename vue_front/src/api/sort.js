import request from '@/utils/request'

export function getSortList (params) {
  return request({
    url: '/sort/getSortList',
    method: 'get',
    params
  })
}

export function getArticleByMonth (params) {
  return request({
    url: '/sort/getArticleByMonth',
    method: 'get',
    params
  })
}
