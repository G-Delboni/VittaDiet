// ============================================================================
// auth.js: login, cadastro, logout e "guards" de página.
//
// USE_MOCK = true faz o fluxo funcionar SEM backend (aceita qualquer dado
// válido), para você treinar Landing -> Login -> User Page.
// Quando o backend estiver pronto, mude para false e ajuste os TODOs abaixo.
// ============================================================================

import { request } from "/js/core/api.js";
import { saveSession, getSession, clearSession } from "/js/core/storage.js";

const USE_MOCK = true;

export async function login({ email, password }) {
  if (USE_MOCK) {
    saveSession({ token: "mock-token", user: { name: nameFromEmail(email) } });
    return;
  }

  // TODO: ajuste caminho e formato da resposta ao contrato real do backend.
  const data = await request("/auth/login", {
    method: "POST",
    body: { email, password },
    auth: false,
  });
  saveSession({ token: data.token, user: data.user });
}

export async function register({ name, email, password }) {
  if (USE_MOCK) {
    saveSession({ token: "mock-token", user: { name } });
    return;
  }

  // TODO: ajuste caminho e formato da resposta ao contrato real do backend.
  const data = await request("/auth/register", {
    method: "POST",
    body: { name, email, password },
    auth: false,
  });
  saveSession({ token: data.token, user: data.user });
}

export function logout() {
  clearSession();
}

export function isAuthenticated() {
  return Boolean(getSession()?.token);
}

export function getCurrentUser() {
  return getSession()?.user ?? null;
}

/**
 * Guard da área logada. Devolve true se pode continuar; senão manda para o login.
 * Isto é só experiência de uso: a proteção real dos dados é feita pelo backend.
 */
export function requireAuth() {
  if (isAuthenticated()) return true;
  window.location.replace("/login/");
  return false;
}

/** Na página de login: quem já está logado vai direto para a área do usuário. */
export function redirectIfAuthenticated() {
  if (isAuthenticated()) {
    window.location.replace("/app/");
  }
}

function nameFromEmail(email) {
  return email.split("@")[0];
}
