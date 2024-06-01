<script setup>
import MoneyInput from './MoneyInput.vue';
import { ref } from 'vue';

const amount = ref(0)
const toAccount = ref("")
const toDigit = ref("")

const handleSubmit = async () => {}
</script>

<template>
  <form class="transfer-form" @submit.prevent="handleSubmit">
    <label for="amount">Quanto você quer transferir?</label>
    <MoneyInput v-model="amount"/>

    <input type="text" placeholder="Conta" v-model="toAccount">
    <input type="text" placeholder="Dígito" v-model="toDigit">
    <button type="submit">Continuar</button>
  </form>
</template>

<style scoped>
.transfer-form {
  display: flex;
  flex-direction: column;
}

.transfer-form input {
  height: 40px;
  background: transparent;
  outline: none;
  border: none;
  border-bottom: 1px solid var(--color-border);
  color: #fff;
  font-size: 1rem;
  margin: 16px 0;
}

.transfer-form button[type="submit"] {
  background: #fff;
  height: 48px;
  border-radius: 16px;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 24px;
}
</style>