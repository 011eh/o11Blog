const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys()

const re = /\.\/(.*)\.svg/

const svgIcons = requireAll(req).map(i => {
  return i.match(re)[1]
})

export default svgIcons

export function generateIconCode(symbol) {
  return `<svg-icon icon-class="${symbol}" />`
}

export function generateElementIconCode(symbol) {
  return `<i class="el-icon-${symbol}" />`
}
