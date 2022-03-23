<template>
  <div class="app-container">
    用户管理
  </div>
</template>

<script>

import {getInfo} from "@/api/user";

export default {
  created() {
    getInfo()
  }
}
</script>
