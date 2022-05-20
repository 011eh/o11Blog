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
      <el-table-column align="center" prop="name" label="名称"/>
      <el-table-column align="center" prop="paramKey" label="参数键值"/>
      <el-table-column align="center" prop="value" label="参数值"/>
      <el-table-column align="center" prop="sort" label="排序"/>
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
  dialogStatus, handlePageChange, handleSizeChange,
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
      tableData: [{
        id: "1",
        createTime: "2022-05-19 02:25:57",
        updateTime: "2022-05-19 02:25:57",
        name: "管理员默认密码",
        paramKey: "adminDefaultPassword",
        value: "11111",
        sort: 100,
      }],
      dialogFormVisible,
      dialogStatus,
      loading,
      operationMap,
      selected: Object.assign({}, selected),
      pagination: Object.assign({}, pagination),
      pageReq: Object.assign(pageReq),
    }
  },
  methods: {
    page() {

    },
    handleCreate() {

    },
    handleUpdate() {

    },
    doDelete(id) {

    },
    handleDeleteMulti() {

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

