import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Routes from '../views/Routes.vue'
import RouteDetail from '../views/RouteDetail.vue'
import Orders from '../views/Orders.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/routes',
    name: 'Routes',
    component: Routes
  },
  {
    path: '/routes/:id',
    name: 'RouteDetail',
    component: RouteDetail
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/orders' && !localStorage.getItem('isLoggedIn')) {
    next('/login')
  } else {
    next()
  }
})

export default router
