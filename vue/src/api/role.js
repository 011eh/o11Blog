import request from "@/utils/request";

export function page(data) {
  return request({
    url: `/role/page`,
    method: 'post',
    data
  });
}

export function create(data) {
  return request({
    url: `/role`,
    method: 'post',
    data
  });
}

export function update(data) {
  return request({
    url: `/role`,
    method: 'put',
    data
  });
}

export function doDelete(data) {
  return request({
    url: `/role`,
    method: 'delete',
    data
  });
}
