import { createRouter, createWebHistory } from "vue-router";
import { restoreSession, isAuthenticated } from "@/core/auth.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", name: "landing", component: () => import("@/views/LandingView.vue") },
    { path: "/login", name: "login", component: () => import("@/views/LoginView.vue") },
    { path: "/app", name: "app", component: () => import("@/views/AppView.vue"), meta: { requiresAuth: true } },
    { path: "/:pathMatch(.*)*", redirect: { name: "landing" } },
  ],
  scrollBehavior: (to) => (to.hash ? { el: to.hash, behavior: "smooth" } : { top: 0 }),
});

// Depois de um F5 o access token (em memória) some: uma única tentativa de
// recuperá-lo pelo cookie HttpOnly, só quando a rota depende de sessão.
let sessionChecked = false;

router.beforeEach(async (to) => {
  const needsSession = to.meta.requiresAuth || to.name === "login";
  if (needsSession && !sessionChecked) {
    await restoreSession();     // nunca lança: devolve true/false
    sessionChecked = true;
  }

  const logged = isAuthenticated();
  if (to.meta.requiresAuth && !logged) return { name: "login" };
  if (to.name === "login" && logged) return { name: "app" };
});

export default router
