import { LogIn } from 'lucide-react'
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { signin, signup } from '../lib/api'
import { saveSession } from '../lib/auth'
import type { LoginRole, SignupRole } from '../types'

export function LoginPage() {
  const navigate = useNavigate()
  const [mode, setMode] = useState<'signin' | 'signup'>('signin')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [loginRole, setLoginRole] = useState<LoginRole>('USER')
  const [loginEmail, setLoginEmail] = useState('')
  const [loginPassword, setLoginPassword] = useState('')
  const [signupRole, setSignupRole] = useState<SignupRole>('USER')
  const [signupName, setSignupName] = useState('')
  const [signupEmailValue, setSignupEmailValue] = useState('')
  const [signupPasswordValue, setSignupPasswordValue] = useState('')
  const [signupPhone, setSignupPhone] = useState('')
  const [signupRestaurant, setSignupRestaurant] = useState('')

  async function handleSignin(event: React.FormEvent) {
    event.preventDefault()
    setError('')
    setLoading(true)
    try {
      const response = await signin(loginRole, {
        email: loginEmail,
        password: loginPassword,
      })
      saveSession(response)
      navigate('/')
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  async function handleSignup(event: React.FormEvent) {
    event.preventDefault()
    setError('')
    setLoading(true)
    try {
      await signup({
        fullName: signupName,
        email: signupEmailValue,
        password: signupPasswordValue,
        phoneNumber: signupPhone,
        role: signupRole,
        restaurantName: signupRole === 'RESTAURANT' ? signupRestaurant : '',
      })
      setMode('signin')
      setLoginEmail(signupEmailValue)
      setLoginPassword(signupPasswordValue)
      setLoginRole(signupRole)
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="mx-auto max-w-lg rounded-3xl border border-zinc-200 bg-white p-8 text-left shadow-sm">
      <div className="flex items-center gap-3">
        <span className="grid size-11 place-items-center rounded-2xl bg-orange-50 text-orange-600">
          <LogIn className="size-5" />
        </span>
        <div>
          <h1 className="text-xl font-semibold text-zinc-900">Account</h1>
          <p className="mt-1 text-sm text-zinc-600">Sign in as a customer or restaurant partner.</p>
        </div>
      </div>

      <div className="mt-6 flex gap-2">
        <button
          type="button"
          onClick={() => setMode('signin')}
          className={`rounded-xl px-4 py-2 text-sm font-semibold ${mode === 'signin' ? 'bg-zinc-900 text-white' : 'bg-zinc-100 text-zinc-700'}`}
        >
          Sign in
        </button>
        <button
          type="button"
          onClick={() => setMode('signup')}
          className={`rounded-xl px-4 py-2 text-sm font-semibold ${mode === 'signup' ? 'bg-zinc-900 text-white' : 'bg-zinc-100 text-zinc-700'}`}
        >
          Sign up
        </button>
      </div>

      {error ? <p className="mt-4 rounded-xl bg-rose-50 p-3 text-sm text-rose-700">{error}</p> : null}

      {mode === 'signin' ? (
        <form className="mt-6 space-y-4" onSubmit={handleSignin}>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Role</div>
            <select
              value={loginRole}
              onChange={(event) => setLoginRole(event.target.value as LoginRole)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
            >
              <option value="USER">Customer</option>
              <option value="RESTAURANT">Restaurant</option>
            </select>
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Email</div>
            <input
              value={loginEmail}
              onChange={(event) => setLoginEmail(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
              placeholder="you@example.com"
              required
            />
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Password</div>
            <input
              type="password"
              value={loginPassword}
              onChange={(event) => setLoginPassword(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
              placeholder="••••••••"
              required
            />
          </label>
          <button
            type="submit"
            disabled={loading}
            className="mt-2 inline-flex w-full items-center justify-center rounded-2xl bg-zinc-900 px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-zinc-800 transition disabled:opacity-60"
          >
            {loading ? 'Signing in...' : 'Continue'}
          </button>
          <p className="mt-4 text-center text-sm text-zinc-500">
            Platform admin?{' '}
            <Link to="/admin/login" className="font-medium text-orange-600 hover:text-orange-700">
              Use the admin console
            </Link>
          </p>
        </form>
      ) : (
        <form className="mt-6 space-y-4" onSubmit={handleSignup}>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Full name</div>
            <input
              value={signupName}
              onChange={(event) => setSignupName(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
              required
            />
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Role</div>
            <select
              value={signupRole}
              onChange={(event) => setSignupRole(event.target.value as SignupRole)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
            >
              <option value="USER">Customer</option>
              <option value="RESTAURANT">Restaurant</option>
            </select>
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Email</div>
            <input
              value={signupEmailValue}
              onChange={(event) => setSignupEmailValue(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
              required
            />
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Password</div>
            <input
              type="password"
              value={signupPasswordValue}
              onChange={(event) => setSignupPasswordValue(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
              required
            />
          </label>
          <label className="block">
            <div className="text-sm font-medium text-zinc-700">Phone number</div>
            <input
              value={signupPhone}
              onChange={(event) => setSignupPhone(event.target.value)}
              className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
            />
          </label>
          {signupRole === 'RESTAURANT' ? (
            <label className="block">
              <div className="text-sm font-medium text-zinc-700">Restaurant name</div>
              <input
                value={signupRestaurant}
                onChange={(event) => setSignupRestaurant(event.target.value)}
                className="mt-2 w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
                required
              />
            </label>
          ) : null}
          <button
            type="submit"
            disabled={loading}
            className="mt-2 inline-flex w-full items-center justify-center rounded-2xl bg-zinc-900 px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-zinc-800 transition disabled:opacity-60"
          >
            {loading ? 'Creating account...' : 'Create account'}
          </button>
        </form>
      )}
    </div>
  )
}

