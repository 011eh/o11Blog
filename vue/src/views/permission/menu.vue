<template>
  <div class="app-container">
    菜单管理
  </div>
</template>

<script>

const defaultRole = {
  key: '',
  name: '',
  description: '',
  routes: []
}

export default {
  data() {
    return {}
  },
  computed: {},
  created() {
  },
  methods: {}
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
</style>
