// ============================================================================
// api.js: único lugar que conversa com o backend (fetch).
// Todas as páginas chamam request(); assim URL base, cabeçalhos e tratamento
// de erro ficam centralizados.
// ============================================================================

import { getSession } from "/js/core/storage.js";

// TODO: ajuste para a URL real do backend (Spring Boot) quando ele existir.
export const API_BASE_URL = "http://localhost:8080";

export class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.name = "ApiError";
    this.status = status;
  }
}

/**
 * Faz uma requisição JSON ao backend.
 * @param {string} path  Caminho, ex.: "/auth/login"
 * @param {{method?: string, body?: object, auth?: boolean}} options
 */
export async function request(path, { method = "GET", body, auth = true } = {}) {
  const headers = { Accept: "application/json" };

  if (body !== undefined) {
    headers["Content-Type"] = "application/json";
  }

  if (auth) {
    const session = getSession();
    if (session?.token) {
      headers.Authorization = `Bearer ${session.token}`;
    }
  }

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      method,
      headers,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });
  } catch {
    // Falha de rede: servidor fora do ar, sem internet, CORS bloqueado...
    throw new ApiError("Não foi possível conectar ao servidor.", 0);
  }

  if (!response.ok) {
    throw new ApiError(await readErrorMessage(response), response.status);
  }

  return response.status === 204 ? null : response.json();
}

async function readErrorMessage(response) {
  try {
    const data = await response.json();
    // TODO: ajuste ao formato de erro que o backend realmente devolver.
    return data.message || "Algo deu errado. Tente novamente.";
  } catch {
    return "Algo deu errado. Tente novamente.";
  }
}
