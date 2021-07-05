import request from '@/plugin/axios'

export function Page (data) {
  return request({
    url: '/admin/smartUser/page',
    method: 'POST',
    data
  })
}

export function Add (data) {
  return request({
    url: '/admin/smartUser/save',
    method: 'POST',
    data
  })
}

export function Update (data) {
  return request({
    url: '/admin/smartUser/update',
    method: 'POST',
    data
  })
}

export function Delete (data) {
  return request({
    url: '/admin/smartUser/delete',
    method: 'POST',
    data
  })
}

