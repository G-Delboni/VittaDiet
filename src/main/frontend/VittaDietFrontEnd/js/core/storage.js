// ============================================================================
// storage.js: guarda a sessão do usuário no navegador.
//
// Por enquanto usa sessionStorage (some ao fechar a aba), bom para estudar.
// ATENÇÃO (segurança): qualquer script da página consegue ler sessionStorage /
// localStorage, então um XSS vazaria o token. Quando o backend existir, a
// opção mais segura é o servidor enviar o token num cookie HttpOnly; nesse
// caso este arquivo deixa de guardar o token e guarda só dados de exibição.
// ============================================================================

const SESSION_KEY = "vittadiet.session";

export function saveSession(session) {
  try {
    sessionStorage.setItem(SESSION_KEY, JSON.stringify(session));
  } catch {
    // Armazenamento bloqueado (modo privado, política do navegador): ignora.
  }
}

export function getSession() {
  try {
    const raw = sessionStorage.getItem(SESSION_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function clearSession() {
  try {
    sessionStorage.removeItem(SESSION_KEY);
  } catch {
    // Nada a fazer.
  }
}
