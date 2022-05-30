<template>
  <div class="app-container">
    <div class="filter-container">
      <el-popconfirm style="margin-left: 5px" title="确定删除吗？" @onConfirm="handleDeleteMulti">
        <template #reference>
          <el-button class="filter-item" type="danger" icon="el-icon-delete" plain circle
                     />
        </template>
      </el-popconfirm>

      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-edit" @click="handleCreate"
                 >
        添加
      </el-button>
      <el-input v-model="pageReq.keyword" placeholder="名称/参数键值" style="width: 200px; margin-left: 10px"
                clearable class="filter-input"/>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search"
                 @click="page" >
        查询
      </el-button>
    </div>


    <el-table v-loading="this.loading" style="width: 100%;" :max-height="tableMaxHeight" :data="tableData" row-key="id"
              @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection"/><!--   todo 表格-->
      <el-table-column align="center" type="index" label="序号"/>
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="paramKey" label="参数键值"/>
      <el-table-column align="center" prop="value" label="参数值"/>
      <el-table-column align="center" prop="sort" label="排序"/>
      <el-table-column fixed="right" label="操作" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="small" @click="handleUpdate(row)"
                     >
            编辑
          </el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id)">
            <template #reference>
              <el-button type="danger" size="small" >
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
        <!--        todo 表单-->
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataOperating.name"/>
        </el-form-item>
        <el-form-item label="参数键值" prop="summary">
          <el-input v-model="dataOperating.paramKey"/>
        </el-form-item>
        <el-form-item label="参数值" prop="value">
          <el-input v-model="dataOperating.value"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="dataOperating.sort" :min="1" :max="100"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dataOperating.remark"/>
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
  tableData,
  tableMaxHeight
} from "@/utils/tableBase";
import checkPermission from "@/utils/permission";
import {createSysParam, deleteSysParam, sysParamPage, updateSysParam} from "@/api/sysBase";

export default {
  created() {
    this.page();
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
      pageReq: Object.assign(pageReq),
      rowSelected,

      dataOperating: {} // todo 表单数据
    }
  },
  methods: {
    page() {
      this.loading = true;
      return new Promise(() => {
        // todo 分页请求
        sysParamPage(this.pageReq).then(res => {
          this.tableData = res.data;
          this.pagination.current = res.pageCurrent
          this.pagination.total = res.total
        }).finally(() => {
          this.loading = false;
        });
      });
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
    doCreateOrUpdate() {
      if (this.dialogStatus === 'create') {
        this.createData();
      } else if (this.dialogStatus === 'update') {
        this.updateData();
      }
    },
    createData() {
      return new Promise(() => {
        // todo 创建
        createSysParam(this.dataOperating).then(() => {
          this.dialogFormVisible = false;
          this.page();
          this.resetDataOperating();
        });
      });
    },
    updateData() {
      return new Promise(() => {
        // todo 更新
        updateSysParam(this.dataOperating).then(() => {
          this.dialogFormVisible = false;
          this.page();
        });
      }).then();
    },
    dialogClose() {
      if (this.dialogStatus === 'update') {
        this.resetDataOperating();
      }
    },
    doDelete(id) {
      return new Promise(() => {
        // todo 删除
        deleteSysParam([id]).then(() => {
          this.prevPageIfPageLastOne();
          this.page();
        });
      });
    },
    deleteMulti(ids) {
      return new Promise(() => {
        // todo 多选删除
        deleteSysParam(ids).then(() => {
          this.prevPageIfPageLastOne()
          this.page();
        })
      });
    },
    resetDataOperating() {// todo 重置数据
      this.dataOperating = {}
    },
    handlePageChange,
    handleSizeChange,
    checkPermission,
    prevPageIfPageLastOne,
    handleDeleteMulti,
    handleSelectionChange
  },
  filters: {}
}
</script>

<style lang="scss" scoped>
</style>

