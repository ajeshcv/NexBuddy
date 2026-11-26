import { useCallback, useEffect, useState } from 'react'
import { adminService } from '../services/adminService'
import { useAuth } from '../context/AuthContext'
import StatusAlert from '../components/StatusAlert'

export default function AdminPage() {
  const { user, profile } = useAuth()
  const [stats, setStats] = useState(null)
  const [users, setUsers] = useState([])
  const [status, setStatus] = useState({ type: 'info', message: '' })

  const role = profile?.role || user?.role

  const loadData = useCallback(async () => {
    try {
      const [dashRes, usersRes] = await Promise.all([
        adminService.getDashboard(),
        adminService.getUsers(),
      ])
      setStats(dashRes.data)
      setUsers(usersRes.data || [])
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }, [])

  useEffect(() => {
    if (role === 'ADMIN') {
      loadData()
    }
  }, [role, loadData])

  if (role !== 'ADMIN') {
    return (
      <div className="mx-auto max-w-lg rounded-2xl border border-rose-200 bg-rose-50 p-6 text-center text-rose-800">
        Access Denied — Administrator role required.
      </div>
    )
  }

  const toggleStatus = async (target) => {
    try {
      if (target.accountStatus === 'SUSPENDED') {
        await adminService.activateUser(target.id)
        setStatus({ type: 'success', message: `Activated ${target.email}` })
      } else {
        await adminService.suspendUser(target.id)
        setStatus({ type: 'success', message: `Suspended ${target.email}` })
      }
      await loadData()
    } catch (err) {
      setStatus({ type: 'error', message: err.message })
    }
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-bold text-slate-900">Admin Dashboard</h1>
        <p className="text-sm text-slate-600">Platform metrics and user moderation</p>
      </div>

      <StatusAlert type={status.type} message={status.message} />

      {stats && (
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-5">
          {[
            { label: 'Total Users', value: stats.totalUsers },
            { label: 'Active Users', value: stats.activeUsers },
            { label: 'Pending Verification', value: stats.pendingVerificationUsers },
            { label: 'Suspended Users', value: stats.suspendedUsers },
            { label: 'Interests', value: stats.totalInterests },
          ].map((card) => (
            <div key={card.label} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
              <div className="text-xs font-semibold uppercase text-slate-500">{card.label}</div>
              <div className="mt-2 text-2xl font-bold text-slate-900">{card.value}</div>
            </div>
          ))}
        </div>
      )}

      <div className="overflow-x-auto rounded-2xl border border-slate-200 bg-white shadow-sm">
        <table className="w-full text-left text-sm">
          <thead className="border-b border-slate-200 bg-slate-50 text-xs font-semibold uppercase text-slate-500">
            <tr>
              <th className="px-4 py-3">Name</th>
              <th className="px-4 py-3">Username</th>
              <th className="px-4 py-3">Email</th>
              <th className="px-4 py-3">Role</th>
              <th className="px-4 py-3">Status</th>
              <th className="px-4 py-3 text-right">Action</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-100">
            {users.map((u) => (
              <tr key={u.id}>
                <td className="px-4 py-3 font-medium text-slate-900">
                  {u.firstName} {u.lastName}
                </td>
                <td className="px-4 py-3 text-slate-600">@{u.username}</td>
                <td className="px-4 py-3 text-slate-600">{u.email}</td>
                <td className="px-4 py-3">
                  <span className="rounded-full bg-indigo-50 px-2.5 py-0.5 text-xs font-semibold text-indigo-700">
                    {u.role}
                  </span>
                </td>
                <td className="px-4 py-3 text-slate-700">{u.accountStatus}</td>
                <td className="px-4 py-3 text-right">
                  {u.role !== 'ADMIN' && (
                    <button
                      onClick={() => toggleStatus(u)}
                      className={`rounded-lg px-3 py-1 text-xs font-semibold ${
                        u.accountStatus === 'SUSPENDED'
                          ? 'bg-emerald-50 text-emerald-700 hover:bg-emerald-100'
                          : 'bg-rose-50 text-rose-700 hover:bg-rose-100'
                      }`}
                    >
                      {u.accountStatus === 'SUSPENDED' ? 'Activate' : 'Suspend'}
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
