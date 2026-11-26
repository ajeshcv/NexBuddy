import { Link, NavLink, useNavigate } from 'react-router-dom'
import { Compass, LogOut, Settings, User, LayoutDashboard, Shield } from 'lucide-react'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { isAuthenticated, profile, user, logout } = useAuth()
  const navigate = useNavigate()
  const isAdmin = (profile?.role || user?.role) === 'ADMIN'

  const handleLogout = async () => {
    await logout()
    navigate('/login')
  }

  const navClass = ({ isActive }) =>
    `inline-flex items-center gap-1.5 rounded-lg px-3 py-2 text-sm font-medium transition ${
      isActive ? 'bg-indigo-50 text-indigo-600' : 'text-slate-600 hover:bg-slate-100 hover:text-slate-900'
    }`

  return (
    <header className="sticky top-0 z-30 border-b border-slate-200 bg-white/90 backdrop-blur">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4 sm:px-6">
        <Link to={isAuthenticated ? '/dashboard' : '/'} className="flex items-center gap-2.5">
          <div className="flex h-9 w-9 items-center justify-center rounded-xl bg-indigo-600 text-white shadow-sm">
            <Compass className="h-5 w-5" />
          </div>
          <span className="text-lg font-bold tracking-tight text-slate-900">NexBuddy</span>
        </Link>

        <nav className="flex items-center gap-2">
          {isAuthenticated ? (
            <>
              <NavLink to="/dashboard" className={navClass}>
                <LayoutDashboard className="h-4 w-4" />
                <span className="hidden sm:inline">Dashboard</span>
              </NavLink>
              {isAdmin && (
                <NavLink to="/admin" className={navClass}>
                  <Shield className="h-4 w-4" />
                  <span className="hidden sm:inline">Admin</span>
                </NavLink>
              )}
              <NavLink to="/profile" className={navClass}>
                <User className="h-4 w-4" />
                <span className="hidden sm:inline">{profile?.firstName || user?.name || 'Profile'}</span>
              </NavLink>
              <NavLink to="/settings" className={navClass}>
                <Settings className="h-4 w-4" />
                <span className="hidden sm:inline">Settings</span>
              </NavLink>
              <button
                onClick={handleLogout}
                className="inline-flex items-center gap-1.5 rounded-lg px-3 py-2 text-sm font-medium text-rose-600 transition hover:bg-rose-50"
              >
                <LogOut className="h-4 w-4" />
                <span className="hidden sm:inline">Logout</span>
              </button>
            </>
          ) : (
            <>
              <Link
                to="/login"
                className="rounded-lg px-3.5 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-100"
              >
                Sign in
              </Link>
              <Link
                to="/register"
                className="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:bg-indigo-700"
              >
                Get Started
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}
