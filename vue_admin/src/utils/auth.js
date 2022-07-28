import Cookies from 'js-cookie'

const TokenKey = 'adtoken'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
