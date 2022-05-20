import request from "@/utils/request";

export function permissionSelect() {
  return request({
    url: `/sysBase/permissionDto`,
    method: 'get',
  });
}

export function permissionTree() {
  return request({
    url: `/sysBase/permissionTree`,
    method: 'get',
  });
}

export function roleSelect() {
  return request({
    url: `/sysBase/roleDto`,
    method: 'get',
  });
}

export function sysLogPage(pageReq) {
  return request({
    url: `/sysBase/sysLogPage`,
    method: 'post',
    data: pageReq
  })
}

export function adminSelect() {
  return request({
    url: `/sysBase/adminDto`,
    method: 'get'
  });
}
