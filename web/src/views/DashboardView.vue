<script setup lang="ts">
import ActionCard from '@/components/ActionCard.vue';
import BalanceToggle from '@/components/BalanceToggle.vue';
import TransferForm from '@/components/TransferForm.vue';
import WithdrawForm from '@/components/WithdrawForm.vue';
import DepositForm from '@/components/DepositForm.vue';
import BaseModal from '@/components/BaseModal.vue';
import IconDeposit from '@/components/icons/IconDeposit.vue';
import IconTransfer from '@/components/icons/IconTransfer.vue';
import IconWithdraw from '@/components/icons/IconWithdraw.vue';
import { ref } from 'vue';

const balance = ref(0)

type ModalType = 'transfer' | 'withdraw' | 'deposit'

const modals = ref({
  transfer: false,
  withdraw: false,
  deposit: false
})

const modalTitles: Record<ModalType, string> = {
  transfer: 'Transferir',
  withdraw: 'Sacar',
  deposit: 'Depositar',
};

const toggleModal = (type: ModalType, state: boolean) => {
  modals.value[type] = state
}

const actions = [
  { icon: IconTransfer, label: 'Transferir', modal: 'transfer' as ModalType},
  { icon: IconWithdraw, label: 'Sacar', modal: 'withdraw' as ModalType},
  { icon: IconDeposit, label: 'Depositar', modal: 'deposit' as ModalType},
]
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
        <ActionCard 
          v-for="action in actions"
          :key="action.modal"
          :icon="action.icon" 
          :label="action.label" 
          @click="toggleModal(action.modal, true)"
        />
      </div>
    </div>

    <BaseModal 
      v-for="(state, type) in modals"
      :key="type"
      :show="state" 
      :title="modalTitles[type]" 
      @close="toggleModal(type, false)"
    >
      <template v-if="type === 'transfer'">
        <TransferForm/>
      </template>
      <template v-if="type === 'withdraw'">
        <WithdrawForm/>
      </template>
      <template v-if="type === 'deposit'">
        <DepositForm/>
      </template>
    </BaseModal>
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