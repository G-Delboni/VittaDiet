// ============================================================================
// landing.js: comportamentos da landing page.
// ============================================================================

import { initNavbar } from "/js/components/navbar.js";

initNavbar();
setCurrentYear();
revealOnScroll();

function setCurrentYear() {
  document.querySelectorAll("[data-year]").forEach((element) => {
    element.textContent = new Date().getFullYear();
  });
}

// Faz as seções aparecerem suavemente ao entrar na tela (IntersectionObserver).
function revealOnScroll() {
  const items = document.querySelectorAll(".reveal");

  if (!("IntersectionObserver" in window)) {
    items.forEach((item) => item.classList.add("is-visible"));
    return;
  }

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("is-visible");
          observer.unobserve(entry.target); // anima só uma vez
        }
      });
    },
    { threshold: 0.15 }
  );

  items.forEach((item) => observer.observe(item));
}
