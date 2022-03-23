import {constantRoutes} from '@/router'
import {menu} from "@/api/user";
import {setComponent} from "@/utils/routers";

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = {...route}
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({commit}) {
    return new Promise(resolve => {
      let accessedRoutes
      menu().then(res => {
        const routers = res.data
        debugger
        for (const router of routers) {
          setComponent(router)
        }
        accessedRoutes = routers

        //accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
        // for (const r of accessedRoutes) {
        //   setComponent(r)
        // }
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)

      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
