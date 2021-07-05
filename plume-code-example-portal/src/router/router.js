import smartUser from '@/pages/smartUser'
import util from '@/libs/util.ice'

const router = [
    {
        path: '/smartUser',
        name: 'smartUser',
        component: smartUser,
        meta: {
            cache: true,
            requiresAuth: true,
            title: ' 用户',
        }
    }
]

const routerConfigMenuOut = [
]

export default util.recursiveRouterConfig([
    ...router,
    ...routerConfigMenuOut,
])

export const frameInRoutes = util.recursiveRouterConfig(router).map(e => {
    const route = e.children ? e.children[0] : e
    return {
        path: e.path,
        name: route.name,
        hidden: route.hidden,
        meta: route.meta,
    }
})
