<script setup>
import { computed, ref } from 'vue'
import IconEye from './icons/IconEye.vue';
import IconEyeClosed from './icons/IconEyeClosed.vue';

const props = defineProps({
  currencySymbol: {
    type: String,
    default: 'R$'
  },
  balance: {
    type: Number,
    required: true
  }
})

const formatCurrency = (value) => {
  const amount = value.toFixed(2);
  return amount.toString().replace('.', ',');
}

const formatedBalance = computed(() => {
  return formatCurrency(props.balance)
})

const isBalanceVisible = ref(true)

const toggleBalanceVisibility = () => {
  isBalanceVisible.value = !isBalanceVisible.value
}
</script>

<template>
  <div class="balance-toggle">
    <header>
      <span>Valor disponível</span>
      <button @click="toggleBalanceVisibility">
        <IconEye v-if="isBalanceVisible"/>
        <IconEyeClosed v-else/>
      </button>
    </header>

    <div class="balance">
      <span>{{ currencySymbol }}</span>
      <p v-if="isBalanceVisible">{{ formatedBalance }}</p>
      <p v-else>.....</p>
    </div>
  </div>
</template>

<style scoped>
.balance-toggle {
  background-color: var(--color-background-soft);
  border-radius: 16px;
  padding: 24px;
}

header {
  display: flex;
  justify-content: space-between;
}

header button {
  background: transparent;
  border: none;
  color: #fff;
}

.balance {
  display: flex;
  margin-top: 32px;
}

.balance span {
  font-size: 1rem;
}

.balance p {
  font-size: 2.4rem;
  line-height: 6px;
  color: var(--vt-c-white);
}
</style>