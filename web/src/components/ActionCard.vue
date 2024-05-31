<script setup>
defineProps({
  icon: {
    type: Object,
    required: true
  },
  label: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div class="card">
    <component :is="icon" />
    <span>{{ label }}</span>
  </div>
</template>

<style scoped>
.card {
  background-color: var(--color-background-soft);

  width: 120px;
  height: 120px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  padding: 16px;
  border-radius: 16px;

  cursor: pointer;
}

.card:hover {
  background-color: var(--vt-c-black-mute);
}

.card span {
  margin-top: 8px;
}
</style>