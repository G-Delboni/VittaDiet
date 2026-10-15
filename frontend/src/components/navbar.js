// ============================================================================
// navbar.js: comportamento do menu (abrir/fechar no celular).
// Funciona em qualquer página que tenha <header data-navbar> com um botão
// [data-navbar-toggle] e um .navbar__menu.
// ============================================================================

export function initNavbar(root = document) {
  root.querySelectorAll("[data-navbar]").forEach((navbar) => {
    const toggle = navbar.querySelector("[data-navbar-toggle]");
    const menu = navbar.querySelector(".navbar__menu");
    if (!toggle || !menu) return;

    const setOpen = (open) => {
      menu.classList.toggle("is-open", open);
      toggle.setAttribute("aria-expanded", String(open));
    };

    toggle.addEventListener("click", () => {
      setOpen(toggle.getAttribute("aria-expanded") !== "true");
    });

    // Fecha o menu ao escolher um link (útil para âncoras da mesma página)
    menu.addEventListener("click", (event) => {
      if (event.target.closest("a")) setOpen(false);
    });

    navbar.addEventListener("keydown", (event) => {
      if (event.key === "Escape") {
        setOpen(false);
        toggle.focus();
      }
    });
  });
}
