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
let tableMaxHeight = window.innerHeight - 210
let dialogStatus = '';
let operationMap = {
  create: '添加',
  update: '修改'
};
let dialogFormVisible = false;
let loading = false;

let pagination = {
  current: 1,
  size: 10,
  pageSizeSelect: [10, 25, 50, 100],
  total: 0
}

let pageReq = {
  current: 1,
  size: 10,
  keyword: '',
}

export {
  tableData,
  tableMaxHeight,
  dialogStatus,
  operationMap,
  dialogFormVisible,
  loading,
  pageReq,
  pagination,
  tagFilter
}
