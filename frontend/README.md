# VittaDiet Web

Front-end em HTML, CSS e JavaScript puros (sem framework e sem build).
Fluxo: **Landing Page → Login Page → User Page**.

## Como rodar

Os módulos ES (`<script type="module">`) e os caminhos absolutos (`/css/...`)
exigem um servidor local servindo a pasta na raiz. Abrir o arquivo direto
(`file://`) não funciona.

```bash
npx serve .
# ou, no VS Code: extensão Live Server (clique direito em index.html)
```

Depois abra `http://localhost:3000` (ou a porta que o servidor indicar).

## Estrutura

```
index.html               Landing
login/index.html         Login e cadastro (abas)
app/index.html           User Page (área logada)
assets/                  ícones e imagens
css/
  base/                  tokens (paleta, fontes, raios), reset, tipografia
  components/            button, navbar, card, form, illustration
  pages/                 landing, login, app (só o que é específico da página)
js/
  core/                  api (fetch), auth (login/guards), storage (sessão)
  components/            navbar (menu mobile)
  pages/                 landing, login, app
```

## Ideias-chave

- **Tokens:** todas as cores e fontes vêm de `src/css/base/tokens.css`. Trocar a paleta = editar um arquivo.
- **Camadas de CSS:** ordem de carregamento tokens → reset → tipografia → componentes → página. Componentes são reutilizados; a página só define o que é dela.
- **Nomes de classes:** convenção BEM (`bloco__elemento--modificador`).
- **Mobile-first:** estilo base para celular; `@media (min-width: ...)` amplia para telas maiores.
- **Módulos ES:** cada HTML carrega só o seu script de `src/views/pages`, que importa o que precisa de `core/` e `components/`.
- **Sprite SVG:** as ilustrações da landing ficam definidas uma vez (`<symbol>`) e são usadas com `<use href="#id">`.

## Onde cada conceito é praticado

| Conceito | Arquivo |
| --- | --- |
| Flexbox com `flex-wrap` (banner + conteúdo) | `src/css/pages/landing.css` |
| CSS Grid (painéis) | `src/css/pages/app.css` |
| Menu mobile acessível (`aria-expanded`) | `src/components/navbar.js` |
| `IntersectionObserver` (aparecer ao rolar) | `src/views/pages/landing.js` |
| Abas acessíveis (`role="tab"`, setas do teclado) | `src/views/pages/login.js` |
| Constraint Validation API + mensagens próprias | `src/views/pages/login.js` |
| `fetch` com `async/await` e erros | `src/core/api.js` |
| Guard de rota no front | `src/core/auth.js` e `src/views/pages/app.js` |

## Modo mock (sem backend)

`src/core/auth.js` começa com `USE_MOCK = true`: qualquer login ou cadastro
válido cria uma sessão falsa e leva à User Page. Para conectar ao backend:

1. Ajuste `API_BASE_URL` em `src/core/api.js`.
2. Mude `USE_MOCK` para `false` em `src/core/auth.js`.
3. Ajuste os caminhos (`/auth/login`, `/auth/register`) e o formato das respostas
   (marcados com `TODO`) ao contrato real da API.

## Segurança (lembretes)

- O guard da User Page é só experiência de uso. **A proteção real é o backend**: toda rota de dados deve validar o token.
- `sessionStorage` é legível por qualquer script da página (risco em caso de XSS). Com o backend, prefira cookie `HttpOnly`.
- Validação no front melhora a experiência, mas **nunca substitui a validação no servidor**.
- O backend precisa liberar CORS para a origem do front durante o desenvolvimento.

## Próximos passos sugeridos

1. Trocar as ilustrações do banner por fotos reais (`src/assets/images`).
2. Substituir o espaço reservado de "Visualização simplificada" quando o design de progresso for decidido.
3. Preencher a User Page: resumo, metas, progresso e refeições.
4. Decidir se o app completo continua em JS puro ou migra para Vue 3 + Vite (CSS em tokens e componentes facilita a migração).
