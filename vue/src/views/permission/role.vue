<template>
  <div class="app-container">
    <div class="filter-container">
      <el-popconfirm style="margin-left: 5px" title="确定删除吗？" @onConfirm="handleDeleteMulti">
        <template #reference>
          <el-button class="filter-item" type="danger" icon="el-icon-delete" plain circle
                     :disabled="!checkPermission(['role:update'])"/>
        </template>
      </el-popconfirm>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate"
                 :disabled="!checkPermission(['role:create'])">
        添加
      </el-button>
      <el-input v-model="pageReq.keyword" placeholder="名称"
                clearable class="filter-input"/>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page"
                 :disabled="!checkPermission(['role:list'])">
        查询
      </el-button>
    </div>
    <el-table v-loading="this.loading" @selection-change="handleSelectionChange" :data="tableData" style="width: 100%;"
              row-key="id" :max-height="tableMaxHeight">
      <el-table-column align="center" type="selection"/>
      <el-table-column align="center" label="序号" width="50" type="index"/>
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="summary" label="简介"/>
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | tagFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)"
                     :disabled="!checkPermission(['role:update'])">
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small" :disabled="!checkPermission(['role:delete'])">
                删除
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible" @closed="dialogClose">
      <el-form ref="dataForm" :model="dataOperating" label-position="left" label-width="33%"
               style="width: 60%; margin-left: 35px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataOperating.name"/>
        </el-form-item>
        <el-form-item label="简介" prop="summary">
          <el-input v-model="dataOperating.summary"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="dataOperating.status"
            size="large"
            active-value="启用"
            inactive-value="禁用"
          />
        </el-form-item>
        <el-form-item label="权限" prop="permissionIds">
          <template>
            <el-button @click="handleGrantPermission">
              授予权限
            </el-button>
          </template>
        </el-form-item>
      </el-form>
      <el-dialog :visible.sync="permissionGrantVisible" title="授予权限" append-to-body>

        <el-tree :check-strictly="true" ref="tree" :data="permissionTreeList" show-checkbox default-expand-all node-key="id"
                 highlight-current :props="treeProps" :expand-on-click-node="false" :check-on-click-node="true"
                 :default-checked-keys="this.permissionIdsGranted" @check-change="handleCheckChange"/>

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="permissionGrantVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="confirmGrant">
          确定
        </el-button>
      </span>
        </template>
      </el-dialog>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="doCreateOrUpdate(dialogStatus)">
          确定
        </el-button>
      </span>
      </template>
    </el-dialog>

    <el-footer>
      <div style="padding-top: 10px">
        <el-pagination
          v-model:currentPage="pagination.current"
          v-model:page-size="pagination.size"
          :page-sizes="pagination.pageSizeSelect"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-footer>
  </div>
</template>

<script>

import {
  dialogFormVisible,
  dialogStatus,
  handleDeleteMulti,
  handlePageChange,
  handleSelectionChange,
  handleSizeChange,
  loading,
  operationMap,
  pageReq,
  pagination,
  prevPageIfPageLastOne,
  rowSelected,
  statusTagFilter,
  tableData,
  tableMaxHeight
} from "@/utils/tableBase";
import {permissionTree} from "@/api/sysBase";
import {create, doDelete, page, update} from "@/api/role";
import {grantedTo} from "@/api/permission";
import checkPermission from "@/utils/permission";

export default {
  created() {
    this.page();
    this.permissionTree();
  },
  data() {
    return {
      tableMaxHeight,
      tableData,
      dialogFormVisible,
      dialogStatus,
      loading,
      operationMap,
      selected: Object.assign({}, rowSelected),
      pagination: Object.assign({}, pagination),
      pageReq: Object.assign({}, pageReq),
      dataOperating: {
        name: '',
        summary: '',
        status: '启用',
        permissionKeys: []
      },
      permissionGrantVisible: false,
      treeProps: {label: 'name'},
      permissionTreeList: [],
      permissionIdsGranted: []
    }
  },
  methods: {
    page() {
      if (!checkPermission(['role:list'])) {
        return;
      }
      this.loading = true;
      new Promise(() => {
        page(this.pageReq).then(pageResult => {
          this.tableData = pageResult.data;
          this.pagination.current = pageResult.pageCurrent
          this.pagination.total = pageResult.total
        }).finally(() => {
          this.loading = false;
        });
      });
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      Object.assign(this.dataOperating, row);
      this.permissionGranted()
      this.dialogFormVisible = true
    },
    handleCreate() {
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys([]);
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    doCreateOrUpdate(dialogStatus) {
      if (dialogStatus === 'create') {
        if (this.$refs.tree) {
          this.dataOperating.permissionKeys = this.$refs.tree.getCheckedKeys();
        }
        this.createData(this.dataOperating);
      } else if (dialogStatus === 'update') {
        this.updateData();
      }
      this.dialogFormVisible = false
    },
    createData(role) {
      return new Promise(() => {
        create(role).then(() => {
          this.dialogFormVisible = false;
          this.resetDataOperating();
          this.page();
        });
      });
    },
    updateData() {
      return new Promise(() => {
        update(this.dataOperating).then(() => {
          this.dialogFormVisible = false;
          this.page();
        })
      });
    },
    doDelete(id) {
      new Promise(() => {
        doDelete([id]).finally(() => {
          this.prevPageIfPageLastOne()
          this.page();
        });
      });
    },
    dialogClose() {
      if (this.dialogStatus === 'update') {
        this.resetDataOperating();
      }
    },
    resetDataOperating() {
      this.dataOperating = {
        name: '',
        summary: '',
        status: '启用',
        permissionKeys: []
      };
      this.permissionIdsGranted = [];
    },
    handleCheckChange(node, checked) {
      if (checked) {
        this.checkParentNode(node);
      } else if (node.children) {
        this.uncheckChildrenNode(node);
      }
    },
    checkParentNode(node) {
      let keys = this.$refs.tree.getCheckedKeys();
      keys.push(node.parentId);
      this.$refs.tree.setCheckedKeys(keys);
    },
    uncheckChildrenNode(node) {
      let childrenIds = node.children.map(node => {
        return node.id;
      });
      let keys = this.$refs.tree.getCheckedKeys().filter(id => {
        return childrenIds.indexOf(id) === -1;
      });
      this.$refs.tree.setCheckedKeys(keys);
    },
    handleGrantPermission() {
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys(this.permissionIdsGranted)
      }
      this.permissionGrantVisible = true;
    },
    confirmGrant() {
      const set = new Set(this.$refs.tree.getCheckedNodes().map(p => {
        return p.permissionKey;
      }));
      this.dataOperating.permissionKeys = Array.from(set);
      this.permissionGrantVisible = false;
    },
    permissionGranted() {
      return new Promise(() => {
        grantedTo(this.dataOperating.id).then(value => {
          const set = new Set();
          value.data.forEach(p => {
            this.permissionIdsGranted.push(p.id);
            set.add(p.permissionKey)
          })
          this.dataOperating.permissionKeys = Array.from(set);
        });

      });
    },
    permissionTree() {
      new Promise(() => {
        permissionTree().then(value => {
          this.permissionTreeList = value.data;
        });
      });
    },
    deleteMulti(ids) {
      doDelete(ids).then(() => {
        this.prevPageIfPageLastOne()
        this.page();
      })
    },
    handlePageChange,
    handleSizeChange,
    checkPermission,
    prevPageIfPageLastOne,
    handleDeleteMulti,
    handleSelectionChange,
  },
  filters: {
    tagFilter: statusTagFilter
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  ::v-deep .apiInfo-class {
    width: 320px;
    margin-top: 15px;
    background-color: #f0f9eb;
    color: #67c23a;
    padding: 8px 16px;
    border-radius: 4px;
    display: inline-block;
  }

  ::v-deep .permission-sourceCode {
    margin-left: 15px;
  }

  ::v-deep .permission-tag {
    background-color: #ecf5ff;
  }
}

.table-wrapper {
  height: calc(100% - 60px);
}
</style>

