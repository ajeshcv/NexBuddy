import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/authService'
import StatusAlert from '../components/StatusAlert'

export default function ForgotPasswordPage() {
  const navigate = useNavigate()
  const [step, setStep] = useState(1)
  const [email, setEmail] = useState('')
  const [otp, setOtp] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [status, setStatus] = useState({ type: 'info', message: '' })

  const sendOtp = async (e) => {
    e.preventDefault()
    try {
      const res = await authService.forgotPassword({ email })
      const devOtp = res?.data?.devOtp
      if (devOtp) {
        setOtp(devOtp)
        setStatus({ type: 'success', message: `Dev mode: Reset OTP is ${devOtp}` })
      } else {
        setStatus({ type: 'success', message: res.message })
      }
      setStep(2)
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  const verifyOtp = async (e) => {
    e.preventDefault()
    try {
      const res = await authService.verifyResetOtp({ email, otp })
      setStatus({ type: 'success', message: res.message })
      setStep(3)
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  const resetPassword = async (e) => {
    e.preventDefault()
    try {
      const res = await authService.resetPassword({ email, newPassword, confirmPassword })
      setStatus({ type: 'success', message: res.message })
      setTimeout(() => navigate('/login'), 1200)
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  return (
    <div className="mx-auto max-w-md rounded-2xl border border-slate-200 bg-white p-8 shadow-sm">
      <h1 className="text-2xl font-bold text-slate-900">Reset Password</h1>
      <p className="mt-1 text-sm text-slate-600">Step {step} of 3</p>

      <div className="mt-6 space-y-4">
        <StatusAlert type={status.type} message={status.message} />

        {step === 1 && (
          <form onSubmit={sendOtp} className="space-y-4">
            <input
              type="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
            />
            <button className="w-full rounded-xl bg-indigo-600 py-2.5 text-sm font-semibold text-white hover:bg-indigo-700">
              Send Reset OTP
            </button>
          </form>
        )}

        {step === 2 && (
          <form onSubmit={verifyOtp} className="space-y-4">
            <input
              type="text"
              required
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              placeholder="Enter 6-digit OTP"
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
            />
            <button className="w-full rounded-xl bg-indigo-600 py-2.5 text-sm font-semibold text-white hover:bg-indigo-700">
              Verify OTP
            </button>
          </form>
        )}

        {step === 3 && (
          <form onSubmit={resetPassword} className="space-y-4">
            <input
              type="password"
              required
              minLength={8}
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              placeholder="New password"
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
            />
            <input
              type="password"
              required
              minLength={8}
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Confirm new password"
              className="w-full rounded-xl border border-slate-300 px-3.5 py-2.5 text-sm"
            />
            <button className="w-full rounded-xl bg-indigo-600 py-2.5 text-sm font-semibold text-white hover:bg-indigo-700">
              Update Password
            </button>
          </form>
        )}
      </div>

      <p className="mt-6 text-center text-sm text-slate-600">
        Remembered your password?{' '}
        <Link to="/login" className="font-semibold text-indigo-600 hover:underline">
          Back to login
        </Link>
      </p>
    </div>
  )
}
