const routerMap = {
  layout: () => import('@/layout'),

  permission: () => import('@/views/permission/permission'),
  role: () => import('@/views/permission/role'),
  user: () => import('@/views/permission/user'),
  empty: () => import('@/views/empty'),
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

export {routerMap}
