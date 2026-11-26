import { apiRequest } from './apiClient'

export const userService = {
  getMyProfile: () => apiRequest('/users/me', { method: 'GET' }),
  updateProfile: (data) => apiRequest('/users/profile', { method: 'PUT', body: JSON.stringify(data) }),
  uploadProfilePicture: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return apiRequest('/users/profile-picture', { method: 'POST', body: formData })
  },
  uploadCoverPhoto: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return apiRequest('/users/cover-photo', { method: 'POST', body: formData })
  },
  getAvailableInterests: () => apiRequest('/interests', { method: 'GET' }),
  updateInterests: (interestIds) =>
    apiRequest('/users/interests', { method: 'PUT', body: JSON.stringify({ interestIds }) }),
  getSettings: () => apiRequest('/users/settings', { method: 'GET' }),
  updateSettings: (data) => apiRequest('/users/settings', { method: 'PUT', body: JSON.stringify(data) }),
  changePassword: (data) => apiRequest('/users/change-password', { method: 'PUT', body: JSON.stringify(data) }),
  deleteAccount: () => apiRequest('/users/me', { method: 'DELETE' }),
}
