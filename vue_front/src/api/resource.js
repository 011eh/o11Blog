import request from '@/utils/request'

export function getStudyVideoBySort (params) {
  return request({
    url: '/resource/getStudyVideoBySort',
    method: 'get',
    params
  })
}
