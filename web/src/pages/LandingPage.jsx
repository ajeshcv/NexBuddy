import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { ArrowRight, CheckCircle2, ShieldCheck, Sparkles, Users } from 'lucide-react'
import { ACTIVITY_CATEGORIES } from '../utils/constants'
import { authService } from '../services/authService'
import { useAuth } from '../context/AuthContext'

export default function LandingPage() {
  const { isAuthenticated } = useAuth()
  const [apiStatus, setApiStatus] = useState('checking')

  useEffect(() => {
    authService
      .health()
      .then(() => setApiStatus('online'))
      .catch(() => setApiStatus('offline'))
  }, [])

  return (
    <div className="space-y-14">
      <section className="rounded-3xl bg-gradient-to-br from-indigo-600 via-indigo-700 to-violet-700 px-6 py-14 text-white shadow-xl sm:px-12">
        <div className="mx-auto max-w-3xl text-center">
          <div className="mb-5 inline-flex items-center gap-2 rounded-full bg-white/15 px-3.5 py-1.5 text-xs font-medium backdrop-blur">
            <span
              className={`h-2 w-2 rounded-full ${
                apiStatus === 'online'
                  ? 'bg-emerald-400'
                  : apiStatus === 'offline'
                  ? 'bg-rose-400'
                  : 'bg-amber-300'
              }`}
            />
            Backend API: {apiStatus === 'online' ? 'Connected' : apiStatus === 'offline' ? 'Offline' : 'Checking...'}
          </div>

          <h1 className="text-4xl font-extrabold tracking-tight sm:text-5xl">
            One Platform. Endless Connections.
          </h1>
          <p className="mt-4 text-base text-indigo-100 sm:text-lg">
            Connect with like-minded people nearby through shared interests, hobbies, local communities, and real-world events.
          </p>

          <div className="mt-8 flex flex-wrap items-center justify-center gap-4">
            {isAuthenticated ? (
              <Link
                to="/dashboard"
                className="inline-flex items-center gap-2 rounded-xl bg-white px-6 py-3 text-sm font-semibold text-indigo-700 shadow-md transition hover:bg-indigo-50"
              >
                Go to Dashboard <ArrowRight className="h-4 w-4" />
              </Link>
            ) : (
              <>
                <Link
                  to="/register"
                  className="inline-flex items-center gap-2 rounded-xl bg-white px-6 py-3 text-sm font-semibold text-indigo-700 shadow-md transition hover:bg-indigo-50"
                >
                  Create Free Account <ArrowRight className="h-4 w-4" />
                </Link>
                <Link
                  to="/login"
                  className="rounded-xl border border-white/30 bg-white/10 px-6 py-3 text-sm font-semibold text-white transition hover:bg-white/20"
                >
                  Sign In
                </Link>
              </>
            )}
          </div>
        </div>
      </section>

      <section className="grid gap-6 md:grid-cols-3">
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <Sparkles className="h-8 w-8 text-indigo-600" />
          <h3 className="mt-3 text-lg font-semibold text-slate-900">Interest-Based Matching</h3>
          <p className="mt-1.5 text-sm text-slate-600">
            Select your hobbies and availability to discover compatible buddies near you.
          </p>
        </div>
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <Users className="h-8 w-8 text-indigo-600" />
          <h3 className="mt-3 text-lg font-semibold text-slate-900">Communities & Events</h3>
          <p className="mt-1.5 text-sm text-slate-600">
            Join local groups, organize weekend meetups, and participate in shared activities.
          </p>
        </div>
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          <ShieldCheck className="h-8 w-8 text-indigo-600" />
          <h3 className="mt-3 text-lg font-semibold text-slate-900">Verified & Private</h3>
          <p className="mt-1.5 text-sm text-slate-600">
            JWT-secured authentication, email verification, and granular privacy controls.
          </p>
        </div>
      </section>

      <section>
        <div className="mb-6 flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold text-slate-900">Explore Activity Categories</h2>
            <p className="text-sm text-slate-600">Find your next partner for any experience</p>
          </div>
          <span className="inline-flex items-center gap-1 text-xs font-medium text-emerald-700">
            <CheckCircle2 className="h-4 w-4" /> Ready for matching
          </span>
        </div>
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {ACTIVITY_CATEGORIES.map((cat) => (
            <div
              key={cat.name}
              className="rounded-2xl border border-slate-200 bg-white p-5 transition hover:border-indigo-200 hover:shadow-md"
            >
              <div className="text-2xl">{cat.icon}</div>
              <h3 className="mt-2 font-semibold text-slate-900">{cat.name}</h3>
              <p className="mt-1 text-sm text-slate-600">{cat.description}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}
