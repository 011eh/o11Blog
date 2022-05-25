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
    url: `/sysBase/sysParam/page`,
    method: 'post',
    data
  })
}

export function createSysParam(data) {
  return request({
    url: `/sysBase/sysParam`,
    method: 'post',
    data
  })
}

export function updateSysParam(data) {
  return request({
    url: `/sysBase/sysParam`,
    method: 'put',
    data
  })
}

export function deleteSysParam(data) {
  return request({
    url: `/sysBase/sysParam`,
    method: 'delete',
    data
  })
}
