<template>

  <div>
    <el-button>fff</el-button>
  </div>
</template>


<script>
import svgIcons from "@/icons/svg-icons";
import elementIcons from "@/icons/element-icons";

export default {
  mounted() {
    this.$refs.tree.setCheckedKeys(['1497825987499515911', '1497825987499515911']);
  },
  created() {
  },
  data() {
    return {
      treeProps: {label: 'name', id: 'id'},
      elementIcons, svgIcons,
      data: [
        {
          id: '1497825987499515915',
          name: "资源管理",
          "children": [
            {
              id: '1497825987499515911',
              name: "权限管理"
            },
            {
              id: '1497825987499515907',
              name: "角色管理"
            },
            {
              id: '1497811057370992642',
              name: "用户管理"
            }
          ]
        },
        {
          id: '1512344969314701313',
          name: "空页面",
          "children": [
            {
              id: '1512345084280573954',
              name: "空页面列表"
            }
          ]
        },
        {
          id: '1497825987499515911',
          name: "权限管理",
          "children": [
            {
              id: '1497825987499515912',
              name: "权限添加"
            },
            {
              id: '1497825987499515913',
              name: "权限更新"
            },
            {
              id: '1497825987499515914',
              name: "权限删除"
            },
            {
              id: '1512328763396460545',
              name: "权限列表"
            }
          ]
        },
        {
          id: '1497825987499515907',
          name: "角色管理",
          "children": [
            {
              id: '1497825987499515908',
              name: "角色添加"
            },
            {
              id: '1497825987499515909',
              name: "角色更新"
            },
            {
              id: '1497825987499515910',
              name: "角色删除"
            }
          ]
        },
        {
          id: '1497811057370992642',
          name: "用户管理",
          "children": [
            {
              id: '1497811074932543489',
              name: "用户添加"
            },
            {
              id: '1497811107790721025',
              name: "用户更新"
            },
            {
              id: '1497825987499515906',
              name: "用户删除"
            }
          ]
        }],
    }
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
