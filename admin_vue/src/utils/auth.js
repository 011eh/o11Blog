import Cookies from 'js-cookie'

const TokenKey = 'satoken'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
