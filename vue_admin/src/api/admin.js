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

export function resetPassword(oldPassword, newPassword) {
  let params = new URLSearchParams()
  params.append('oldPassword', oldPassword)
  params.append('newPassword', newPassword)
  return request({
    url: `/admin/resetPassword`,
    method: 'post',
    params
  });
}

export function resetToDefaultPassword(userId) {
  return request({
    url: `/admin/resetPassword/${userId}`,
    method: 'post',
  });
}
