import request from '@/utils/request'

export function getListByDictType(params) {
  return request({
    url: '/sysDictData/getListByDictType',
    method: 'post',
    params
  })
}

export function getListByDictTypeList(params) {
  return request({
    url: '/sysDictData/getListByDictTypeList',
    method: 'post',
    data: params
  })
}
