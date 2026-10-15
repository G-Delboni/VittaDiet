import { createRouter, createWebHistory } from 'vue-router'
import { restoreSession, isAuthenticated } from "@/core/auth.js"; // Importamos o isAuthenticated aqui

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/pages/login.js')
    },
    {
      path: '/app',
      name: 'app',
      component: () => import('@/views/pages/app.js'),
      meta: { requiresAuth: true }
    }
  ],
})

// Controla se a tentativa de refresh pelo cookie já foi feita ao carregar o app
let isSessionChecked = false;

router.beforeEach(async (to) => {
  // Se a página acabou de ser recarregada (F5), tenta restaurar usando o Cookie do Spring Boot
  if (!isSessionChecked) {
    try {
      await restoreSession();
    } catch (error) {
      console.warn("Sessão expirada ou usuário não autenticado");
    } finally {
      isSessionChecked = true;
    }
  }

  const logged = isAuthenticated();

  // Redirecionamentos de segurança baseados no estado de 'logged'
  if (to.meta.requiresAuth && !logged) {
    return { name: "login" };
  }

  if (to.name === "login" && logged) {
    return { name: "app" };
  }
});

export default router
