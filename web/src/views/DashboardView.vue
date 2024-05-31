<script setup lang="ts">
import ActionCard from '@/components/ActionCard.vue';
import BalanceToggle from '@/components/BalanceToggle.vue';
import IconDeposit from '@/components/icons/IconDeposit.vue';
import IconTransfer from '@/components/icons/IconTransfer.vue';
import IconWithdraw from '@/components/icons/IconWithdraw.vue';
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

    <div class="quick-actions">
      <h2>Ações Rápidas</h2>
      <div class="content">
        <ActionCard :icon="IconTransfer" label="Transferir"/>
        <ActionCard :icon="IconWithdraw" label="Sacar"/>
        <ActionCard :icon="IconDeposit" label="Depositar"/>
      </div>
    </div>
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

.quick-actions .content {
  display: flex;
  gap: 16px;
}

.wallet h2,
.quick-actions h2 {
  color: #fff;
  margin-bottom: 16px
}
</style>