import {Notification} from "element-ui";

function showMsg(code) {
  return Math.floor(code / 1000) === 1;
}

function success(code) {
  return code % 1000 === 200;
}

function successMsg(res) {
  if (showMsg(res.code)) {
    Notification({
      title: '成功',
      message: res.msg || '操作成功',
      type: 'success'
    })
  }
}

export {success, showMsg, successMsg}

