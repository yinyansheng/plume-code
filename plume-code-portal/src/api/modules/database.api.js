export default ({service, request, serviceForMock, requestForMock, mock, faker, tools}) => ({
  testConnection(data = {}) {
    return request({
      url: '/database/testConnection',
      method: 'post',
      data
    })
  },
  listTableName(data = {}) {
    return request({
      url: '/database/listTableName',
      method: 'post',
      data
    })
  }
})
