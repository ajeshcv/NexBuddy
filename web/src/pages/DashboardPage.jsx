import { Link } from 'react-router-dom'
import { Calendar, MessageCircle, Sparkles, Users, ArrowRight } from 'lucide-react'
import { useAuth } from '../context/AuthContext'

export default function DashboardPage() {
  const { profile, user } = useAuth()

  const interests = profile?.interests || []
  const fullName = profile ? `${profile.firstName} ${profile.lastName}` : user?.name || 'Buddy'

  const modules = [
    {
      title: 'Buddy Matching',
      desc: 'Discover nearby people matching your interests and availability.',
      icon: Sparkles,
      status: 'Phase 2',
    },
    {
      title: 'Communities',
      desc: 'Join or create local interest-based communities and discussions.',
      icon: Users,
      status: 'Phase 2',
    },
    {
      title: 'Events & Meetups',
      desc: 'Create or RSVP to local activities, treks, matches, and meetups.',
      icon: Calendar,
      status: 'Phase 2',
    },
    {
      title: 'Real-Time Chat',
      desc: 'WebSocket-powered 1-to-1 and group messaging with attachments.',
      icon: MessageCircle,
      status: 'Phase 2',
    },
  ]

  return (
    <div className="space-y-8">
      <div className="flex flex-col justify-between gap-4 rounded-2xl border border-slate-200 bg-white p-6 shadow-sm sm:flex-row sm:items-center">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">Hello, {fullName} 👋</h1>
          <p className="mt-1 text-sm text-slate-600">
            @{profile?.username || user?.username} · {profile?.city || 'Location not set'}
          </p>
        </div>
        <Link
          to="/profile"
          className="inline-flex items-center gap-2 rounded-xl bg-indigo-600 px-4 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700"
        >
          Manage Profile & Interests <ArrowRight className="h-4 w-4" />
        </Link>
      </div>

      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <div className="flex items-center justify-between">
          <h2 className="text-lg font-semibold text-slate-900">Your Interests ({interests.length})</h2>
          <Link to="/profile" className="text-sm font-medium text-indigo-600 hover:underline">
            Edit interests
          </Link>
        </div>
        {interests.length > 0 ? (
          <div className="mt-4 flex flex-wrap gap-2">
            {interests.map((interest) => (
              <span
                key={interest.id}
                className="rounded-full bg-indigo-50 px-3.5 py-1.5 text-xs font-semibold text-indigo-700"
              >
                {interest.name}
              </span>
            ))}
          </div>
        ) : (
          <p className="mt-3 text-sm text-slate-500">
            No interests selected yet. Add interests on your profile to power Buddy Matching.
          </p>
        )}
      </div>

      <div>
        <h2 className="mb-4 text-lg font-semibold text-slate-900">Platform Roadmap</h2>
        <div className="grid gap-4 sm:grid-cols-2">
          {modules.map((mod) => {
            const Icon = mod.icon
            return (
              <div key={mod.title} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                <div className="flex items-center justify-between">
                  <Icon className="h-6 w-6 text-indigo-600" />
                  <span className="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600">
                    {mod.status}
                  </span>
                </div>
                <h3 className="mt-3 font-semibold text-slate-900">{mod.title}</h3>
                <p className="mt-1 text-sm text-slate-600">{mod.desc}</p>
              </div>
            )
          })}
        </div>
      </div>
    </div>
  )
}
