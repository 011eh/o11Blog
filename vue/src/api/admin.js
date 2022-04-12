import request from "@/utils/request";

export function page(data) {
  return request({
    url: `/admin/page`,
    method: 'post',
    data
  });
}

export function create(data) {
  return request({
    url: `/admin`,
    method: 'post',
    data
  });
}

export function update(data) {
  return request({
    url: `/admin`,
    method: 'put',
    data
  });
}

export function doDelete(data) {
  return request({
    url: `/admin`,
    method: 'delete',
    data
  });
}
