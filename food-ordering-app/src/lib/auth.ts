import { emitCartUpdated, emitSessionUpdated } from './events'

export function saveSession(payload: {
  token: string
  email: string
  role: string
  fullName: string
}) {
  localStorage.setItem('fo_token', payload.token)
  localStorage.setItem('fo_email', payload.email)
  localStorage.setItem('fo_role', payload.role)
  localStorage.setItem('fo_name', payload.fullName)
  emitSessionUpdated()
  emitCartUpdated()
}

export function clearSession() {
  localStorage.removeItem('fo_token')
  localStorage.removeItem('fo_email')
  localStorage.removeItem('fo_role')
  localStorage.removeItem('fo_name')
  emitSessionUpdated()
  emitCartUpdated()
}

export function getSession() {
  const token = localStorage.getItem('fo_token')
  const email = localStorage.getItem('fo_email')
  const role = localStorage.getItem('fo_role')
  const fullName = localStorage.getItem('fo_name')
  if (!token || !email || !role || !fullName) return null
  return { token, email, role, fullName }
}

export function isLoggedIn() {
  return !!localStorage.getItem('fo_token')
}
