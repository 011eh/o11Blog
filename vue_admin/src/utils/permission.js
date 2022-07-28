import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/role.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const roles = store.getters && store.getters.roles
    const permissionRequired = value

    if (permissionRequired.length === 1 && permissionRequired[0] === "") {
      return true;
    }

    return roles.some(role => {
      return permissionRequired.includes(role)
    });
  } else {
    console.error(`无此权限`)
    return false
  }
};
