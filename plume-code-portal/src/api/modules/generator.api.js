export default ({service, request, serviceForMock, requestForMock, mock, faker, tools}) => ({
  generate(data = {}) {
    return request({
      url: '/generator/generate',
      timeout: 10000,
      method: 'post',
      data
    })
  },
  tree(params = {}) {
    return request({
      url: '/generator/tree',
      method: 'get',
      params
    })
  },
  content(params = {}) {
    return request({
      url: '/generator/content',
      method: 'get',
      params
    })
  }
})
