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

export function doDelete(data) {
  return request({
    url: `/permission`,
    method: 'delete',
    data
  });
}

export function grantedTo(roleId) {
  return request({
    url: `/permission/granted/${roleId}`,
    method: 'get',
  });
}

// 接口鉴权
export function apiMatchPage(data) {
  return request({
    url: `/permission/apiMatcher/page`,
    method: 'post',
    data
  });
}

export function apiMatchCreate(data) {
  return request({
    url: `/permission/apiMatcher`,
    method: 'post',
    data
  });
}

export function apiMatchUpdate(data) {
  return request({
    url: `/permission/apiMatcher`,
    method: 'put',
    data
  });
}

export function apiMatchDelete(data) {
  return request({
    url: `/permission/apiMatcher`,
    method: 'delete',
    data
  });
}
