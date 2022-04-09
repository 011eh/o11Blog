import request from "@/utils/request";

export function permissionSelect() {
  return request({
    url: `/sysConfig/permissionDto`,
    method: 'get',
  });
}

export function permissionTree() {
  return request({
    url: `/sysConfig/permissionTree`,
    method: 'get',
  });
}
