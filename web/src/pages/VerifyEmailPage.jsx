import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { authService } from '../services/authService'
import StatusAlert from '../components/StatusAlert'

export default function VerifyEmailPage() {
  const location = useLocation()
  const navigate = useNavigate()
  const initialDevOtp = location.state?.devOtp || ''
  const [email, setEmail] = useState(location.state?.email || '')
  const [otp, setOtp] = useState(initialDevOtp)
  const [status, setStatus] = useState({
    type: initialDevOtp ? 'success' : 'info',
    message: initialDevOtp
      ? `Dev mode (SMTP not configured): Your OTP is ${initialDevOtp}`
      : 'Check your email inbox for the 6-digit OTP, or click Resend OTP.',
  })
  const [submitting, setSubmitting] = useState(false)

  const handleVerify = async (e) => {
    e.preventDefault()
    setSubmitting(true)
    try {
      const res = await authService.verifyEmail({ email, otp })
      setStatus({ type: 'success', message: res.message || 'Email verified! Redirecting to login...' })
      setTimeout(() => navigate('/login'), 1200)
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    } finally {
      setSubmitting(false)
    }
  }

  const handleResend = async () => {
    if (!email) {
      setStatus({ type: 'error', message: 'Please enter your email first.' })
      return
    }
    try {
      const res = await authService.resendOtp({ email })
      const devOtp = res?.data?.devOtp
      if (devOtp) {
        setOtp(devOtp)
        setStatus({ type: 'success', message: `Dev mode: New OTP is ${devOtp}` })
      } else {
        setStatus({ type: 'success', message: res.message || 'OTP sent to your email.' })
      }
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  return (
    <div className="mx-auto max-w-md rounded-2xl border border-slate-200 bg-white p-8 shadow-sm">
      <h1 className="text-2xl font-bold text-slate-900">Verify your email</h1>
      <p className="mt-1 text-sm text-slate-600">Enter the 6-digit verification code</p>

      <form onSubmit={handleVerify} className="mt-6 space-y-4">
        <StatusAlert type={status.type} message={status.message} />

        <div>
          <label className="mb-1 block text-sm font-medium text-slate-700">Email</label>
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-200"
          />
        </div>

        <div>
          <label className="mb-1 block text-sm font-medium text-slate-700">OTP Code</label>
          <input
            type="text"
            required
            maxLength={6}
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            placeholder="123456"
            className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-center text-lg tracking-widest focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-200"
          />
        </div>

        <button
          type="submit"
          disabled={submitting}
          className="w-full rounded-xl bg-indigo-600 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700 disabled:opacity-60"
        >
          {submitting ? 'Verifying...' : 'Verify Email'}
        </button>

        <button
          type="button"
          onClick={handleResend}
          className="w-full rounded-xl border border-slate-300 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-50"
        >
          Resend OTP
        </button>
      </form>
    </div>
  )
}
