<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue';

const props = defineProps({
  show: Boolean,
  title: String
})

const emit = defineEmits(['close'])

const closeModal = () => {
  emit('close')
}

const handleKeydown = (event) => {
  if (event.key === 'Escape' && props.show) {
    closeModal();
  }
};

onMounted(() => {
  window.addEventListener('keydown', handleKeydown);
});

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown);
});
</script>

<template>
  <div v-if="show" class="modal-overlay" @click.self="closeModal">
    <div class="modal">
      <header>
        <h3>{{ title }}</h3>
        <button @click="closeModal" class="close-button">&times;</button>
      </header>
      
      <div class="content">
        <slot/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: var(--color-background);
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  padding: 48px;
}

.modal header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 8px;
}

.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #fff;
}

.content {
  padding: 16px 0;
}
</style>