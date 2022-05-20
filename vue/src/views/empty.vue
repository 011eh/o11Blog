<template>
  <div class="app-container">
    <div class="filter-container">
      <el-popconfirm style="margin-left: 5px" title="确定删除吗？" @onConfirm="handleDeleteMulti">
        <template #reference>
          <el-button class="filter-item" type="danger" icon="el-icon-delete" plain circle
                     :disabled="!checkPermission([''])"/>
        </template>
      </el-popconfirm>

      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate"
                 :disabled="!checkPermission([''])">
        添加
      </el-button>
      <el-input v-model="pageReq.keyword" placeholder="名称/参数键值" style="width: 200px; margin-left: 10px"
                clearable class="filter-input"/>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page"
                 :disabled="!checkPermission([''])">
        查询
      </el-button>
    </div>

    <el-table v-loading="this.loading" style="width: 100%;" :max-height="tableMaxHeight" :data="tableData" row-key="id">
      <el-table-column align="center" type="selection"/>
      <el-table-column align="center" type="index" label="序号"/>
      <el-table-column fixed="right" label="操作" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)"
                     :disabled="!checkPermission([''])">
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small" :disabled="!checkPermission([''])">
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
  handlePageChange,
  handleSizeChange,
  loading,
  operationMap,
  pageReq,
  pagination,
  selected,
  tableMaxHeight
} from "@/utils/tableBase";
import checkPermission from "@/utils/permission";

export default {
  created() {
  },
  data() {
    return {
      tableMaxHeight,
      tableData,
      dialogFormVisible,
      dialogStatus,
      loading,
      operationMap,
      selected: Object.assign({}, selected),
      pagination: Object.assign({}, pagination),
      pageReq: Object.assign(pageReq),

      dataOperating: {}
    }
  },
  methods: {
    page() {

    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true;
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      Object.assign(this.dataOperating, row);
      this.dialogFormVisible = true;
    },
    doDelete(id) {

    },
    handleDeleteMulti() {

    },
    dialogClose() {
      if (this.dialogStatus === 'update') {
        this.resetDataOperating();
      }
    },
    resetDataOperating() {
      this.dataOperating = {}
    },
    handlePageChange,
    handleSizeChange,
    checkPermission
  },
  filters: {}
}
</script>

<style lang="scss" scoped>
</style>

