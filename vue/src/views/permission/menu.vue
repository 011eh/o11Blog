<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit"
                 @click="handleCreate"
      >
        添加
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search"
                 @click="list">
        查询
      </el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
      row-key="id"
      :expand-row-keys="expandRowIds"
      v-loading="listLoading"
      border
      :tree-props="{ children: 'children' }"
    >
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="resourceType" label="资源类型">
        <template slot-scope="{row}">
          <el-tag :type="row.resourceType | tagFilter">{{ row.resourceType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="permissionKey" label="关键字"/>
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | tagFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sort" label="排序"/>
      <el-table-column label="Actions" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button type="danger" size="small">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="dataOperating" label-position="left" label-width="100px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="dataOperating.name"/>
        </el-form-item>
        <el-form-item label="权限关键字" prop="parentId">

        </el-form-item>
        <el-form-item label="父级资源" prop="parentId">
          <el-input v-model="dataOperating.parentId"/>
        </el-form-item>
        <el-form-item label="排序" prop="parentId">

        </el-form-item>
        <el-form-item label="资源类型" prop="parentId">

        </el-form-item>
        <el-form-item label="图标" prop="parentId">

        </el-form-item>
        <el-form-item label="固定在面包屑" prop="parentId">
          <el-switch
            v-model="dataOperating.alwaysShow"
            class="ml-2"
            inline-prompt
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="总是显示菜单"
          />
        </el-form-item>
        <el-form-item label="Vue缓存" prop="parentId">
          <el-switch
            v-model="dataOperating.alwaysShow"
            class="ml-2"
            inline-prompt
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="总是显示菜单"
          />
        </el-form-item>
        <el-form-item label="面包屑上显示" prop="parentId">
          <el-switch
            v-model="dataOperating.alwaysShow"
            class="ml-2"
            inline-prompt
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="总是显示菜单"
          />
        </el-form-item>
        <el-form-item label="路由路径" prop="parentId">

        </el-form-item>
        <el-form-item label="面包屑重定向地址" prop="parentId">

        </el-form-item>
        <el-form-item label="在侧边栏隐藏" prop="parentId">
          <el-switch
            v-model="dataOperating.alwaysShow"
            class="ml-2"
            inline-prompt
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="总是显示菜单"
          />
        </el-form-item>
        <el-form-item label="Vue组件名" prop="parentId">

        </el-form-item>
        <el-form-item label="" prop="parentId">
          <el-switch
            v-model="dataOperating.alwaysShow"
            class="ml-2"
            inline-prompt
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="总是显示菜单"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {list} from "@/api/permission";

export default {
  data() {
    return {
      tableData: [],
      dataOperating: {
        "id": "1497825987499515915",
        "createTime": "2022-03-24 15:19:38",
        "updateTime": "2022-03-24 15:19:38",
        name: "资源权限",
        "permissionKey": "router:any",
        "parentId": "0",
        "sort": 100,
        "resourceType": "菜单",
        "meta": {
          "icon": "lock",
          "affix": false,
          "title": "资源权限",
          "noCache": true,
          "breadcrumb": true
        },
        "path": "/permission",
        "hidden": false,
        "redirect": "/permission/page",
        "component": "layout",
        "alwaysShow": false
      },
      listLoading: false,
      expandRowIds: [],
      operationMap: {
        create: '添加',
        update: '修改'
      },
      dialogStatus: '',
      dialogFormVisible: false
    }
  },
  computed: {},
  created() {
    this.list()
  },
  methods: {
    list() {
      this.listLoading = true
      return new Promise(resolve => {
        list().then(res => {
          const {data: permissions} = res
          this.tableData = permissions;
          for (let permission of permissions) {
            this.expandRowIds.push(permission.id);
          }
        }).finally(() => {
          this.listLoading = false
        });
      });
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true

      this.dataOperating = {
        "name": "资源权限",
        "permissionKey": "router:any",
        "parentId": "0",
        "sort": 100,
        "resourceType": "菜单",
        "meta": {
          "icon": "lock",
          "affix": false,
          "title": "资源权限",
          "noCache": true,
          "breadcrumb": true
        },
        "path": "/permission",
        "hidden": false,
        "redirect": "/permission/page",
        "component": "layout",
        "alwaysShow": false
      };
    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    createData() {

    },
    updateData() {
    },
  },
  filters: {
    tagFilter(type) {
      if (type === '菜单') {
        return 'success'
      }
      if (type === '操作') {
        return 'warning'
      }
      if (type === '启用') {
        return
      }
      if (type === '禁用') {
        return 'info'
      }
    },
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .roles-table {
    margin-top: 30px;
  }

  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
