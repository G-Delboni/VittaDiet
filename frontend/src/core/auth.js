import { request, refreshSession, ApiError } from "./api.js";
import { getAccessToken, setAccessToken, saveUser, getUser, clearSession } from "/js/core/storage.js";

export async function login({ email, password }) {
  try {
    const data = await request("/auth/login", { method: "POST", body: { email, password }, auth: false });
    setAccessToken(data.token);
    if (data.username) saveUser({ name: data.username });
  } catch (error) {
    if (error.status === 401) throw new ApiError("E-mail ou senha inválidos.", 401);
    throw error;
  }
}

export async function register({ name, email, password, height, gender }) {
  await request("/auth/register", {
    method: "POST",
    auth: false,
    body: { username: name, email, password, height: Number(height), userGender: gender },
  });
  await login({ email, password });              // o back não loga ao cadastrar
}

export async function logout() {
  try { await request("/auth/logout", { method: "POST", auth: false }); } catch { /* segue */ }
  clearSession();
}

export const isAuthenticated = () => Boolean(getAccessToken());
export const getCurrentUser = () => getUser();

// Após recarregar a página o token em memória some: recupera pelo cookie
export async function restoreSession() {
  return getAccessToken() ? true : refreshSession();
}

export async function requireAuth() {
  if (await restoreSession()) return true;
  window.location.replace("/login/");
  return false;
}

export async function redirectIfAuthenticated() {
  if (await restoreSession()) window.location.replace("/app/");
}