import { API_BASE_URL, STORAGE_KEYS } from '../utils/constants'

async function refreshAccessToken() {
  const refreshToken = localStorage.getItem(STORAGE_KEYS.REFRESH_TOKEN)
  if (!refreshToken) return null

  const response = await fetch(`${API_BASE_URL}/auth/refresh-token`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ refreshToken }),
  })
  if (!response.ok) return null

  const payload = await response.json()
  const newToken = payload?.data?.token
  if (newToken) {
    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, newToken)
    return newToken
  }
  return null
}

export async function apiRequest(path, options = {}, retry = true) {
  const token = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)
  const isFormData = options.body instanceof FormData

  const headers = {
    ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
  })

  if (response.status === 401 && retry && !path.startsWith('/auth/')) {
    const newToken = await refreshAccessToken()
    if (newToken) {
      return apiRequest(path, options, false)
    }
    localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN)
    localStorage.removeItem(STORAGE_KEYS.REFRESH_TOKEN)
    localStorage.removeItem(STORAGE_KEYS.USER)
  }

  const payload = await response.json().catch(() => ({}))
  if (!response.ok) {
    const message =
      payload?.errors?.length
        ? payload.errors.join(', ')
        : payload?.message || `Request failed (${response.status})`
    const err = new Error(message)
    err.status = response.status
    err.payload = payload
    throw err
  }

  return payload
}
