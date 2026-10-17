import { request, refreshSession, ApiError } from "./api.js";
import { getAccessToken, setAccessToken, saveUser, getUser, clearSession } from "./storage.js";

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

// Os nomes seguem o form (username/height/gender) e o RegisterRequestDTO do back.
export async function register({ username, email, password, height, gender }) {
  try {
    await request("/auth/register", {
      method: "POST",
      auth: false,
      body: { username, email, password, height: Number(height), userGender: gender },
    });
  } catch (error) {
    if (error.status === 409) throw new ApiError("Este e-mail já está cadastrado.", 409);
    if (error.status === 400) throw new ApiError("Confira os dados informados.", 400);
    throw error;
  }
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
  return getAccessToken() ? true : refreshSession();   // refreshSession nunca lança
}
