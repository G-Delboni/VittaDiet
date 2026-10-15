// ============================================================================
// app.js: área do usuário (User Page).
// Começa com o "guard": sem sessão, redireciona para o login antes de mostrar
// qualquer coisa. O conteúdo fica [hidden] até a checagem passar.
// ============================================================================

import { requireAuth, logout, getCurrentUser } from "/js/core/auth.js";
import { initNavbar } from "/js/components/navbar.js";

if (await requireAuth()) {
  initNavbar();

  const user = getCurrentUser();
  document.querySelectorAll("[data-user-name]").forEach((element) => {
    element.textContent = user?.name ?? "bem-vindo";
  });

  document.querySelector("[data-app]").hidden = false;

  document.querySelector("[data-logout]").addEventListener("click", async () => {
    await logout();
    window.location.assign("/");
  });
}
