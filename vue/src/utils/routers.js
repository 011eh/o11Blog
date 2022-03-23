const routerMap = {
  "role": () => import('@/views/permission/role'),
  "page": () => import('@/views/permission/page'),
  "directive": () => import('@/views/permission/directive'),
  "layout": () => import('@/layout')
}

export function setComponent(router) {
  debugger
  if (router.component) {
    console.log(router.component)
    debugger
    router.component = routerMap[router.component]
    console.log(router.component)
  }
  if (router.children) {
    for (const child of router.children) {
      setComponent(child)
    }
  }
}
