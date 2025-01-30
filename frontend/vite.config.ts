// vite.config.ts
import { defineConfig } from 'vite'

export default defineConfig({
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/oauth2/authorization/kakao': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/login/oauth2/code/kakao': {  // 카카오 콜백 URL도 프록시 설정
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
