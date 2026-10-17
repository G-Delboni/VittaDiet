<script setup>
import { onMounted } from "vue";
import { initNavbar } from "@/components/navbar.js";
import "@/css/components/card.css";
import "@/css/pages/landing.css";

onMounted(() => {
  initNavbar();
  document.querySelectorAll("[data-year]").forEach((el) => { el.textContent = new Date().getFullYear(); });
  revealOnScroll();
});

// Faz as seções aparecerem suavemente ao entrar na tela (IntersectionObserver).
function revealOnScroll() {
  const items = document.querySelectorAll(".reveal");
  if (!("IntersectionObserver" in window)) {
    items.forEach((item) => item.classList.add("is-visible"));
    return;
  }
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add("is-visible");
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.15 });
  items.forEach((item) => observer.observe(item));
}
</script>

<template>
  <main class="landing" id="conteudo">

    <!-- Faixa 1: navbar + hero -->
    <div class="band">
      <div class="band__photo">
        <figure class="photo-tile photo-tile--mint">
          <img src="@/assets/images/salad_image.webp" alt="Foto: salada colorida">
          <figcaption class="chip">Diversidade</figcaption>
        </figure>
      </div>

      <div class="band__content">

        <header class="navbar" data-navbar>
          <RouterLink class="navbar__brand" to="/">
            <img src="@/assets/icons/leaf.svg" alt="" width="32" height="32">
            VittaDiet
          </RouterLink>
          <button class="navbar__toggle" type="button" aria-expanded="false" aria-controls="menu-principal" data-navbar-toggle>
            <span class="sr-only">Abrir menu</span>
            <span class="navbar__bars" aria-hidden="true"></span>
          </button>
          <nav class="navbar__menu" id="menu-principal" aria-label="Principal">
            <a class="navbar__link" href="#sobre">Sobre</a>
            <a class="navbar__link" href="#visao">Visualização</a>
            <RouterLink class="btn btn--primary btn--sm" :to="{ name: 'login' }">Entrar</RouterLink>
          </nav>
        </header>

        <section class="hero" id="inicio">
          <div class="hero__text">
            <h1 class="hero__title">Sua mesa, do seu jeito. Sua saúde, no seu ritmo.</h1>
            <p class="hero__lead">Descubra seu gasto energético, monte refeições com calorias calculadas e acompanhe sua rotina alimentar em um espaço feito para ser acolhedor.</p>
            <div class="hero__actions">
              <RouterLink class="btn btn--primary" :to="{ name: 'login', query: { modo: 'cadastro' } }">Começar agora</RouterLink>
              <a class="btn btn--soft" href="#sobre">Conhecer o VittaDiet</a>
            </div>
          </div>
          <figure class="hero__visual">
            <div class="hero__blob">
              <img src="@/assets/images/beautiful_meal_image.webp" alt="Foto: refeição completa" >
            </div>
            <div class="hero__dot hero__dot--big"></div>
            <div class="hero__dot hero__dot--small"></div>
            
          </figure>
        </section>

      </div>
    </div>

    <!-- Faixa 2: Sobre -->
    <div class="band">
      <div class="band__photo">
        <figure class="photo-tile photo-tile--leaf">
          <img src="@/assets/images/woman_eating_image.webp" alt="Foto: mulher sorrindo enquanto come">
          <figcaption class="chip">Equilíbrio</figcaption>
        </figure>
      </div>

      <div class="band__content">

        <section class="about reveal" id="sobre" aria-labelledby="sobre-titulo">
          <div class="about__intro">
            <div class="about__icon">
              <svg class="illustration" viewBox="0 0 120 120" aria-hidden="true"><use href="#ill-notebook"/></svg>
            </div>
            <p class="eyebrow">Sobre</p>
            <h2 class="section-title" id="sobre-titulo">Nutrição que cabe na sua rotina</h2>
            <p class="lead">O VittaDiet reúne em um só lugar o que você precisa para entender sua alimentação: seu gasto de energia, uma consulta de alimentos e o registro das suas refeições. Sem complicação e sem julgamento.</p>
          </div>

          <ol class="steps">
            <li class="step-card">
              <span class="step-card__number" aria-hidden="true">1</span>
              <div class="step-card__body">
                <h3 class="step-card__title">Cadastre seus dados</h3>
                <p class="step-card__text">Peso, altura, idade e sexo para calcular o seu gasto energético basal.</p>
              </div>
            </li>
            <li class="step-card">
              <span class="step-card__number" aria-hidden="true">2</span>
              <div class="step-card__body">
                <h3 class="step-card__title">Monte suas refeições</h3>
                <p class="step-card__text">Escolha alimentos e quantidades. Calorias, gordura, carboidrato e proteína aparecem por item e no total.</p>
              </div>
            </li>
            <li class="step-card">
              <span class="step-card__number" aria-hidden="true">3</span>
              <div class="step-card__body">
                <h3 class="step-card__title">Acompanhe o histórico</h3>
                <p class="step-card__text">Revise o que você comeu e ajuste a rotina com mais clareza.</p>
              </div>
            </li>
          </ol>
        </section>

      </div>
    </div>

    <!-- Faixa 3: Visualização simplificada + CTA + rodapé -->
    <div class="band">
      <div class="band__photo">
        <figure class="photo-tile photo-tile--soft">
          <img src="@/assets/images/fresh_fruits_image.webp" alt="Foto: frutas frescas">
          <figcaption class="chip">Saúde</figcaption>
        </figure>
      </div>

      <div class="band__content">

        <section class="progress-section reveal" id="visao" aria-labelledby="visao-titulo">
          <div class="progress-section__text">
            <p class="eyebrow">Visualização simplificada</p>
            <h2 class="section-title" id="visao-titulo">Acompanhar sua evolução deve ser um prazer</h2>
            <p class="lead">Em vez de tabelas e números frios, o VittaDiet vai mostrar o seu progresso de um jeito agradável, confortável e motivador, pensado para você querer voltar amanhã, sem pressão.</p>
            <ul class="check-list">
              <li class="check-list__item">
                <span class="check-list__icon"><svg viewBox="0 0 24 24" aria-hidden="true"><use href="#icon-check"/></svg></span>
                Resumos claros do seu dia e da sua semana
              </li>
              <li class="check-list__item">
                <span class="check-list__icon"><svg viewBox="0 0 24 24" aria-hidden="true"><use href="#icon-check"/></svg></span>
                Metas opcionais: defina se quiser, ignore se preferir
              </li>
              <li class="check-list__item">
                <span class="check-list__icon"><svg viewBox="0 0 24 24" aria-hidden="true"><use href="#icon-check"/></svg></span>
                Um progresso que você sente, e não apenas lê
              </li>
            </ul>
          </div>

          <!-- Espaço reservado: substituir pela visualização de progresso quando o design for decidido -->
          <figure class="progress-placeholder">
            <div class="progress-placeholder__circle progress-placeholder__circle--large"></div>
            <div class="progress-placeholder__circle progress-placeholder__circle--medium"></div>
            <div class="progress-placeholder__circle progress-placeholder__circle--small"></div>
            <svg class="illustration" viewBox="0 0 120 120" aria-hidden="true"><use href="#ill-progress"/></svg>
            <figcaption class="chip">Espaço reservado: visualização de progresso (em definição)</figcaption>
          </figure>
        </section>

        <section class="cta reveal" aria-labelledby="cta-titulo">
          <div class="cta__body">
            <div class="cta__icon">
              <svg class="illustration" viewBox="0 0 120 120" aria-hidden="true"><use href="#ill-steam-bowl"/></svg>
            </div>
            <div>
              <h2 class="cta__title" id="cta-titulo">Pronto para começar?</h2>
              <p class="cta__text">Crie sua conta em poucos minutos ou entre para continuar de onde parou.</p>
            </div>
          </div>
          <div class="cta__actions">
            <RouterLink class="btn btn--primary" :to="{ name: 'login', query: { modo: 'cadastro' } }">Criar conta</RouterLink>
            <RouterLink class="btn btn--outline" :to="{ name: 'login' }">Entrar</RouterLink>
          </div>
        </section>

        <footer class="site-footer">
          <span>VittaDiet</span>
          <span>Projeto de portfólio · <span data-year>2026</span></span>
        </footer>

      </div>
    </div>

  </main>
</template>
