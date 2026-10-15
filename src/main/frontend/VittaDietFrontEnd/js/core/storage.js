// O access token fica SÓ em memória. O refresh token é um cookie HttpOnly
// (o JavaScript nem enxerga). Em sessionStorage vão só dados de exibição.
const USER_KEY = "vittadiet.user";
let accessToken = null;

export const getAccessToken = () => accessToken;
export const setAccessToken = (token) => { accessToken = token; };

export function saveUser(user) {
  try { sessionStorage.setItem(USER_KEY, JSON.stringify(user)); } catch { /* ignora */ }
}

export function getUser() {
  try {
    const raw = sessionStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch { return null; }
}

export function clearSession() {
  accessToken = null;
  try { sessionStorage.removeItem(USER_KEY); } catch { /* ignora */ }
}