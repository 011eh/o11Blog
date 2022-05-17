const routerMap = {
  layout: () => import('@/layout'),

  permission: () => import('@/views/permission/permission'),
  role: () => import('@/views/permission/role'),
  user: () => import('@/views/permission/user'),
  empty: () => import('@/views/empty'),
  sysLog: () => import('@/views/log/sysLog'),
}
const routerSelect = [
  {name: '菜单布局', component: 'layout'},

  {name: '权限管理', component: 'permission'},
  {name: '角色管理', component: 'role'},
  {name: '用户管理', component: 'user'},
  {name: '操作日志', component: 'sysLog'},

  {name: '空页面', component: 'empty'}
]

export function setComponent(router) {
  if (router.component) {
    router.component = routerMap[router.component]
  }
  if (router.children) {
    for (const child of router.children) {
      setComponent(child)
    }
  }
}

export {routerSelect}
