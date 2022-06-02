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
                 :disabled="createDisable">
        添加
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-search"
                 @click="page">
        查询
      </el-button>
    </div>

    <el-table border v-loading="this.loading" style="width: 100%;" :max-height="tableMaxHeight" :data="tableData"
              row-key="id" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection"/>
      <el-table-column align="center" type="index" label="序号"/>
      <el-table-column align="center" prop="matches" label="匹配路径">
        <template slot-scope="{row,$index}">
          <el-select s size="small" :disabled="row.editDisable" v-model="row.matches" multiple>
            <el-option v-for="item in urls" :key="item" :label="item" :value="item"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="noMatches" label="排除路径">
        <template slot-scope="{row,$index}">
          <el-select size="small" :disabled="row.editDisable" v-model="row.noMatches" multiple>
            <el-option v-for="item in urls" :key="item" :label="item" :value="item"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="methods" label="HTTP方法">
        <template slot-scope="{row,$index}">
          <el-select size="small" :disabled="row.editDisable" v-model="row.methods" multiple>
            <el-option v-for="item in httpMethods" :key="item" :label="item" :value="item"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="requiredAl" label="需要任一权限">
        <template slot-scope="{row,$index}">
          <el-select size="small" :disabled="row.editDisable" v-model="row.requiredAny" multiple>
            <el-option v-for="item in permissionOptions" :key="item.id" :label="item.permissionKey" :value="item.id"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="requiredAny" label="需要所有权限">
        <template slot-scope="{row,$index}">
          <el-select size="small" :disabled="row.editDisable" v-model="row.requiredAll" multiple>
            <el-option v-for="item in permissionOptions" :key="item.id" :label="item.permissionKey" :value="item.id"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="stopChain" label="通过则结束匹配链">
        <template slot-scope="{row,$index}">
          <el-switch size="small" :disabled="row.editDisable" v-model="row.stopChain"/>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sort" label="排序">
        <template slot-scope="{row,$index}">
          <el-input-number size="small" :disabled="row.editDisable" v-model="row.sort" :min="1" :max="100"/>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="230">
        <template slot-scope="{row,$index}">
          <el-button v-if="row.editDisable" type="primary" size="small" @click="handleUpdate(row)">编辑</el-button>
          <el-button v-else type="success" size="small" @click="doUpdateOrCreate(row,$index)">
            保存
          </el-button>
          <el-button :disabled="row.editDisable" size="small" @click="row.editDisable=true">取消</el-button>
          <el-popconfirm style="margin-left: 5px" title="确定删除吗" @onConfirm="doDelete(row.id,$index)">
            <template #reference>
              <el-button type="danger" size="small">
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
import {createSysParam, deleteSysParam, getAllUrl, permissionSelect, sysParamPage, updateSysParam} from "@/api/sysBase";
import {apiMatchCreate, apiMatchDelete, apiMatchPage, apiMatchUpdate} from "@/api/permission";

export default {
  created() {
    this.getPermissionOptions();
    this.getUrlOptions();
    this.page();
  },
  data() {
    return {
      tableMaxHeight,
      tableData: [],
      dialogStatus,
      loading,
      operationMap,
      selected: Object.assign({}, rowSelected),
      pagination: Object.assign({}, pagination),
      pageReq: Object.assign({}, pageReq),
      rowSelected,

      dataOperating: {},// todo 表单数据
      inputVisible: false,
      noMatchInputVisible: false,
      inputValue: '',
      noMatchInputValue: '',
      permissionOptions: [],
      httpMethods: ['get', 'post', 'put', 'delete'],
      createDisable: false,
      toCreateIndex: null,
      urls: []
    }
  },
  methods: {
    page() {
      this.loading = true;
      this.createDisable = false;
      return new Promise(() => {
        apiMatchPage(this.pageReq).then(res => {
          this.tableData = res.data.map(item => {
            return Object.assign({editDisable: true}, item);
          });
          this.pagination.current = res.pageCurrent
          this.pagination.total = res.total
        }).finally(() => {
          this.loading = false;
        });
      });
    },
    handleCreate() {
      this.toCreateIndex = this.tableData.length;
      this.tableData.push({
        matches: [],
        methods: [],
        noMatches: [],
        requiredAny: [],
        requiredAll: [],
        stopChain: true,
        sort: 100,
        editDisable: false,
      });
      this.createDisable = true;
    },
    handleUpdate(row) {
      row.editDisable = false;
    },
    doUpdateOrCreate(row, index) {
      row.editDisable = true;
      if (index === this.toCreateIndex) {
        this.createData(row);
        this.toCreateIndex = null;
        this.createDisable = false;
      } else {
        this.updateData(row);
      }
    },
    createData(row) {
      return new Promise(() => {
        apiMatchCreate(row).then(res => {
          row.id = res.data;
        });
      });
    },
    updateData(row) {
      return new Promise(() => {
        apiMatchUpdate(row);
      });
    },
    dialogClose() {
      if (this.dialogStatus === 'update') {
        this.resetDataOperating();
      }
    },
    doDelete(id, index) {
      if (index === this.toCreateIndex) {
        this.createDisable = false;
      }
      this.tableData.splice(index, 1);
      if (!id) {
        return;
      }
      apiMatchDelete([id]);
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
    getPermissionOptions() {
      return new Promise(() => {
        permissionSelect().then(res => {
          let options = res.data.filter(p => {
            return p.resourceType === '操作';
          });
          options.sort((i1, i2) => {
            return i1.permissionKey.localeCompare(i2.permissionKey);
          });
          this.permissionOptions = options;
        });
      });
    },
    getUrlOptions() {
      return new Promise(() => {
        getAllUrl().then(res => {
          this.urls = res.data;
        });
      });
    },
    handlePageChange,
    handleSizeChange,
    checkPermission,
    prevPageIfPageLastOne,
    handleDeleteMulti,
    handleSelectionChange
  }
  ,
  filters: {}
}
</script>

<style lang="scss" scoped>
.tag-margin {
  margin: 0 5px 5px 0;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 28px;
  line-height: 26px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>

