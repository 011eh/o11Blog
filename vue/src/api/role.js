import request from "@/utils/request";

export function page(data) {
  return request({
    url: `/role/page`,
    method: 'post',
    data
  });
}
