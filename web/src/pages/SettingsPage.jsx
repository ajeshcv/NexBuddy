import { useEffect, useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { userService } from '../services/userService'
import StatusAlert from '../components/StatusAlert'

export default function SettingsPage() {
  const { profile, refreshProfile } = useAuth()
  const [settings, setSettings] = useState({
    notificationEnabled: true,
    profileVisible: true,
    locationVisible: true,
    theme: 'Light',
    language: 'English',
  })
  const [passwords, setPasswords] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  })
  const [status, setStatus] = useState({ type: 'info', message: '' })

  useEffect(() => {
    if (profile?.settings) {
      setSettings(profile.settings)
    }
  }, [profile])

  const handleSaveSettings = async (e) => {
    e.preventDefault()
    try {
      await userService.updateSettings(settings)
      await refreshProfile()
      setStatus({ type: 'success', message: 'Settings saved successfully.' })
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  const handleChangePassword = async (e) => {
    e.preventDefault()
    try {
      const res = await userService.changePassword(passwords)
      setPasswords({ currentPassword: '', newPassword: '', confirmPassword: '' })
      setStatus({ type: 'success', message: res.message || 'Password updated.' })
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  return (
    <div className="mx-auto max-w-2xl space-y-8">
      <StatusAlert type={status.type} message={status.message} />

      <form onSubmit={handleSaveSettings} className="space-y-4 rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <h1 className="text-xl font-bold text-slate-900">Privacy & Preferences</h1>

        {[
          { key: 'notificationEnabled', label: 'Enable Notifications' },
          { key: 'profileVisible', label: 'Public Profile Visibility' },
          { key: 'locationVisible', label: 'Share Approximate Location for Nearby Buddies' },
        ].map((item) => (
          <label key={item.key} className="flex items-center justify-between rounded-xl border border-slate-200 px-4 py-3">
            <span className="text-sm font-medium text-slate-700">{item.label}</span>
            <input
              type="checkbox"
              checked={Boolean(settings[item.key])}
              onChange={(e) => setSettings({ ...settings, [item.key]: e.target.checked })}
              className="h-4 w-4 accent-indigo-600"
            />
          </label>
        ))}

        <div className="grid gap-4 sm:grid-cols-2">
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Theme</label>
            <select
              value={settings.theme}
              onChange={(e) => setSettings({ ...settings, theme: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3 py-2 text-sm"
            >
              <option value="Light">Light</option>
              <option value="Dark">Dark</option>
            </select>
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Language</label>
            <select
              value={settings.language}
              onChange={(e) => setSettings({ ...settings, language: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3 py-2 text-sm"
            >
              <option value="English">English</option>
            </select>
          </div>
        </div>

        <button className="rounded-xl bg-indigo-600 px-5 py-2.5 text-sm font-semibold text-white hover:bg-indigo-700">
          Save Preferences
        </button>
      </form>

      <form onSubmit={handleChangePassword} className="space-y-4 rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-bold text-slate-900">Change Password</h2>
        <input
          type="password"
          required
          placeholder="Current password"
          value={passwords.currentPassword}
          onChange={(e) => setPasswords({ ...passwords, currentPassword: e.target.value })}
          className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
        />
        <input
          type="password"
          required
          minLength={8}
          placeholder="New password"
          value={passwords.newPassword}
          onChange={(e) => setPasswords({ ...passwords, newPassword: e.target.value })}
          className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
        />
        <input
          type="password"
          required
          minLength={8}
          placeholder="Confirm new password"
          value={passwords.confirmPassword}
          onChange={(e) => setPasswords({ ...passwords, confirmPassword: e.target.value })}
          className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
        />
        <button className="rounded-xl bg-slate-900 px-5 py-2.5 text-sm font-semibold text-white hover:bg-slate-800">
          Update Password
        </button>
      </form>
    </div>
  )
}
