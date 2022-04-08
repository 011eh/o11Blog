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
    <el-dialog :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible">
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
            <el-button @click="permissionGrantVisible = true">
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
        <el-tree
          :check-strictly="true"
          ref="tree"
          :data="treeVoList"
          show-checkbox
          default-expand-all
          node-key="id"
          highlight-current
          :props="treeProps"
        />
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="permissionGrantVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="setTreeVo">
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
  </div>
</template>

<script>

import {dialogFormVisible, dialogStatus, loading, operationMap, tagFilter} from "@/utils/tableBase";

export default {
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
        status: '启用'
      },
      permissionGrantVisible: false,
      treeProps: {label: 'name'},
      treeVoList: [
        {
          id: '1497825987499515915',
          name: "资源管理",
          "children": [
            {
              id: '1497825987499515911',
              name: "权限管理"
            },
            {
              id: '1497825987499515907',
              name: "角色管理"
            },
            {
              id: '1497811057370992642',
              name: "用户管理"
            }
          ]
        },
        {
          id: '1512344969314701313',
          name: "空页面",
          "children": [
            {
              id: '1512345084280573954',
              name: "空页面列表"
            }
          ]
        },
        {
          id: '1497825987499515911',
          name: "权限管理",
          "children": [
            {
              id: '1497825987499515912',
              name: "权限添加"
            },
            {
              id: '1497825987499515913',
              name: "权限更新"
            },
            {
              id: '1497825987499515914',
              name: "权限删除"
            },
            {
              id: '1512328763396460545',
              name: "权限列表"
            }
          ]
        },
        {
          id: '1497825987499515907',
          name: "角色管理",
          "children": [
            {
              id: '1497825987499515908',
              name: "角色添加"
            },
            {
              id: '1497825987499515909',
              name: "角色更新"
            },
            {
              id: '1497825987499515910',
              name: "角色删除"
            }
          ]
        },
        {
          id: '1497811057370992642',
          name: "用户管理",
          "children": [
            {
              id: '1497811074932543489',
              name: "用户添加"
            },
            {
              id: '1497811107790721025',
              name: "用户更新"
            },
            {
              id: '1497825987499515906',
              name: "用户删除"
            }
          ]
        }
      ]
    }
  },
  methods: {
    page() {

    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dataOperating = row
      this.dialogFormVisible = true
    },
    handleCreate() {
      this.dataOperating = {
        status: '启用'
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    doDelete(id) {
      console.log(id)
    },
    doCreateOrUpdate(dialogStatus) {
      if (dialogStatus === 'create') {
        console.log("c")
      } else if (dialogStatus === 'update') {
        console.log("u")
      }
      this.dialogFormVisible = false
    },
    setTreeVo() {
      this.$refs.tree.setCheckedKeys(['1497825987499515911']);
      console.log(this.$refs.tree.getCheckedKeys());
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

