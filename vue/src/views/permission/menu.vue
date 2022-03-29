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

    <el-dialog v-cloak :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="dataOperating" label-position="left" label-width="33%"
               style="width: 60%; margin-left: 35px">
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="dataOperating.name"/>
        </el-form-item>
        <el-form-item label="资源类型" prop="resourceType">
          <el-select v-model="dataOperating.resourceType" class="m-2" placeholder="无">
            <el-option
              v-for="item in resourceTypeOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="权限关键字" prop="permissionKey">
          <el-input v-model="dataOperating.permissionKey"/>
        </el-form-item>
        <el-form-item label="父级资源" prop="parentId">
          <el-select v-model="dataOperating.parentId" class="m-2" placeholder="无"
          >
            <el-option
              v-for="item in flattedData"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>

        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="dataOperating.sort" :min="1" :max="100"/>
        </el-form-item>

        <div v-if="dataOperating.resourceType!=='操作'">
          <el-form-item label="路由路径" prop="path">
            <el-input v-model="dataOperating.path"/>
          </el-form-item>
          <el-form-item label="Vue组件名" prop="component">
            <el-select v-model="dataOperating.component" class="m-2" placeholder="无">
              <el-option
                v-for="item in componentOptions"
                :key="item"
                :label="item"
                :value="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item hidden label="路由标题" prop="title">
            <el-input v-model="dataOperating.name"/>
          </el-form-item>

          <el-form-item v-if="" label="面包屑重定向地址" prop="redirect">
            <el-input v-model="dataOperating.redirect"/>
          </el-form-item>
          <el-form-item label="图标" prop="meta.icon">
            <svg-icon icon-class="el-icon-phone"/>
          </el-form-item>
          <el-form-item label="固定在面包屑" prop="meta.affix">
            <el-radio v-model="dataOperating.meta.affix" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.affix" :label="false" border>否</el-radio>
          </el-form-item>
          <el-form-item label="开启Vue缓存" prop="meta.noCache">
            <el-radio v-model="dataOperating.meta.noCache" :label="false" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.noCache" :label="true" border>否</el-radio>
          </el-form-item>
          <el-form-item label="面包屑上显示" prop="breadcrumb">
            <el-radio v-model="dataOperating.meta.breadcrumb" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.breadcrumb" :label="false" border>否</el-radio>
          </el-form-item>
          <el-form-item label="在侧边栏隐藏" prop="hidden">
            <el-radio v-model="dataOperating.hidden" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.hidden" :label="false" border>否</el-radio>
          </el-form-item>
          <el-form-item label="总是显示菜单" prop="alwaysShow">
            <el-radio v-model="dataOperating.alwaysShow" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.alwaysShow" :label="false" border>否</el-radio>
          </el-form-item>
        </div>
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
import {routerMap} from "@/utils/routers";

export default {
  data() {
    return {
      componentOptions: Object.keys(routerMap),
      permissionKeyOptions: [],
      resourceTypeOptions: ['一级菜单', '二级菜单', '操作'],

      tableData: [],
      flattedData: [],
      dataOperating: {
        id: null,
        name: '',
        resourceType: '操作',
        permissionKey: '',
        parentId: '',
        sort: 100,
        path: null,
        component: null,
        meta: {
          icon: null,
          affix: null,
          title: null,
          noCache: null,
          breadcrumb: null
        },
        hidden: null,
        redirect: null,
        alwaysShow: null
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
    Object.assign(this.dataOperating, this.dataTemplate)
    this.list()
  },
  methods: {
    list() {
      this.listLoading = true
      this.flattedData = []
      return new Promise(() => {
        list().then(res => {
          const {data: permissions} = res
          this.tableData = permissions;
          for (let permission of permissions) {
            this.expandRowIds.push(permission.id);
          }
          this.flatTableData(this.tableData)
        }).finally(() => {
          this.listLoading = false
        });
      });
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.dataOperating = Object.assign({}, row)
    },
    handleCreate() {
      this.resetDataOperating()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    createData() {
      console.log(this.dataOperating)
    },
    updateData() {
      console.log(this.dataOperating)
    },
    resetDataOperating() {
      this.dataOperating = {
        name: '',
        resourceType: '操作',
        permissionKey: '',
        parentId: '',
        sort: 100,
        path: null,
        component: null,
        meta: {
          icon: null,
          affix: null,
          title: null,
          noCache: null,
          breadcrumb: null
        },
        hidden: null,
        redirect: null,
        alwaysShow: null
      }
    },
    flatTableData(list) {
      for (const item of list) {
        const {id, name: name = '无'} = item
        this.flattedData.push({id, name})
        if (item.children) {
          this.flatTableData(item.children)
        }
      }
    },
  },
  filters: {
    tagFilter(type) {
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
