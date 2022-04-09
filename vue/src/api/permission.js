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

export function create(permission) {
  return request({
    url: `/permission`,
    method: 'post',
    data: permission
  });
}

export function update(permission) {
  return request({
    url: `/permission`,
    method: 'put',
    data: permission
  });
}

export function doDelete(id) {
  return request({
    url: `/permission`,
    method: 'delete',
    data: [id]
  });
}

export function grantedTo(roleId) {
  return request({
    url: `/permission/granted/${roleId}`,
    method: 'get',
  });
}
