export default function StatusAlert({ type = 'info', message }) {
  if (!message) return null

  const styles = {
    success: 'border-emerald-200 bg-emerald-50 text-emerald-800',
    error: 'border-rose-200 bg-rose-50 text-rose-800',
    info: 'border-indigo-200 bg-indigo-50 text-indigo-800',
  }

  return (
    <div className={`rounded-xl border px-4 py-3 text-sm font-medium ${styles[type] || styles.info}`}>
      {message}
    </div>
  )
}
