<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<template>
  <div class="app-shell">
    <nav class="nav">
      <span class="brand">personal-sandbox</span>
      <RouterLink to="/work-hours">工作管理</RouterLink>
    </nav>
    <main>
      <RouterView />
    </main>
  </div>
</template>
