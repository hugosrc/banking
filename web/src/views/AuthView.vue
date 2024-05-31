<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const account = ref('');
const digit = ref('');

const router = useRouter()

const handleSubmit = () => {
  router.push({
    path: '/',
    query: {
      account: account.value,
      digit: digit.value
    }
  })
};
</script>

<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-left">
        <h1>Acessar conta</h1>
        <form @submit.prevent="handleSubmit" class="auth-form">
          <div class="form-group">
            <input 
              id="account" 
              type="text" 
              v-model="account" 
              required 
              placeholder="Digite sua conta" 
            />
          </div>
          <div class="form-group">
            <input 
              id="digit" 
              type="text" 
              v-model="digit" 
              required 
              placeholder="Digite seu dígito" 
            />
          </div>
          <button type="submit" class="login-button">Entrar</button>
        </form>
      </div>
      <div class="auth-right">
        <h2>Bem-vindo ao Banking</h2>
        <p>Login para acessar sua conta</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.auth-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: var(--color-background);
}

.auth-container {
  display: flex;
  width: 80%;
  max-width: 1200px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
}

.auth-left {
  flex: 1;
  background-color: var(--color-background-soft);
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.auth-left h1 {
  margin-bottom: 20px;
  font-size: 24px;
  color: var(--color-heading);
}

.auth-form {
  display: flex;
  flex-direction: column;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: none;
  border-bottom: 1px solid var(--color-border);
  font-size: 16px;
  color: var(--color-text);
  background: transparent;
}

input:focus {
  border-color: var(--color-border-hover);
  outline: none;
}

.login-button {
  background: #fff;
  height: 48px;
  border-radius: 16px;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 24px;
}

.login-button:hover {
  background-color: #fff;
  filter: brightness(0.9);
}

.auth-right {
  flex: 1;
  background: linear-gradient(to right, #6c63ff, #7b78ff);
  color: var(--vt-c-white);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
}

.auth-right h2 {
  margin-bottom: 10px;
  font-size: 32px;
  color: var(--color-heading);
}

.auth-right p {
  font-size: 18px;
  max-width: 300px;
  text-align: center;
  color: var(--color-text);
}
</style>
