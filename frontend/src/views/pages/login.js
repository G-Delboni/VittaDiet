// ============================================================================
// login.js: abas Entrar / Criar conta, validação e envio dos formulários.
// Usa a Constraint Validation API do navegador (required, type="email",
// minlength) e só personaliza as mensagens.
// ============================================================================

import { login, register, redirectIfAuthenticated } from "@/core/auth.js";

redirectIfAuthenticated();

// ---------- Abas ----------

const tabs = [...document.querySelectorAll('[role="tab"]')];
const panels = [...document.querySelectorAll('[role="tabpanel"]')];

function selectTab(tab) {
  tabs.forEach((item) => {
    const selected = item === tab;
    item.setAttribute("aria-selected", String(selected));
    item.tabIndex = selected ? 0 : -1;
  });
  panels.forEach((panel) => {
    panel.hidden = panel.id !== tab.getAttribute("aria-controls");
  });
}

tabs.forEach((tab) => {
  tab.addEventListener("click", () => selectTab(tab));

  // Setas esquerda/direita trocam de aba (padrão de acessibilidade para tabs)
  tab.addEventListener("keydown", (event) => {
    if (event.key !== "ArrowRight" && event.key !== "ArrowLeft") return;
    const step = event.key === "ArrowRight" ? 1 : -1;
    const next = tabs[(tabs.indexOf(tab) + step + tabs.length) % tabs.length];
    selectTab(next);
    next.focus();
  });
});

// A landing leva para /login/?modo=cadastro quando o botão é "Criar conta"
if (new URLSearchParams(window.location.search).get("modo") === "cadastro") {
  selectTab(document.getElementById("tab-cadastro"));
}

// ---------- Validação ----------

function getErrorMessage(input) {
  const { validity } = input;
  if (validity.valueMissing) return "Preencha este campo.";
  if (validity.typeMismatch) return "Informe um e-mail válido.";
  if (validity.tooShort) return `Use pelo menos ${input.minLength} caracteres.`;
  if (validity.customError) return input.validationMessage;
  return "Valor inválido.";
}

// Mostra (ou limpa) o erro de um campo e devolve se ele é válido
function showFieldError(input) {
  const errorBox = document.getElementById(input.getAttribute("aria-describedby"));
  const valid = input.checkValidity();
  input.setAttribute("aria-invalid", String(!valid));
  errorBox.textContent = valid ? "" : getErrorMessage(input);
  errorBox.hidden = valid;
  return valid;
}

function bindForm(form, submitAction, beforeValidate = () => {}) {
  const fields = [...form.querySelectorAll("input")];
  const status = form.querySelector("[data-status]");
  const button = form.querySelector('button[type="submit"]');

  fields.forEach((field) => {
    field.addEventListener("blur", () => showFieldError(field));
    field.addEventListener("input", () => {
      // Depois do primeiro erro, revalida enquanto a pessoa digita
      if (field.getAttribute("aria-invalid") === "true") showFieldError(field);
    });
  });

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    status.textContent = "";
    beforeValidate();

    const results = fields.map(showFieldError);
    const firstInvalid = fields.find((_, index) => !results[index]);
    if (firstInvalid) {
      firstInvalid.focus();
      return;
    }

    const originalLabel = button.textContent;
    button.disabled = true;
    button.textContent = "Aguarde…";

    try {
      await submitAction(Object.fromEntries(new FormData(form)));
      window.location.assign("/app/");
    } catch (error) {
      status.textContent = error.message;
      button.disabled = false;
      button.textContent = originalLabel;
    }
  });
}

// ---------- Formulários ----------

bindForm(document.getElementById("form-login"), login);

const formRegister = document.getElementById("form-cadastro");
const password = document.getElementById("cadastro-senha");
const confirmation = document.getElementById("cadastro-confirmar");

function syncPasswordMatch() {
  const mismatch = confirmation.value !== password.value;
  confirmation.setCustomValidity(mismatch ? "As senhas não coincidem." : "");
}

password.addEventListener("input", syncPasswordMatch);
confirmation.addEventListener("input", syncPasswordMatch);

bindForm(formRegister, register, syncPasswordMatch);
