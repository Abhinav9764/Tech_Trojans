import { Link, Outlet, useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import { Shield } from 'lucide-react'
import { clearSession, getSession } from '../lib/auth'

export function AdminLayout() {
  const navigate = useNavigate()

  useEffect(() => {
    const session = getSession()
    if (!session || session.role !== 'ADMIN') navigate('/admin/login', { replace: true })
  }, [navigate])

  const session = getSession()
  if (!session || session.role !== 'ADMIN') {
    return (
      <div className="grid min-h-dvh place-items-center bg-slate-950 text-sm text-slate-500">
        Verifying administrator access…
      </div>
    )
  }

  const handleLogout = () => {
    clearSession()
    navigate('/admin/login')
  }

  return (
    <div className="min-h-dvh bg-slate-950 text-slate-100">
      <header className="border-b border-slate-800 bg-slate-900/80 backdrop-blur">
        <div className="mx-auto flex h-14 max-w-6xl items-center justify-between px-4 sm:px-6">
          <div className="flex items-center gap-3">
            <span className="grid size-9 place-items-center rounded-lg bg-indigo-500/20 text-indigo-300">
              <Shield className="size-5" aria-hidden />
            </span>
            <div>
              <div className="text-sm font-semibold text-white">Administration</div>
              <div className="text-xs text-slate-500">Food ordering console</div>
            </div>
          </div>
          <nav className="flex items-center gap-3">
            <Link
              to="/"
              className="rounded-lg px-3 py-1.5 text-sm text-slate-400 transition hover:bg-slate-800 hover:text-white"
            >
              View storefront
            </Link>
            <button
              type="button"
              onClick={handleLogout}
              className="rounded-lg bg-slate-800 px-4 py-2 text-sm font-medium text-white transition hover:bg-slate-700"
            >
              Log out
            </button>
          </nav>
        </div>
      </header>
      <main className="mx-auto max-w-6xl px-4 py-8 sm:px-6">
        <Outlet />
      </main>
    </div>
  )
}
