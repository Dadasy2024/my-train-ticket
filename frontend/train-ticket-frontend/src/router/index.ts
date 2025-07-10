import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TicketUploadView from '../views/TicketUploadView.vue'

const routes = [
  { path: '/', name: 'home', component: HomeView }, // 访问 / 显示 HomeView
  { path: '/upload', name: 'ticket-upload', component: TicketUploadView } // 访问 /upload 显示 TicketUploadView
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
