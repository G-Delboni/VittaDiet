import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

// Ordem: tokens -> reset -> tipografia -> componentes compartilhados.
// O CSS específico de cada página é importado dentro da própria view.
import "@/css/base/tokens.css";
import "@/css/base/reset.css";
import "@/css/base/typography.css";
import "@/css/components/button.css";
import "@/css/components/navbar.css";
import "@/css/components/illustration.css";

// Marca que o JS está ativo: as animações de rolagem só valem com JS (ver landing.css).
// Fica aqui, e não em <script> inline no HTML, para o CSP poder bloquear scripts inline.
document.documentElement.classList.add("js");

createApp(App).use(router).mount("#app");
