import { createContext, useCallback, useContext, useEffect, useState } from 'react'
import { authService } from '../services/authService'
import { userService } from '../services/userService'
import { STORAGE_KEYS } from '../utils/constants'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const raw = localStorage.getItem(STORAGE_KEYS.USER)
    try {
      return raw ? JSON.parse(raw) : null
    } catch {
      return null
    }
  })
  const [profile, setProfile] = useState(null)
  const [loading, setLoading] = useState(Boolean(localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)))

  const refreshProfile = useCallback(async () => {
    const token = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)
    if (!token) {
      setProfile(null)
      setLoading(false)
      return null
    }
    try {
      const res = await userService.getMyProfile()
      setProfile(res.data)
      return res.data
    } catch {
      localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN)
      localStorage.removeItem(STORAGE_KEYS.REFRESH_TOKEN)
      localStorage.removeItem(STORAGE_KEYS.USER)
      setUser(null)
      setProfile(null)
      return null
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    refreshProfile()
  }, [refreshProfile])

  const login = async (credentials) => {
    const res = await authService.login(credentials)
    const { token, refreshToken, user: userData } = res.data
    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, token)
    localStorage.setItem(STORAGE_KEYS.REFRESH_TOKEN, refreshToken)
    localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(userData))
    setUser(userData)
    await refreshProfile()
    return res
  }

  const logout = async () => {
    try {
      if (localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)) {
        await authService.logout()
      }
    } catch {
      // Clear local session even if token already expired
    } finally {
      localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN)
      localStorage.removeItem(STORAGE_KEYS.REFRESH_TOKEN)
      localStorage.removeItem(STORAGE_KEYS.USER)
      setUser(null)
      setProfile(null)
    }
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        profile,
        loading,
        isAuthenticated: Boolean(user && localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)),
        login,
        logout,
        refreshProfile,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider')
  return ctx
}
