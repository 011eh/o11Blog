<template>
  <div class="app-container">
    角色管理
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

