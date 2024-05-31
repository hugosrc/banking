import { createRouter, createWebHistory } from 'vue-router'
import AuthView from '@/views/AuthView.vue'
import DashboardView from '@/views/DashboardView.vue' 

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/auth',
      name: 'auth',
      component: AuthView
    },
    {
      path: '/',
      name: 'dashboard',
      component: DashboardView,
      beforeEnter: (to, from, next) => {
        if (!(to.query.account && to.query.digit)) {
          next('/auth')
        }
  
        next()
      }
    }
  ]
})

export default router
