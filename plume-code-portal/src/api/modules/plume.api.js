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
  listTable (data = {}) {
    return request({
      url: '/plume/listTable',
      method: 'post',
      data
    })
  }
})
