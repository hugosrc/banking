<script setup>
import { ref } from 'vue';
import MoneyInput from './MoneyInput.vue';

const amount = ref(0)

const handleSubmit = async () => {}
</script>

<template>
  <form class="withdraw-form" @submit.prevent="handleSubmit">
    <label for="amount">Quanto você quer sacar?</label>
    <MoneyInput v-model="amount"/>

    <button type="submit">Continuar</button>
  </form>
</template>

<style scoped>
.withdraw-form {
  display: flex;
  flex-direction: column;
}

.withdraw-form button[type="submit"] {
  background: #fff;
  height: 48px;
  border-radius: 16px;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 24px;
}
</style>