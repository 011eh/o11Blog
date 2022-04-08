function tagFilter(type) {
  if (type === '一级菜单') {
    return 'success'
  }
  if (type === '二级菜单') {
    return
  }
  if (type === '操作') {
    return 'danger'
  }
  if (type === '启用') {
    return
  }
  if (type === '禁用') {
    return 'info'
  }
}

let tableData = [];
let dialogStatus = '';
let operationMap = {
  create: '添加',
  update: '修改'
};
let dialogFormVisible = false;
let loading = false;
export {
  tableData,
  dialogStatus,
  operationMap,
  dialogFormVisible,
  loading,
  tagFilter
}
