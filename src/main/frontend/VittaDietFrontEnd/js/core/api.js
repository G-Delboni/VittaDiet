import { getAccessToken, setAccessToken, saveUser, clearSession } from "/js/core/storage.js";

export const API_BASE_URL = "http://localhost:8080";

export class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.name = "ApiError";
    this.status = status;
  }
}

let refreshing = null;

// Uma renovação por vez: na aba (promise compartilhada) e entre abas (Web Locks)
export function refreshSession() {
  refreshing ??= withLock(doRefresh).finally(() => { refreshing = null; });
  return refreshing;
}

const withLock = (task) =>
    navigator.locks ? navigator.locks.request("vittadiet-refresh", task) : task();

async function doRefresh() {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
      method: "POST",
      credentials: "include",
    });
    if (!response.ok) { clearSession(); return false; }
    const data = await response.json();
    setAccessToken(data.token);
    if (data.username) saveUser({ name: data.username });
    return true;
  } catch {
    return false;
  }
}

export async function request(path, { method = "GET", body, auth = true } = {}, retry = true) {
  const headers = { Accept: "application/json" };
  if (body !== undefined) headers["Content-Type"] = "application/json";

  const token = getAccessToken();
  if (auth && token) headers.Authorization = `Bearer ${token}`;

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      method,
      headers,
      credentials: "include",                 // sem isto o cookie é ignorado
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });
  } catch {
    throw new ApiError("Não foi possível conectar ao servidor.", 0);
  }

  // Access token vencido: renova e repete a chamada uma única vez
  if (response.status === 401 && auth && retry && (await refreshSession())) {
    return request(path, { method, body, auth }, false);
  }

  if (!response.ok) throw new ApiError(await readErrorMessage(response), response.status);

  const text = await response.text();            // aceita corpo vazio (200 sem corpo, 204)
  return text ? JSON.parse(text) : null;
}

async function readErrorMessage(response) {
  try {
    const data = await response.json();
    return data.message || "Algo deu errado. Tente novamente.";
  } catch {
    return "Algo deu errado. Tente novamente.";
  }
}