import request from '@/plugin/axios'

export function Page (data) {
  return request({
    url: '/admin/${className}/page',
    method: 'POST',
    data
  })
}

export function Add (data) {
  return request({
    url: '/admin/${className}/save',
    method: 'POST',
    data
  })
}

export function Update (data) {
  return request({
    url: '/admin/${className}/update',
    method: 'POST',
    data
  })
}

export function Delete (data) {
  return request({
    url: '/admin/${className}/delete',
    method: 'POST',
    data
  })
}

