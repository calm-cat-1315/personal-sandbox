<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { importWorkHours, listWorkHours, type WorkHourRecord } from '../api/workHours'

const records = ref<WorkHourRecord[]>([])
const loading = ref(false)
const importing = ref(false)
const error = ref('')
const success = ref('')
const selectedFile = ref<File | null>(null)

async function refresh() {
  loading.value = true
  error.value = ''
  try {
    records.value = await listWorkHours()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to load records'
  } finally {
    loading.value = false
  }
}

function onFileChange(event: Event) {
  const input = event.target as HTMLInputElement
  selectedFile.value = input.files?.[0] ?? null
  success.value = ''
  error.value = ''
}

async function onImport() {
  if (!selectedFile.value) {
    error.value = 'Please choose a CSV file first'
    return
  }

  importing.value = true
  error.value = ''
  success.value = ''
  try {
    const result = await importWorkHours(selectedFile.value)
    success.value = result.message
    selectedFile.value = null
    const input = document.getElementById('csv-file') as HTMLInputElement | null
    if (input) {
      input.value = ''
    }
    await refresh()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Import failed'
  } finally {
    importing.value = false
  }
}

onMounted(refresh)
</script>

<template>
  <section class="page">
    <header class="header">
      <h1>工作管理 · 日工时</h1>
      <p>导入 CSV 日工时记录，并在下方查看明细。</p>
    </header>

    <div class="import-panel">
      <label class="file-label" for="csv-file">选择 CSV 文件</label>
      <input id="csv-file" type="file" accept=".csv,text/csv" @change="onFileChange" />
      <button type="button" :disabled="importing" @click="onImport">
        {{ importing ? '导入中…' : '导入' }}
      </button>
      <button type="button" class="secondary" :disabled="loading" @click="refresh">刷新列表</button>
    </div>

    <p v-if="success" class="success">{{ success }}</p>
    <p v-if="error" class="error">{{ error }}</p>
    <p v-if="loading" class="muted">加载中…</p>

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>日期</th>
            <th>工时</th>
            <th>标签</th>
            <th>备注</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="!loading && records.length === 0">
            <td colspan="4" class="empty">暂无记录，先导入一份 CSV。</td>
          </tr>
          <tr v-for="item in records" :key="item.id">
            <td>{{ item.workDate }}</td>
            <td>{{ item.hours }}</td>
            <td>{{ item.label || '—' }}</td>
            <td>{{ item.note || '—' }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <p class="hint">
      CSV 表头：<code>work_date,hours,label,note</code> · 示例文件见
      <code>samples/work-hours-sample.csv</code>
    </p>
  </section>
</template>
