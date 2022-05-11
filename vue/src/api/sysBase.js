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