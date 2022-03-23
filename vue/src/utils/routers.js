const routerMap = {
  page: () => import('@/views/permission/admin'),
  role: () => import('@/views/permission/menu'),
  directive: () => import('@/views/permission/role'),
  layout: () => import('@/layout')
}

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
