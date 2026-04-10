import { Shield } from 'lucide-react'
import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { signinAdmin } from '../lib/api'
import { getSession, saveSession } from '../lib/auth'

export function AdminLoginPage() {
  const navigate = useNavigate()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const session = getSession()
    if (session?.role === 'ADMIN') navigate('/admin', { replace: true })
  }, [navigate])

  async function handleSubmit(event: React.FormEvent) {
    event.preventDefault()
    setError('')
    setLoading(true)
    try {
      const response = await signinAdmin({ email, password })
      saveSession(response)
      navigate('/admin')
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="relative min-h-dvh overflow-hidden bg-slate-950">
      <div
        className="pointer-events-none absolute inset-0 opacity-40"
        style={{
          backgroundImage:
            'radial-gradient(circle at 20% 20%, rgb(79 70 229 / 0.35), transparent 45%), radial-gradient(circle at 80% 60%, rgb(14 165 233 / 0.2), transparent 40%)',
        }}
      />
      <div className="relative mx-auto flex min-h-dvh max-w-md flex-col justify-center px-4 py-12">
        <div className="rounded-2xl border border-slate-800 bg-slate-900/90 p-8 shadow-2xl shadow-black/40 backdrop-blur">
          <div className="flex items-center gap-3">
            <span className="grid size-12 place-items-center rounded-xl bg-indigo-500 text-white shadow-lg shadow-indigo-500/30">
              <Shield className="size-6" aria-hidden />
            </span>
            <div>
              <h1 className="text-lg font-semibold tracking-tight text-white">Administrator sign in</h1>
              <p className="mt-0.5 text-sm text-slate-400">Restricted access — staff only</p>
            </div>
          </div>

          {error ? (
            <p
              className="mt-6 rounded-lg border border-rose-500/30 bg-rose-950/50 px-3 py-2 text-sm text-rose-200"
              role="alert"
            >
              {error}
            </p>
          ) : null}

          <form className="mt-6 space-y-4" onSubmit={handleSubmit}>
            <label className="block">
              <span className="text-xs font-medium uppercase tracking-wide text-slate-500">Work email</span>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                autoComplete="username"
                className="mt-2 w-full rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-white outline-none ring-indigo-500/0 transition placeholder:text-slate-600 focus:border-indigo-500 focus:ring-4 focus:ring-indigo-500/20"
                placeholder="admin@foodapp.com"
                required
              />
            </label>
            <label className="block">
              <span className="text-xs font-medium uppercase tracking-wide text-slate-500">Password</span>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
                className="mt-2 w-full rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-white outline-none transition focus:border-indigo-500 focus:ring-4 focus:ring-indigo-500/20"
                placeholder="••••••••"
                required
              />
            </label>
            <button
              type="submit"
              disabled={loading}
              className="mt-2 w-full rounded-xl bg-indigo-600 py-3 text-sm font-semibold text-white shadow-lg shadow-indigo-600/25 transition hover:bg-indigo-500 disabled:opacity-50"
            >
              {loading ? 'Signing in…' : 'Enter console'}
            </button>
          </form>

          <p className="mt-8 border-t border-slate-800 pt-6 text-center text-sm text-slate-500">
            Customer or restaurant?{' '}
            <Link to="/login" className="font-medium text-indigo-400 hover:text-indigo-300">
              Use the public login
            </Link>
          </p>
        </div>
      </div>
    </div>
  )
}
