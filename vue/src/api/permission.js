import request from '@/utils/request'

export function list() {
  return request({
    url: '/permission/list',
    method: 'get'
  });
}

export function detail(id) {
  return request({
    url: `/permission/${id}`,
    method: 'get',
  });
}

export function update(permission) {
  return request({
    url: `/permission`,
    method: 'put',
    permission
  });
}
