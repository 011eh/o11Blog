<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate"
                 :disabled="!checkPermission(['permission:create'])">
        添加
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search"
                  @click="list" :disabled="!checkPermission(['permission:list'])">
        查询
      </el-button>
    </div>
    <el-table v-loading="this.loading" :data="tableData" style="width: 100%;" row-key="id" :expand-row-keys="expandRowIds" border
              :tree-props="{ children: 'children' }">
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="resourceType" label="资源类型">
        <template slot-scope="{row}">
          <el-tag :type="row.resourceType | tagFilter">{{ row.resourceType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="permissionKey" label="关键字"/>
      <el-table-column align="center" prop="path" label="路由"/>
      <el-table-column align="center" prop="meta.icon" label="图标">
        <template slot-scope="{row}" v-if="row.resourceType!=='操作'">
          <i v-if="notNull(row.meta.icon) && isElIcon(row.meta.icon)"
             :class="row.meta.icon"/>
          <svg-icon class="iconInDataOperatingDialog"
                    v-if="notNull(row.meta.icon) && !isElIcon(row.meta.icon)"
                    :icon-class="row.meta.icon"/>
          <span style="font-size: 5px" v-if="!notNull(row.meta.icon)">无</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | tagFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sort" label="排序"/>
      <el-table-column fixed="right" label="Actions" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)"
                     :disabled="!checkPermission(['permission:update'])">
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small" :disabled="!checkPermission(['permission:delete'])">
                删除
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="dataOperating" label-position="left" label-width="23%"
               style="width: 60%; margin-left: 35px">
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="dataOperating.name"/>
        </el-form-item>
        <el-form-item label="权限关键字" prop="permissionKey">
          <el-input v-model="dataOperating.permissionKey"/>
        </el-form-item>
        <el-form-item label="资源类型" prop="resourceType">
          <el-select v-model="dataOperating.resourceType" class="m-2" @change="resourceTypeChange" placeholder="无">
            <el-option
              v-for="item in resourceTypeOptions"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父级资源" prop="parentId">
          <el-select v-model="dataOperating.parentId" class="m-2" :disabled="dataOperating.resourceType==='一级菜单'"
                     placeholder="无"
          >
            <el-option v-for="item in parentOptionFilter" :key="item.id" :label="item.name" :value="item.id">
              <span>{{ item.name }} <el-tag style="margin-left: 10px" size="small"
                                            :type="item.resourceType | tagFilter">{{ item.resourceType }}</el-tag>
              </span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="dataOperating.sort" :min="1" :max="100"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="dataOperating.status"
            size="large"
            active-value="启用"
            inactive-value="禁用"
          />
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
          <el-form-item label="重定向地址" prop="redirect">
            <el-input v-model="dataOperating.redirect"/>
          </el-form-item>
          <el-form-item label="图标" prop="meta.icon">
            <template>
              <el-button class="iconInDataOperatingDialog" @click="iconDialogVisible = true">
                <i v-if="notNull(dataOperating.meta.icon) && isElIcon(dataOperating.meta.icon)"
                   :class="dataOperating.meta.icon"/>
                <svg-icon class="iconInDataOperatingDialog"
                          v-if="notNull(dataOperating.meta.icon) && !isElIcon(dataOperating.meta.icon)"
                          :icon-class="dataOperating.meta.icon"/>
                <span style="font-size: 5px" v-if="!notNull(dataOperating.meta.icon)">无</span>
              </el-button>
            </template>
          </el-form-item>
          <el-form-item label="固定在面包屑" prop="meta.affix">
            <el-radio v-model="dataOperating.meta.affix" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.affix" :label="null" border>否</el-radio>
          </el-form-item>
          <el-form-item label="禁用Vue缓存" prop="meta.noCache">
            <el-radio v-model="dataOperating.meta.noCache" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.noCache" :label="null" border>否</el-radio>
          </el-form-item>
          <el-form-item label="面包屑上显示" prop="breadcrumb">
            <el-radio v-model="dataOperating.meta.breadcrumb" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.meta.breadcrumb" :label="null" border>否</el-radio>
          </el-form-item>
          <el-form-item label="在侧边栏隐藏" prop="hidden">
            <el-radio v-model="dataOperating.hidden" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.hidden" :label="null" border>否</el-radio>
          </el-form-item>
          <el-form-item v-if="dataOperating.resourceType==='一级菜单'" label="始终作为嵌套菜单" prop="alwaysShow">
            <el-radio v-model="dataOperating.alwaysShow" :label="true" border>是</el-radio>
            <el-radio v-model="dataOperating.alwaysShow" :label="null" border>否</el-radio>
          </el-form-item>
        </div>
      </el-form>
      <el-dialog
        :visible.sync="iconDialogVisible"
        title="修改图标"
        append-to-body>

        <div style="padding-bottom:5px;text-align: right">
          <el-button @click="cancelIcon">取消图标</el-button>
        </div>

        <el-tabs type="border-card">
          <el-tab-pane label="Icons">
            <div class="grid">
              <div v-for="item of svgIcons" :key="item" @click="handleSelectIcon(item)">
                <div class="icon-item">
                  <svg-icon :icon-class="item" class-name="disabled"/>
                  <span class="iconSpan">{{ item }}</span>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="Element-UI Icons">
            <div class="grid">
              <div v-for="item of elementIcons" :key="item" @click="handleSelectIcon('el-icon-' + item)">
                <div class="icon-item">
                  <i :class="'el-icon-' + item"/>
                  <span class="iconSpan">{{ item }}</span>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
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
import {create, detail, doDelete, list, update} from "@/api/permission";
import {routerMap} from "@/utils/routers";
import svgIcons from '@/icons/svg-icons'
import elementIcons from '@/icons/element-icons'
import {dialogFormVisible, dialogStatus, loading, operationMap, tableData, tagFilter} from '@/utils/tableBase'
import {permissionSelect} from "@/api/sysBase";
import checkPermission from '@/utils/permission.js'

export default {
  data() {
    return {
      svgIcons, elementIcons,
      componentOptions: Object.keys(routerMap),
      resourceTypeOptions: ['一级菜单', '二级菜单', '操作'],

      tableData,
      operationMap,
      dialogStatus,
      loading,
      dialogFormVisible,
      dataOperating: {
        id: null,
        name: '',
        resourceType: '操作',
        permissionKey: '',
        parentId: null,
        sort: 100,
        status: '启用',
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
      expandRowIds: [],
      iconDialogVisible: false,
      parentOptions: []
    }
  },
  created() {
    this.notNull(undefined);
    this.notNull(null);
    this.list()
    this.getParentSelect()
  },
  methods: {
    parentOptionFilter() {
      debugger
      return this.parentOptions.filter(p => {
        debugger
        if (this.dataOperating.id === p.id) {
          return false;
        }
        if (this.dataOperating.resourceType === '二级菜单') {
          return p.resourceType === '一级菜单';
        }
        return p.resourceType !== '操作';
      })
    },
    list() {
      if (!checkPermission(['permission:list'])) {
        return;
      }
      this.loading = true;
      this.expandRowIds = []
      return new Promise(() => {
        list().then(res => {
          const {data: permissions} = res
          this.tableData = permissions;
          for (let permission of permissions) {
            this.expandRowIds.push(permission.id);
          }
        }).finally(() => {
          this.loading = false
        });
      });
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      return new Promise(() => {
        detail(row.id).then(res => {
          let {data} = res
          data.meta = Object.assign(this.dataOperating.meta, data.meta);
          this.dataOperating = Object.assign({
            name: null,
            resourceType: '操作',
            permissionKey: '',
            parentId: null,
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
          }, data)
          this.dialogFormVisible = true
        });
      })

    },
    handleCreate() {
      this.resetDataOperating()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    createData(data) {
      return new Promise(() => {
        create(data).then(() => {
          this.dialogFormVisible = false;
          this.list();
        });
      })
    },
    updateData(data) {
      return new Promise(() => {
        update(data).then(() => {
          this.dialogFormVisible = false;
          this.list();
        });
      })
    },
    resetDataOperating() {
      this.dataOperating = {
        name: '',
        resourceType: '操作',
        permissionKey: '',
        parentId: null,
        sort: 100,
        status: '启用',
        path: null,
        component: null,
        meta: {
          icon: null,
          affix: null,
          title: null,
          noCache: true,
          breadcrumb: null
        },
        hidden: null,
        redirect: null,
        alwaysShow: null
      }
    },
    isElIcon(icon) {
      return icon.includes('el-icon')
    },
    notNull(icon) {
      return !!icon;

    },
    handleSelectIcon(icon) {
      this.dataOperating.meta.icon = icon
      this.iconDialogVisible = false
    },
    cancelIcon() {
      this.dataOperating.meta.icon = null
      this.iconDialogVisible = false
    },
    getParentSelect() {
      return new Promise(() => {
        return permissionSelect().then(res => {
          this.parentOptions = res.data
        });
      })
    },
    doCreateOrUpdate(action) {
      let dataSend = null
      if (this.dataOperating.resourceType !== '操作') {
        this.dataOperating.meta.title = this.dataOperating.name
        dataSend = this.dataOperating
      } else if (this.dataOperating.resourceType === '操作') {
        dataSend = (({
                       id,
                       name,
                       permissionKey,
                       resourceType,
                       parentId,
                       sort,
                       status
                     }) => ({
          id,
          name,
          permissionKey,
          resourceType,
          parentId,
          sort,
          status
        }))(this.dataOperating)
      }
      if (action === 'create') {
        this.createData(dataSend);
        return;
      }
      if (action === 'update') {
        this.updateData(dataSend);
      }
    },
    resourceTypeChange(value) {
      if (value === '一级菜单') {
        this.dataOperating.parentId = null;
      }
      if (value === '二级菜单') {
        this.dataOperating.alwaysShow = null;
      }
    },
    doDelete(id) {
      return new Promise(() => {
        doDelete([id]).then(() => {
          this.list();
        });
      });
    },
    checkPermission
  },
  filters: {
    tagFilter
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

.grid {
  position: relative;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 2fr));
}

.icon-item {
  margin: 0;
  height: 85px;
  text-align: center;
  width: 102px;
  float: left;
  font-size: 22px;
  color: #24292e;
  cursor: pointer;
}

.iconSpan {
  display: block;
  font-size: 16px;
  margin-top: 10px;
}

.disabled {
  pointer-events: none;
}

.iconInDataOperatingDialog {
  font-size: 15px;
}

</style>
