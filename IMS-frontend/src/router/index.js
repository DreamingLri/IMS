import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/Login.vue')
  },{
    path: '/index',
    name: 'index',
    component: () => import('@/views/home/index.vue'),
    children: [
      {
        path: '',
        name: 'dashboard',
        component: () => import('@/views/components/DashBoard.vue')
      },
      {
        path: 'user-info',
        name: 'user-info',
        component: () => import('@/views/components/UserInfoPage.vue')
      },
      {
        path: 'course',
        name: 'course',
        component: () => import('@/views/components/CoursePage.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
