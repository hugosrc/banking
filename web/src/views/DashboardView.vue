<script setup lang="ts">
import BalanceToggle from '@/components/BalanceToggle.vue';
import { ref } from 'vue';

const balance = ref(0)
</script>

<template>
  <div class="dashboard">
    <header class="greeting-header">
      <div>Olá, bom dia!</div>
    </header>
    
    <section class="wallet">
      <h2>Sua Carteira</h2>
      <div class="content">
        <BalanceToggle :balance="balance"/>
      </div>
    </section>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 2rem;
}

.greeting-header {
  margin-top: 48px;
  font-size: 1rem;
}

.wallet {
  margin: 32px 0;
}

.wallet h2 {
  color: #fff;
  margin-bottom: 16px
}
</style>