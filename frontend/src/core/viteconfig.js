// vite.config.js
import vue from "@vitejs/plugin-vue";
import {defineConfig} from "vite";

export default defineConfig({
    plugins: [vue()],
    server: {
        port: 5173,
        proxy: { "/auth": "http://localhost:8080" },   // acrescente os outros caminhos da API
    },
});