import { apiRequest } from './apiClient'

export const authService = {
  health: () => apiRequest('/health', { method: 'GET' }),
  register: (data) => apiRequest('/auth/register', { method: 'POST', body: JSON.stringify(data) }),
  verifyEmail: (data) => apiRequest('/auth/verify-email', { method: 'POST', body: JSON.stringify(data) }),
  resendOtp: (data) => apiRequest('/auth/resend-otp', { method: 'POST', body: JSON.stringify(data) }),
  login: (data) => apiRequest('/auth/login', { method: 'POST', body: JSON.stringify(data) }),
  forgotPassword: (data) => apiRequest('/auth/forgot-password', { method: 'POST', body: JSON.stringify(data) }),
  verifyResetOtp: (data) => apiRequest('/auth/verify-reset-otp', { method: 'POST', body: JSON.stringify(data) }),
  resetPassword: (data) => apiRequest('/auth/reset-password', { method: 'POST', body: JSON.stringify(data) }),
  logout: () => apiRequest('/auth/logout', { method: 'POST' }),
}
