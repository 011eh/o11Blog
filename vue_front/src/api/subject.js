import request from '@/utils/request'

export function getSubjectList(params) {
  return request({
    url: '/subject/getList',
    method: 'post',
    data: params
  })
}

export function getSubjectItemList(params) {
  return request({
    url: '/subject/getItemList',
    method: 'post',
    data: params
  })
}



