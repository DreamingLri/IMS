import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/chart',
    name: 'chart',
    component: () => import('@/views/echarts/credit-chart.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/Login.vue')
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('@/views/home/index.vue'),
    redirect: '/index/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/components/DashBoard.vue')
      },
      {
        path: 'student-info',
        name: 'student-info',
        component: () => import('@/views/components/StudentInfoPage.vue')
      },
      {
        path: 'course',
        name: 'course',
        component: () => import('@/views/components/CoursePage.vue')
      },
      {
        path: 'teacher-info',
        name: 'teacher-info',
        component: () => import('@/views/components/TeacherInfoPage.vue')
      },
      {
        path: 'exam',
        name: 'exam',
        component: () => import('@/views/components/ExamPage.vue')
      },
      {
        path: 'course-time',
        name: 'course-time',
        component: () => import('@/views/components/CourseTimePage.vue')
      },
      {
        path: 'select-course',
        name: 'select-course',
        component: () => import('@/views/components/SelectCoursePage.vue')
      },
      {
        path: 'class-table',
        name: 'class-table',
        component: () => import('@/views/components/ClassTable.vue')
      },
      {
        path: 'exam-student',
        name: 'exam-student',
        component:() => import ('@/views/components/ExamStudentPage.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
