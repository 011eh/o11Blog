<template>
  <div class="app-container">
    <el-popover placement="right" :width="400" trigger="click">
      <template #reference>
        <el-button style="margin-right: 16px">Click to activate</el-button>
      </template>
      <el-table :data="[]">
        <el-table-column width="150" property="date" label="date" />
        <el-table-column width="100" property="name" label="name" />
        <el-table-column width="300" property="address" label="address" />
      </el-table>
    </el-popover>
  </div>
</template>

<script>

export default {
  data() {
    return {}
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.app-container {
  ::v-deep .permission-alert {
    width: 320px;
    margin-top: 15px;
    background-color: #f0f9eb;
    color: #67c23a;
    padding: 8px 16px;
    border-radius: 4px;
    display: inline-block;
  }

  ::v-deep .permission-sourceCode {
    margin-left: 15px;
  }

  ::v-deep .permission-tag {
    background-color: #ecf5ff;
  }
}
</style>

