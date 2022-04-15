<template>
  <div class="app-container">
    <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/"
      :show-file-list="false">
      <img v-if="imageUrl" :src="imageUrl" class="avatar"/>
      <div v-else class="avatar-uploader-icon">
        <i class="el-icon-plus"/>
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "Empty",
  data() {
    return {
      imageUrl: null
    }
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 60px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: .2s;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
  line-height: 120px;
}
</style>
