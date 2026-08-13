import { createRouter, createWebHistory } from 'vue-router'
import WorkHoursView from '../views/WorkHoursView.vue'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/work-hours' },
    { path: '/work-hours', name: 'work-hours', component: WorkHoursView },
  ],
})
