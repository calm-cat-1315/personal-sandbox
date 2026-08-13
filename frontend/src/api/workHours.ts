export interface WorkHourRecord {
  id: number
  workDate: string
  hours: number
  label: string | null
  note: string | null
}

export interface WorkHoursImportResponse {
  importedCount: number
  message: string
}

export async function listWorkHours(): Promise<WorkHourRecord[]> {
  const response = await fetch('/api/work-hours')
  if (!response.ok) {
    throw new Error(`Failed to load work hours (${response.status})`)
  }
  return response.json()
}

export async function importWorkHours(file: File): Promise<WorkHoursImportResponse> {
  const formData = new FormData()
  formData.append('file', file)

  const response = await fetch('/api/work-hours/import', {
    method: 'POST',
    body: formData,
  })

  if (!response.ok) {
    let message = `Import failed (${response.status})`
    try {
      const body = await response.json()
      if (body?.error) {
        message = body.error
      }
    } catch {
      // ignore JSON parse errors
    }
    throw new Error(message)
  }

  return response.json()
}
