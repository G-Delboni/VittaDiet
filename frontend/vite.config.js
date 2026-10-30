import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    port: 5173,
    // O navegador só enxerga localhost:5173; o Vite repassa /auth ao Spring.
    // Acrescente aqui os outros prefixos da API quando existirem (ex.: "/users").
    proxy: { '/api': 'http://localhost:8080' },
  },
})
