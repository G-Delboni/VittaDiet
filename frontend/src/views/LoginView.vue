<script setup>
import { reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { login, register } from "@/core/auth.js";
import "@/css/components/form.css";
import "@/css/pages/login.css";

const route = useRoute();
const router = useRouter();

// A landing leva para /login?modo=cadastro quando o botão é "Criar conta"
const tab = ref(route.query.modo === "cadastro" ? "cadastro" : "login");
const tabs = ["login", "cadastro"];

function onTabKeydown(event) {
  if (event.key !== "ArrowRight" && event.key !== "ArrowLeft") return;
  const step = event.key === "ArrowRight" ? 1 : -1;
  tab.value = tabs[(tabs.indexOf(tab.value) + step + tabs.length) % tabs.length];
  document.getElementById(`tab-${tab.value}`)?.focus();
}

// ---------- Validação (Constraint Validation API do navegador) ----------

const errors = reactive({});                 // id do campo -> mensagem
const status = reactive({ login: "", cadastro: "" });
const busy = reactive({ login: false, cadastro: false });

function messageFor(input) {
  const v = input.validity;
  if (v.valueMissing) return "Preencha este campo.";
  if (v.typeMismatch) return "Informe um e-mail válido.";
  if (v.tooShort) return `Use pelo menos ${input.minLength} caracteres.`;
  if (v.badInput || v.rangeUnderflow || v.rangeOverflow) return "Informe um valor válido.";
  if (v.customError) return input.validationMessage;
  return "Valor inválido.";
}

function check(input) {
  const valid = input.checkValidity();
  errors[input.id] = valid ? "" : messageFor(input);
  return valid;
}

function onInput(event) {
  // Depois do primeiro erro, revalida enquanto a pessoa digita
  if (errors[event.target.id]) check(event.target);
}

function syncPasswordMatch(form) {
  const { password, passwordConfirmation } = form.elements;
  passwordConfirmation.setCustomValidity(
    passwordConfirmation.value !== password.value ? "As senhas não coincidem." : ""
  );
}

function onPasswordInput(event) {
  syncPasswordMatch(event.currentTarget.form);
  onInput(event);
}

async function submit(event, formName, action) {
  const form = event.currentTarget;
  status[formName] = "";
  if (formName === "cadastro") syncPasswordMatch(form);

  const fields = [...form.querySelectorAll("input, select")];
  const results = fields.map(check);
  const firstInvalid = fields.find((_, i) => !results[i]);
  if (firstInvalid) { firstInvalid.focus(); return; }

  busy[formName] = true;
  try {
    await action(Object.fromEntries(new FormData(form)));
    router.push({ name: "app" });
  } catch (error) {
    status[formName] = error.message;
  } finally {
    busy[formName] = false;
  }
}
</script>

<template>
  <div class="auth">

    <aside class="auth__aside">
      <p class="auth__tagline">Comer bem pode ser simples, leve e acolhedor.</p>
      <svg class="illustration auth__art" viewBox="0 0 120 120" aria-hidden="true">
        <path class="fill-mint" d="M14 58h92a46 46 0 0 1-92 0z"/>
        <circle class="fill-leaf" cx="40" cy="48" r="14"/>
        <circle class="fill-soft" cx="64" cy="42" r="16"/>
        <circle class="fill-leaf" cx="86" cy="50" r="12"/>
        <circle class="fill-mint" cx="52" cy="36" r="6"/>
      </svg>
    </aside>

    <main class="auth__main" id="conteudo">
      <div class="auth__card">

        <RouterLink class="auth__brand" to="/">
          <img src="/assets/icons/leaf.svg" alt="" width="32" height="32">
          VittaDiet
        </RouterLink>

        <div class="tabs" role="tablist" aria-label="Acesso à conta">
          <button v-for="t in tabs" :key="t" class="tabs__tab" type="button" role="tab"
                  :id="`tab-${t}`" :aria-controls="`painel-${t}`"
                  :aria-selected="String(tab === t)" :tabindex="tab === t ? 0 : -1"
                  @click="tab = t" @keydown="onTabKeydown">
            {{ t === "login" ? "Entrar" : "Criar conta" }}
          </button>
        </div>

        <!-- Painel: Entrar -->
        <section id="painel-login" role="tabpanel" aria-labelledby="tab-login" :hidden="tab !== 'login'">
          <h1 class="auth__title">Bem-vindo de volta</h1>
          <form class="form" id="form-login" novalidate @submit.prevent="submit($event, 'login', login)">
            <div class="field">
              <label class="field__label" for="login-email">E-mail</label>
              <input class="field__input" id="login-email" name="email" type="email" autocomplete="email" required
                     aria-describedby="erro-login-email" :aria-invalid="String(Boolean(errors['login-email']))"
                     @blur="check($event.target)" @input="onInput">
              <p class="field__error" id="erro-login-email" :hidden="!errors['login-email']">{{ errors["login-email"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="login-senha">Senha</label>
              <input class="field__input" id="login-senha" name="password" type="password" autocomplete="current-password" required
                     aria-describedby="erro-login-senha" :aria-invalid="String(Boolean(errors['login-senha']))"
                     @blur="check($event.target)" @input="onInput">
              <p class="field__error" id="erro-login-senha" :hidden="!errors['login-senha']">{{ errors["login-senha"] }}</p>
            </div>
            <p class="form-status" role="alert">{{ status.login }}</p>
            <button class="btn btn--primary btn--block" type="submit" :disabled="busy.login">
              {{ busy.login ? "Aguarde…" : "Entrar" }}
            </button>
          </form>
        </section>

        <!-- Painel: Criar conta -->
        <section id="painel-cadastro" role="tabpanel" aria-labelledby="tab-cadastro" :hidden="tab !== 'cadastro'">
          <h1 class="auth__title">Crie sua conta</h1>
          <form class="form" id="form-cadastro" novalidate @submit.prevent="submit($event, 'cadastro', register)">
            <div class="field">
              <label class="field__label" for="cadastro-nome">Nome</label>
              <input class="field__input" id="cadastro-nome" name="username" type="text" autocomplete="name" required
                     aria-describedby="erro-cadastro-nome" :aria-invalid="String(Boolean(errors['cadastro-nome']))"
                     @blur="check($event.target)" @input="onInput">
              <p class="field__error" id="erro-cadastro-nome" :hidden="!errors['cadastro-nome']">{{ errors["cadastro-nome"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="cadastro-email">E-mail</label>
              <input class="field__input" id="cadastro-email" name="email" type="email" autocomplete="email" required
                     aria-describedby="erro-cadastro-email" :aria-invalid="String(Boolean(errors['cadastro-email']))"
                     @blur="check($event.target)" @input="onInput">
              <p class="field__error" id="erro-cadastro-email" :hidden="!errors['cadastro-email']">{{ errors["cadastro-email"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="cadastro-altura">Altura (cm)</label>
              <!-- O back exige height (coluna NOT NULL) -->
              <input class="field__input" id="cadastro-altura" name="height" type="number" inputmode="decimal"
                     min="50" max="260" step="any" required
                     aria-describedby="erro-cadastro-altura" :aria-invalid="String(Boolean(errors['cadastro-altura']))"
                     @blur="check($event.target)" @input="onInput">
              <p class="field__error" id="erro-cadastro-altura" :hidden="!errors['cadastro-altura']">{{ errors["cadastro-altura"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="cadastro-genero">Sexo biológico</label>
              <!-- Os valores precisam ser os do enum UserGender do back -->
              <select class="field__input" id="cadastro-genero" name="gender" required
                      aria-describedby="erro-cadastro-genero" :aria-invalid="String(Boolean(errors['cadastro-genero']))"
                      @blur="check($event.target)" @change="onInput">
                <option value="" disabled selected>Selecione uma opção</option>
                <option value="MALE">Masculino</option>
                <option value="FEMALE">Feminino</option>
                <option value="OTHER">Prefiro não responder</option>
              </select>
              <p class="field__error" id="erro-cadastro-genero" :hidden="!errors['cadastro-genero']">{{ errors["cadastro-genero"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="cadastro-senha">Senha</label>
              <input class="field__input" id="cadastro-senha" name="password" type="password" autocomplete="new-password"
                     required minlength="8"
                     aria-describedby="erro-cadastro-senha" :aria-invalid="String(Boolean(errors['cadastro-senha']))"
                     @blur="check($event.target)" @input="onPasswordInput">
              <p class="field__error" id="erro-cadastro-senha" :hidden="!errors['cadastro-senha']">{{ errors["cadastro-senha"] }}</p>
            </div>
            <div class="field">
              <label class="field__label" for="cadastro-confirmar">Confirmar senha</label>
              <input class="field__input" id="cadastro-confirmar" name="passwordConfirmation" type="password" autocomplete="new-password" required
                     aria-describedby="erro-cadastro-confirmar" :aria-invalid="String(Boolean(errors['cadastro-confirmar']))"
                     @blur="check($event.target)" @input="onPasswordInput">
              <p class="field__error" id="erro-cadastro-confirmar" :hidden="!errors['cadastro-confirmar']">{{ errors["cadastro-confirmar"] }}</p>
            </div>
            <p class="form-status" role="alert">{{ status.cadastro }}</p>
            <button class="btn btn--primary btn--block" type="submit" :disabled="busy.cadastro">
              {{ busy.cadastro ? "Aguarde…" : "Criar conta" }}
            </button>
          </form>
        </section>

        <RouterLink class="auth__back" to="/">Voltar para o início</RouterLink>
      </div>
    </main>

  </div>
</template>
