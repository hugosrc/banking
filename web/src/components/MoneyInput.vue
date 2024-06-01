<script setup>
import { ref, watch } from 'vue';

const amount = ref('0,00')

const emit = defineEmits(['update:modelValue']);

const formatCurrency = (value) => {
  const cleanedValue = value.replace(/\D/g, '') || '0';
  const num = parseInt(cleanedValue).toString() || '0';
  if (num === '0') return '0,00';

  const paddedNum = num.padStart(3, '0');
  const cents = paddedNum.slice(-2);
  const integerPart = paddedNum.slice(0, -2);

  return `${integerPart},${cents}`;
}

watch(amount, (newAmount) => {
  amount.value = formatCurrency(newAmount);

  const rawValue = parseFloat(amount.value.replace(',', '.'))
  emit('update:modelValue', rawValue)
});
</script>

<template>
  <div class="money-input">
    <span>R$</span>
    <input type="text" name="amount" v-model="amount">
  </div>
</template>

<style scoped>
.money-input input {
  outline: none;

  height: 40px;
  background: transparent;
  border: none;

  font-size: 1.6rem;
  color: #fff;

  margin: 0 8px;
}
</style>
