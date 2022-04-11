import request from "@/utils/request";

export function getPage(data) {
  return request({
    url: `/admin/page`,
    method: 'post',
    data
  });
}
