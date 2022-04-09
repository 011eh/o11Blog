<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page">
        查询
      </el-button>
    </div>
    <el-table :data="tableData" style="width: 100%;" row-key="id" border>
      <el-table-column align="center" label="序号" width="50" type="index"/>
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="summary" label="简介"/>
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | tagFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="Actions" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small">
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
      <el-dialog :visible.sync="permissionGrantVisible" title="授予权限" append-to-body>

        <el-tree :check-strictly="true" ref="tree" :data="permissionTreeList" show-checkbox default-expand-all node-key="id"
                 highlight-current :props="treeProps" :expand-on-click-node="false" :check-on-click-node="true"
                 :default-checked-keys="this.dataOperating.permissionIds" @check-change="handleCheckChange"/>

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
      <el-pagination
        background
        layout="prev, pager, next"
        :total="50"
        class="mt-4"
      />
    </el-footer>
  </div>
</template>

<script>

import {dialogFormVisible, dialogStatus, loading, operationMap, tagFilter} from "@/utils/tableBase";
import {permissionTree} from "@/api/sysConfig";
import {page} from "@/api/role";
import {grantedTo} from "@/api/permission";

export default {
  created() {
    this.permissionTree();
    this.page(1, 10);
  },

  data() {
    return {
      tableData: [
        {
          id: "1497825987499515916",
          createTime: "2022-02-22T02:30:39",
          updateTime: "2022-02-22T02:30:39",
          name: "普通管理员",
          summary: "普通管理员有普通权限",
          status: "启用",
          permissionIds: [
            "1497825987499515915",
            "1497825987499515907"
          ]
        }
      ],
      dialogFormVisible,
      dialogStatus,
      loading,
      operationMap,
      dataOperating: {
        status: '启用',
        permissionIds: []
      },
      permissionGrantVisible: false,
      treeProps: {label: 'name'},
      permissionTreeList: [],
      pageReq: {
        current: 1,
        size: 10,
        keyword: '',
      }
    }
  },
  methods: {
    page() {
      new Promise(() => {
        page(this.pageReq).then(value => {
          this.tableData = value.data;
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
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dataOperating = row
      this.permissionList()
      this.dialogFormVisible = true
    },
    handleCreate() {
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys([]);
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    doDelete(id) {
    },
    doCreateOrUpdate(dialogStatus) {
      if (dialogStatus === 'create') {
      } else if (dialogStatus === 'update') {
      }
      this.dialogFormVisible = false
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
    confirmGrant() {
      this.dataOperating.permissionIds = this.$refs.tree.getCheckedKeys();
      this.permissionGrantVisible = false;
    },
    handleGrantPermission() {
      this.permissionGrantVisible = true
    },
    dialogClose() {
      if (this.dialogStatus === 'update') {
        this.dataOperating = {
          status: '启用',
          permissionIds: []
        }
      }
    },
    permissionList() {
      return new Promise(() => {
        grantedTo(this.dataOperating.id).then(value => {
          this.$refs.tree.setCheckedKeys(value.data)
        });
      });
    },
  },
  filters: {
    tagFilter
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  ::v-deep .permission-alert {
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
</style>

