import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import {useAuthStore} from "@/stores/authSore.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/', name: 'home', component: HomeView},
    {path: '/login', name: 'login', component: LoginView},
    {path: '/about', name: 'about', component: () => import('../views/AboutView.vue')}, // lazy load
    {path: '/dashboard', name: 'dashboard', component: () => import('../views/DashboardView.vue'),  meta: { requiresAuth: true }},
    {path: '/admin', name: 'admin', component: () => import('../views/AdminView.vue'),  meta: { requiresAuth: true }},

  ],
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const isAuthenticated = authStore.isAuthenticated // or from Pinia store
  if (to.meta.requiresAuth && !sessionStorage.getItem("accessToken")) {
    next('/login')
  } else {
    next()
  }
})

export default router
