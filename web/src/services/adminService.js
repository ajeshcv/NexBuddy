import { apiRequest } from './apiClient'

export const adminService = {
  getDashboard: () => apiRequest('/admin/dashboard', { method: 'GET' }),
  getUsers: () => apiRequest('/admin/users', { method: 'GET' }),
  suspendUser: (userId) => apiRequest(`/admin/users/${userId}/suspend`, { method: 'PUT' }),
  activateUser: (userId) => apiRequest(`/admin/users/${userId}/activate`, { method: 'PUT' }),
}
