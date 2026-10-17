<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import { logout, getCurrentUser } from "@/core/auth.js";
import { initNavbar } from "@/components/navbar.js";
import "@/css/pages/app.css";

// O guard de rota (router/index.js) já barrou quem não tem sessão.
const router = useRouter();
const userName = getCurrentUser()?.name ?? "bem-vindo";

onMounted(() => initNavbar());

async function onLogout() {
  await logout();
  router.push({ name: "landing" });
}
</script>

<template>
  <div class="app-shell">

    <header class="navbar" data-navbar>
      <RouterLink class="navbar__brand" to="/app">
        <img src="/assets/icons/leaf.svg" alt="" width="32" height="32">
        VittaDiet
      </RouterLink>
      <button class="navbar__toggle" type="button" aria-expanded="false" aria-controls="menu-app" data-navbar-toggle>
        <span class="sr-only">Abrir menu</span>
        <span class="navbar__bars" aria-hidden="true"></span>
      </button>
      <nav class="navbar__menu" id="menu-app" aria-label="Conta">
        <span class="navbar__user">Olá, <span>{{ userName }}</span></span>
        <button class="btn btn--outline btn--sm" type="button" @click="onLogout">Sair</button>
      </nav>
    </header>

    <main class="app" id="conteudo">
      <section class="app__welcome">
        <h1 class="app__title">Olá, <span>{{ userName }}</span></h1>
        <p class="lead">Aqui vai ficar o seu painel: resumos, metas e progresso.</p>
      </section>

      <div class="app__grid">
        <article class="panel">
          <h2 class="panel__title">Resumo de hoje</h2>
          <p class="panel__empty">Seus resumos aparecerão aqui.</p>
        </article>
        <article class="panel">
          <h2 class="panel__title">Metas (opcionais)</h2>
          <p class="panel__empty">Defina uma meta quando quiser.</p>
        </article>
        <article class="panel panel--wide">
          <h2 class="panel__title">Seu progresso</h2>
          <p class="panel__empty">Espaço reservado para a visualização de progresso.</p>
        </article>
        <article class="panel panel--wide">
          <h2 class="panel__title">Refeições recentes</h2>
          <p class="panel__empty">Suas refeições registradas aparecerão aqui.</p>
        </article>
      </div>
    </main>

  </div>
</template>
