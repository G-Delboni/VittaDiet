# VittaDiet Web

Front-end em Vue 3 + Vite. Fluxo: **Landing (`/`) → Login (`/login`) → User Page (`/app`)**.

## Como rodar

```bash
cd frontend
npm install
npm run dev      # http://localhost:5173
```

O Spring precisa estar em `http://localhost:8080`. O Vite repassa `/auth` ao
Spring (proxy em `vite.config.js`), então o navegador só enxerga `localhost:5173`:
sem CORS em dev e com o cookie `refresh_token` (SameSite=Strict) funcionando.
Novos prefixos da API (ex.: `/users`) precisam ser acrescentados ao `proxy`.

## Estrutura

```
index.html            entrada do Vite (sprite SVG + <div id="app">)
src/assets/           ícones e imagens (importados via @/assets/...)
src/main.js           cria o app, importa o CSS base
src/router/           rotas e guard de sessão
src/views/            LandingView, LoginView, AppView
src/components/       navbar.js (menu mobile)
src/core/             api (fetch + refresh), auth (login/logout), storage (sessão)
src/css/              tokens -> base -> componentes -> páginas
```

## Autenticação

- Access token (JWT, 1h): só em memória (`storage.js`), enviado em `Authorization: Bearer`.
- Refresh token: cookie HttpOnly gerenciado pelo back; `credentials: "include"` em toda chamada.
- 401 em rota autenticada → `/auth/refresh` → repete a chamada uma vez.
- Após F5 o guard do router chama `/auth/refresh` para recuperar o access token.
- `VITE_API_URL` (opcional) aponta para outro host; o padrão é a mesma origem.
- O guard de rota é só experiência de uso: **a proteção real é o backend**.

## Headers de segurança

- **Produção (Vercel):** `vercel.json` define CSP estrito (`script-src 'self'`, sem inline), `nosniff`, `X-Frame-Options`, `Referrer-Policy`, `Permissions-Policy` e HSTS, além do fallback de SPA. Se você adicionar um recurso externo (fonte, CDN, analytics), libere o domínio no CSP.
- **Dev:** sem CSP, porque o HMR do Vite usa inline/eval.
- **Regra:** nada de `<script>` ou `style=""` inline no HTML. Para estilo dinâmico use classes CSS.
- **API:** os headers da resposta JSON ficam em `SecurityConfiguration` (`.headers(...)`).
