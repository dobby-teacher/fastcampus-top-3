import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '@/pages/LoginPage.vue';
import CoursesPage from '@/pages/CoursesPage.vue';
import PurchasePage from '@/pages/PurchasePage.vue';
import PurchaseHistoryPage from '@/pages/PurchaseHistoryPage.vue';
import CourseDetailPage from "@/pages/CourseDetailPage.vue";

const routes = [
    { path: '/', component: LoginPage },
    { path: '/courses', component: CoursesPage },
    { path: '/purchase/:id', component: PurchasePage },
    { path: '/history', component: PurchaseHistoryPage },
    { path: '/course/:id', component: CourseDetailPage },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;