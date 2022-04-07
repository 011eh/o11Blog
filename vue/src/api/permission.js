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

export function parentSelect() {
  return request({
    url: `/sysConfig/permissionDto`,
    method: 'get',
  });
}

export function doDelete(id) {
  return request({
    url: `/permission`,
    method: 'delete',
    data: [id]
  });
}
