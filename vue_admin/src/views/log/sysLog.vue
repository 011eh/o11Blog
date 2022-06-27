<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select class="filter-item" multiple v-model="pageReq.operators" placeholder="Select">
        <el-option v-for="item in adminOptions" :key="item.id" :label="item.username" :value="item.username"/>
      </el-select>
      <el-input class="filter-input" clearable v-model="pageReq.operation" placeholder="操作"/>
      <el-input class="filter-input" clearable v-model="pageReq.ip" placeholder="IP"/>
      <el-date-picker class="filter-dateTime" type="datetimerange" start-placeholder="开始时间" end-placeholder="结束时间"
                      :default-time="['00:00:00', '23:59:59']" v-model="timeSelect" value-format="yyyy-MM-dd HH:mm:ss"/>

      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search" @click="page"
                 >查询
      </el-button>

      <el-table  v-loading="this.loading" style="width: 100%;" :max-height="tableMaxHeight" :data="tableData" row-key="id">
        <el-table-column align="center" type="index" width="50px" label="序号"/>
        <el-table-column align="center" width="100px" type="expand" prop="controller" label="接口信息">
          <template #default="props">
            <div class="apiInfo apiInfo-class"><span>接口：</span>{{ props.row.controller }}</div>
            <br>
            <div class="apiInfo apiInfo-other">
              <span>方法：</span>{{ props.row.method }}
              <br>
              <span>参数：</span>{{ props.row.params }}
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="username" label="操作人"/>
        <el-table-column align="center" width="120px" prop="ip" label="IP"/>
        <el-table-column align="center" prop="operation" label="操作"/>
        <el-table-column align="center" prop="uri" label="URI"/>
        <el-table-column align="center" width="140px" prop="httpMethod" label="请求方法"/>
        <el-table-column align="center" width="50px" prop="logStatus" label="状态"/>
        <el-table-column align="center" width="80px" prop="timeCost" label="耗时"/>
        <el-table-column align="center" prop="createTime" label="操作时间"/>
      </el-table>
    </div>


    <el-footer>
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
  rowSelected,
  tableData,
  tableMaxHeight
} from "@/utils/tableBase";
import checkPermission from "@/utils/permission";
import {adminSelect, sysLogPage} from "@/api/sysBase";

export default {
  created() {
    this.page()
    this.adminSelection();
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
      pageReq: Object.assign({operators: [], ip: "", operation: "", startTime: null, endTime: null}, pageReq),

      timeSelect: [],
      adminOptions: []
    }
  },
  methods: {
    page() {
      this.pageReq.startTime = this.timeSelect != null ? this.timeSelect[0] : null;
      this.pageReq.endTime = this.timeSelect != null ? this.timeSelect[1] : null;

      this.loading = true;
      return new Promise(() => {
        sysLogPage(this.pageReq).then(res => {
          this.tableData = res.data;
          this.pagination.current = res.pageCurrent;
          this.pagination.total = res.total;
        }).finally(() => {
          this.loading = false;
        });
      });
    },

    adminSelection() {
      return new Promise(resolve => {
        adminSelect().then(res => {
          this.adminOptions = res.data;
        })
      })
    },

    handleSizeChange,
    handlePageChange,
    checkPermission
  },
  filters: {}
}
</script>

<style lang="scss" scoped>
.apiInfo {
  margin-top: 15px;
  padding: 8px 16px;
  border-radius: 4px;
  display: inline-block;
}

.apiInfo-class {
  color: #67c23a;
  background-color: #f0f9eb;
  font-size: 18px;
}

.apiInfo-other {
  color: #0a76a4;
  background-color: #DDECFB;
  font-size: 16px;
}
</style>

