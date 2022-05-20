import request from "@/utils/request";

// Dto
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

export function adminSelect() {
  return request({
    url: `/sysBase/adminDto`,
    method: 'get'
  });
}


// 日志
export function sysLogPage(pageReq) {
  return request({
    url: `/sysBase/sysLogPage`,
    method: 'post',
    data: pageReq
  })
}


// 系统参数
export function sysParamPage(data) {
  return request({
    url: `sysParam/page`,
    method: 'post',
    data
  })
}

export function sysLogCreate(data) {
  return request({
    url: `sysParam/`,
    method: 'post',
    data
  })
}

export function sysParamUpdate(data) {
  return request({
    url: `sysParam/`,
    method: 'put',
    data
  })
}

export function sysParamDelete(data) {
  return request({
    url: `sysParam/`,
    method: 'delete',
    data
  })
}
