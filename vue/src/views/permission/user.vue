<template>
  <div class="app-container">
    <div class="filter-container">
      <el-popconfirm style="margin-left: 5px" title="确定删除吗？" @onConfirm="handleDeleteMulti">
        <template #reference>
          <el-button class="filter-item" type="danger" icon="el-icon-delete" plain circle
                     :disabled="!checkPermission(['admin:update'])"/>
        </template>
      </el-popconfirm>

      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate"
                 :disabled="!checkPermission(['admin:create'])">
        添加
      </el-button>
      <el-input v-model="pageReq.keyword" placeholder="用户名/昵称" style="width: 200px; margin-left: 10px"
                clearable class="filter-item"/>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page"
                 :disabled="!checkPermission(['admin:list'])">
        查询
      </el-button>
    </div>
    <el-table ref="table" v-loading="this.loading" :data="tableData" style="width: 100%;" row-key="id"
              @selection-change="handleSelectionChange" :max-height="tableMaxHeight">
      <el-table-column type="selection"/>
      <el-table-column align="center" label="序号" width="50" type="index"/>
      <el-table-column align="center" prop="username" label="用户名"/>
      <el-table-column align="center" prop="nickName" label="昵称"/>
      <el-table-column align="center" prop="avatar" label="头像">
        <template slot-scope="{row,$index}">
          <el-avatar :size="65" :src="row.avatar" @error="true">
            <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"/>
          </el-avatar>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="roleName" label="角色">
        <template slot-scope="{row,$index}">
          <div v-if="!row.roleName">无</div>
          <div v-else>
            {{ row.roleName }}
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | tagFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="Actions" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)"
                     :disabled="!checkPermission(['admin:update'])">
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗？" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small" :disabled="!checkPermission(['admin:delete'])">
                删除
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="operationMap[dialogStatus]" :visible.sync="dialogFormVisible" @closed="dialogClosed">
      <el-form ref="dataForm" :model="dataOperating" label-position="left" label-width="33%"
               style="width: 60%; margin-left: 35px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dataOperating.username"/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="dataOperating.nickName"/>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="dataOperating.roleId" class="m-2" placeholder="无">
            <el-option key="" label="无" value=""/>
            <el-option v-for="item in roleOptions" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <pan-thumb v-if="this.dataOperating.avatar" :image="this.dataOperating.avatar"/>
          <pan-thumb v-else image="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"/>
          <el-button @click="uploaderToggle">设置头像</el-button>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="dataOperating.status"
            size="large"
            active-value="启用"
            inactive-value="禁用"
          />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="doCreateOrUpdate">
          确定
        </el-button>
      </span>
      </template>
    </el-dialog>
    <el-footer style="height: 0">
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
    <my-upload field="file" v-model="toggle" :width="200" :height="200"
               url="http://localhost:9527/api/sysBase/uploadAvatar" img-format="jpg" @crop-upload-success="uploadSuccess"/>
  </div>
</template>


<script>

import PanThumb from '@/components/PanThumb'
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
  selected,
  tableData,
  tableMaxHeight,
  tagFilter
} from "@/utils/tableBase";
import {roleSelect} from "@/api/sysBase";
import {create, doDelete, page, update} from "@/api/admin";
import checkPermission from "@/utils/permission";
import {successMsg} from "@/utils/msg";
import myUpload from 'vue-image-crop-upload/upload-2.vue'

export default {
  created() {
    this.page();
    this.getRoleSelect();
  },
  data() {
    return {
      tableMaxHeight,
      operationMap,
      loading,
      dialogFormVisible,
      dialogStatus,
      tableData,
      selected: Object.assign({}, selected),
      pagination: Object.assign({}, pagination),
      pageReq: Object.assign({}, pageReq),
      dataOperating: {
        username: '',
        nickName: '',
        roleId: null,
        status: '启用'
      },
      roleOptions: [],
      toggle: false,

    }
  },
  methods: {
    page() {
      if (!checkPermission(['admin:list'])) {
        return;
      }

      this.loading = true;
      return new Promise(() => {
        page(this.pageReq).then(pageResult => {
          this.tableData = pageResult.data;
          this.pagination.current = pageResult.pageCurrent
          this.pagination.total = pageResult.total
        }).finally(() => {
          this.loading = false;
        });
      });
    },
    doCreateOrUpdate() {
      if (this.dialogStatus === 'create') {
        this.createData();
      } else if (this.dialogStatus === 'update') {
        this.updateData();
      }
    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true;
    },
    handleUpdate(row) {
      Object.assign(this.dataOperating, row);
      this.dialogStatus = 'update'
      this.dialogFormVisible = true;
    },
    doDelete(id) {
      return new Promise(() => {
        doDelete([id]).then((res) => {
          this.prevPageIfPageLastOne()
          this.page();
          this.successMsg(res);
        });
      });
    },
    dialogClosed() {
      if (this.dialogStatus === 'update') {
        this.resetDataOperating();
      }
    },
    createData() {
      return new Promise(() => {
        create(this.dataOperating).then(res => {
          this.dialogFormVisible = false;
          this.resetDataOperating();
          this.page();
          this.successMsg(res);
        });
      });
    },
    updateData() {
      return new Promise(() => {
        update(this.dataOperating).then((res) => {
          this.dialogFormVisible = false;
          this.page();
          this.successMsg(res);
        });
      });
    },
    getRoleSelect() {
      return new Promise(() => {
        roleSelect().then(value => {
          this.roleOptions = value.data;
        });
      });
    },
    resetDataOperating() {
      this.dataOperating = {
        username: '',
        nickName: '',
        roleId: null,
        status: '启用'
      }
    },
    uploadSuccess(result) {
      this.dataOperating.avatar = result.data;
    },
    deleteMulti(ids) {
      doDelete(ids).then((res) => {
        this.prevPageIfPageLastOne()
        this.page();
        this.successMsg(res);
      })
    },
    uploaderToggle() {
      this.toggle = !this.toggle;
    },
    handleSelectionChange,
    handleDeleteMulti,
    checkPermission,
    prevPageIfPageLastOne,
    handlePageChange,
    handleSizeChange,
    successMsg
  },
  filters: {
    tagFilter,
  },
  components: {myUpload, PanThumb}
}
</script>

<style lang="scss" scoped>
.grid {
  position: relative;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
}

.icon-item {
  margin: 20px;
  height: 85px;
  text-align: center;
  width: 100px;
  float: left;
  font-size: 30px;
  color: #24292e;
  cursor: pointer;
}

.disabled {
  pointer-events: none;
}

.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 60px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: .2s;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
  line-height: 120px;
}
</style>
