import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../views/Login.vue'
import Register from '../views/Register.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/Admin.vue')
  },
  {
    path: '/admin/teacher',
    name: 'adminTeacher',
    component: () => import('../views/AdminTeacher.vue')
  },
  {
    path: '/teacher',
    name: 'teacher',
    component: () => import('../views/Teacher.vue')
  },
  {
    path: '/teacher/course/:courseId',
    name: 'teacherCourse',
    component: () => import('../views/TeacherCourse.vue')
  },
    {
      path: '/teacher/course/stu/:studentId/:courseId',
      name: 'teacherStudent',
      component: () => import('../views/TeacherStudent.vue')
    },{
      path: '/teacher/topic/:topicId/:courseId',
      name: 'teacherTopic',
      component: () => import('../views/TeacherTopic.vue')
    },
  {
    path: '/teacher/paper',
    name: 'teacherPaper',
    component: () => import('../views/TeacherPaper.vue')
  },
  {
    path: '/teacher/paper/message/:paperId',
    name: 'teacherMessage',
    component: () => import('../views/TeacherMessage.vue')
  },
    {
      path: '/student',
      name: 'student',
      component: () => import('../views/Student.vue')
    },
    {
      path: '/student/topic',
      name: 'studentTopic',
      component: () => import('../views/StudentTopic.vue')
    },
    {
      path: '/student/course/topic/:courseId',
      name: 'studentCourse',
      component: () => import('../views/StudentCourse.vue')
    },
  {
    path: '/student/paper',
    name: 'studentPaper',
    component: () => import('../views/StudentPaper.vue')
  },
  {
    path: '/student/paper/message/:paperId',
    name: 'studentMessage',
    component: () => import('../views/StudentMessage.vue')
  },

  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
