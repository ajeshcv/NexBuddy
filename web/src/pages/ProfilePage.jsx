import { useEffect, useState } from 'react'
import { Camera } from 'lucide-react'
import { useAuth } from '../context/AuthContext'
import { userService } from '../services/userService'
import StatusAlert from '../components/StatusAlert'

export default function ProfilePage() {
  const { profile, refreshProfile } = useAuth()
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    username: '',
    bio: '',
    city: '',
    state: '',
    country: '',
    occupation: '',
    education: '',
  })
  const [availableInterests, setAvailableInterests] = useState([])
  const [selectedInterestIds, setSelectedInterestIds] = useState([])
  const [status, setStatus] = useState({ type: 'info', message: '' })
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    if (profile) {
      setForm({
        firstName: profile.firstName || '',
        lastName: profile.lastName || '',
        username: profile.username || '',
        bio: profile.bio || '',
        city: profile.city || '',
        state: profile.state || '',
        country: profile.country || '',
        occupation: profile.occupation || '',
        education: profile.education || '',
      })
      setSelectedInterestIds((profile.interests || []).map((i) => i.id))
    }
  }, [profile])

  useEffect(() => {
    userService
      .getAvailableInterests()
      .then((res) => setAvailableInterests(res.data || []))
      .catch(() => {})
  }, [])

  const handleSaveProfile = async (e) => {
    e.preventDefault()
    setSaving(true)
    setStatus({ type: 'info', message: '' })
    try {
      await userService.updateProfile(form)
      await refreshProfile()
      setStatus({ type: 'success', message: 'Profile updated successfully.' })
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    } finally {
      setSaving(false)
    }
  }

  const toggleInterest = (id) => {
    setSelectedInterestIds((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    )
  }

  const handleSaveInterests = async () => {
    setSaving(true)
    try {
      await userService.updateInterests(selectedInterestIds)
      await refreshProfile()
      setStatus({ type: 'success', message: 'Interests updated successfully.' })
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    } finally {
      setSaving(false)
    }
  }

  const handleAvatarUpload = async (e) => {
    const file = e.target.files?.[0]
    if (!file) return
    try {
      await userService.uploadProfilePicture(file)
      await refreshProfile()
      setStatus({ type: 'success', message: 'Profile picture uploaded.' })
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  return (
    <div className="space-y-8">
      <StatusAlert type={status.type} message={status.message} />

      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <div className="flex flex-wrap items-center gap-5">
          <div className="relative h-20 w-20 overflow-hidden rounded-full bg-indigo-100">
            {profile?.profilePicture ? (
              <img src={profile.profilePicture} alt="Avatar" className="h-full w-full object-cover" />
            ) : (
              <div className="flex h-full w-full items-center justify-center text-2xl font-bold text-indigo-700">
                {profile?.firstName?.[0] || 'N'}
              </div>
            )}
          </div>
          <div>
            <h1 className="text-xl font-bold text-slate-900">
              {profile?.firstName} {profile?.lastName}
            </h1>
            <p className="text-sm text-slate-500">{profile?.email}</p>
            <label className="mt-2 inline-flex cursor-pointer items-center gap-1.5 rounded-lg border border-slate-300 px-3 py-1.5 text-xs font-medium text-slate-700 hover:bg-slate-50">
              <Camera className="h-3.5 w-3.5" />
              Upload Profile Photo
              <input type="file" accept="image/*" className="hidden" onChange={handleAvatarUpload} />
            </label>
          </div>
        </div>

        <form onSubmit={handleSaveProfile} className="mt-6 grid gap-4 sm:grid-cols-2">
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">First Name</label>
            <input
              type="text"
              value={form.firstName}
              onChange={(e) => setForm({ ...form, firstName: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Last Name</label>
            <input
              type="text"
              value={form.lastName}
              onChange={(e) => setForm({ ...form, lastName: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Username</label>
            <input
              type="text"
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Occupation</label>
            <input
              type="text"
              value={form.occupation}
              onChange={(e) => setForm({ ...form, occupation: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">City</label>
            <input
              type="text"
              value={form.city}
              onChange={(e) => setForm({ ...form, city: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div>
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Country</label>
            <input
              type="text"
              value={form.country}
              onChange={(e) => setForm({ ...form, country: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div className="sm:col-span-2">
            <label className="mb-1 block text-xs font-semibold uppercase text-slate-500">Bio</label>
            <textarea
              rows={3}
              value={form.bio}
              onChange={(e) => setForm({ ...form, bio: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2 text-sm"
            />
          </div>
          <div className="sm:col-span-2">
            <button
              type="submit"
              disabled={saving}
              className="rounded-xl bg-indigo-600 px-5 py-2.5 text-sm font-semibold text-white hover:bg-indigo-700 disabled:opacity-60"
            >
              Save Profile
            </button>
          </div>
        </form>
      </div>

      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-semibold text-slate-900">Your Interests</h2>
        <p className="mt-1 text-sm text-slate-600">Select hobbies and activities you want to connect over</p>

        <div className="mt-4 flex flex-wrap gap-2.5">
          {availableInterests.map((item) => {
            const active = selectedInterestIds.includes(item.id)
            return (
              <button
                key={item.id}
                type="button"
                onClick={() => toggleInterest(item.id)}
                className={`rounded-full border px-4 py-1.5 text-sm font-medium transition ${
                  active
                    ? 'border-indigo-600 bg-indigo-600 text-white'
                    : 'border-slate-300 bg-white text-slate-700 hover:border-indigo-300'
                }`}
              >
                {item.name}
              </button>
            )
          })}
        </div>

        <button
          type="button"
          onClick={handleSaveInterests}
          disabled={saving}
          className="mt-5 rounded-xl bg-slate-900 px-5 py-2.5 text-sm font-semibold text-white hover:bg-slate-800 disabled:opacity-60"
        >
          Update Interests
        </button>
      </div>
    </div>
  )
}
