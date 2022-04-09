<template>

  <div>
    <el-button>fff</el-button>
  </div>
</template>


<script>

export default {
  created() {
  },
  data() {
    return {}
  },
  methods: {
    func(item) {
      return item.name;
    },
    fid(item) {
      return item.id
    },
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

span {
  display: block;
  font-size: 16px;
  margin-top: 10px;
}

.disabled {
  pointer-events: none;
}
</style>
