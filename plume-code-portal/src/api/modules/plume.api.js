export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  test_connection (data = {}) {
    return request({
      url: '/plume/test',
      method: 'post',
      data
    })
  },
  listTables (data = {}) {
    return request({
      url: '/plume/listTables',
      method: 'post',
      data
    })
  },
  generate (data = {}) {
    return request({
      url: '/plume/generate',
      method: 'post',
      data
    })
  }
})
