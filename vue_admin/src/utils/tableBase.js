function statusTagFilter(type) {
  if (type === '启用') {
    return
  }
  if (type === '禁用') {
    return 'info'
  }
}

function permissionTypeTagFilter(type) {
  if (type === '一级菜单') {
    return 'success'
  }
  if (type === '二级菜单') {
    return
  }
  if (type === '操作') {
    return 'danger'
  }
}

function handlePageChange(currentPage) {
  this.pagination.current = currentPage;
  let {current, size} = this.pagination;
  this.pageReq.current = current
  this.pageReq.size = size
  this.page();
}

function handleSizeChange(currentSize) {
  this.pagination.size = currentSize;
  let {current, size} = this.pagination;
  this.pageReq.current = current;
  this.pageReq.size = size;
  this.page();
}

function prevPageIfPageLastOne() {
  if (this.tableData.length === 1 && this.pageReq.current > 1) {
    this.pageReq.current -= 1
  }
}

function handleSelectionChange(list) {
  this.rowSelected = list;
}

function handleDeleteMulti() {
  if (this.rowSelected.length <= 0) {
    return;
  }
  const ids = this.rowSelected.map(item => {
    return item.id;
  });
  this.deleteMulti(ids);
}

let tableData = [];
let tableMaxHeight = window.innerHeight - 240
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

let rowSelected=[]

export {
  tableData,
  tableMaxHeight,
  dialogStatus,
  operationMap,
  dialogFormVisible,
  loading,
  pageReq,
  pagination,
  rowSelected,
  statusTagFilter,
  permissionTypeTagFilter,
  prevPageIfPageLastOne,
  handleSizeChange,
  handlePageChange,
  handleDeleteMulti,
  handleSelectionChange
}
