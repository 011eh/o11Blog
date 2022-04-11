<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-input v-model="pageReq.keyword" placeholder="名称" style="width: 200px; margin-left: 10px"
                clearable class="filter-item"/>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page"
                 v-loading.fullscreen.lock="loading">
        查询
      </el-button>
    </div>
    <el-table :data="tableData" style="width: 100%;" row-key="id" :max-height="tableMaxHeight">
      <el-table-column align="center" label="序号" width="50" type="index"/>
      <el-table-column align="center" prop="username" label="用户名"/>
      <el-table-column align="center" prop="nickName" label="昵称"/>
      <el-table-column align="center" prop="avatar" label="头像">
        <template slot-scope="{row,$index}">
          <el-avatar :size="70" :src="row.avatar"/>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="nameRole" label="角色">
      </el-table-column>
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
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dataOperating.username"/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="dataOperating.nickName"/>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="dataOperating.roleId" class="m-2" @change="roleChange" placeholder="无">
            <el-option v-for="item in roleOptions" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>

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
        <el-button type="primary" @click="doCreateOrUpdate(dialogStatus)">
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
  </div>
</template>


<script>

import {
  dialogFormVisible,
  dialogStatus,
  loading,
  operationMap,
  pageReq,
  pagination,
  tableMaxHeight,
  tagFilter
} from "@/utils/tableBase";
import {roleSelect} from "@/api/sysConfig";
import {getPage} from "@/api/admin";

export default {
  created() {
    this.page();
    this.getRoleSelect();
  },
  data() {
    return {
      tableMaxHeight,
      tableData: [],
      dialogFormVisible,
      dialogStatus,
      loading,
      pagination,
      operationMap,
      pageReq,
      dataOperating: {
        status: '启用'
      },
      roleOptions: [],
    }
  },
  methods: {
    page() {
      return new Promise(() => {
        getPage(this.pageReq).then(value => {
          this.tableData = value.data;
        });
      });
    },
    doCreateOrUpdate() {

    },
    handleCreate() {
      this.dialogFormVisible = true;
    },
    handleUpdate() {
      this.dialogFormVisible = true;
    },
    doDelete() {

    },
    dialogClose() {

    },
    handlePageChange() {
    },
    handleSizeChange() {
    },
    roleChange(value) {
    },
    getRoleSelect() {
      return new Promise(() => {
        roleSelect().then(value => {
          this.roleOptions = value.data;
          console.log(this.roleOptions)
        });
      });
    },
  },
  filters: {
    tagFilter,
  }
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
</style>
