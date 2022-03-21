export function setComponent(router) {
  debugger
  if (router.component) {
    console.log(router.component)
    router.component = import('@/' + router.component)
    console.log(router.component)
  }
  if (router.children) {
    for (const child of router.children) {
      setComponent(child)
    }
  }
}
