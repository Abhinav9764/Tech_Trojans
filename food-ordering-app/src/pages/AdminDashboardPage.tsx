import { Pencil, Trash2 } from 'lucide-react'
import { useCallback, useEffect, useMemo, useState } from 'react'
import { deleteAdminUser, listAdminUsers, updateAdminUser } from '../lib/api'
import type { AdminUser, AdminUserUpdatePayload } from '../types'

type TabFilter = 'ALL' | 'USER' | 'RESTAURANT' | 'ADMIN'

export function AdminDashboardPage() {
  const [users, setUsers] = useState<AdminUser[]>([])
  const [filter, setFilter] = useState<TabFilter>('ALL')
  const [loading, setLoading] = useState(true)
  const [listError, setListError] = useState('')
  const [editing, setEditing] = useState<AdminUser | null>(null)
  const [formError, setFormError] = useState('')
  const [saving, setSaving] = useState(false)
  const [deletingId, setDeletingId] = useState<number | null>(null)

  const [fullName, setFullName] = useState('')
  const [email, setEmail] = useState('')
  const [phoneNumber, setPhoneNumber] = useState('')
  const [restaurantName, setRestaurantName] = useState('')
  const [active, setActive] = useState(true)
  const [newPassword, setNewPassword] = useState('')

  const loadUsers = useCallback(async () => {
    setListError('')
    setLoading(true)
    try {
      const data = await listAdminUsers()
      setUsers(data)
    } catch (exception) {
      setListError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    void loadUsers()
  }, [loadUsers])

  const filtered = useMemo(() => {
    if (filter === 'ALL') return users
    return users.filter((u) => u.role === filter)
  }, [users, filter])

  const openEdit = (user: AdminUser) => {
    setEditing(user)
    setFormError('')
    setFullName(user.fullName)
    setEmail(user.email)
    setPhoneNumber(user.phoneNumber ?? '')
    setRestaurantName(user.restaurantName ?? '')
    setActive(user.active)
    setNewPassword('')
  }

  const closeEdit = () => {
    setEditing(null)
    setFormError('')
    setNewPassword('')
  }

  const handleSaveEdit = async (event: React.FormEvent) => {
    event.preventDefault()
    if (!editing) return
    setFormError('')
    setSaving(true)
    try {
      const payload: AdminUserUpdatePayload = {
        fullName,
        email,
        phoneNumber: phoneNumber.trim(),
        active,
      }
      if (editing.role === 'RESTAURANT') {
        payload.restaurantName = restaurantName.trim()
      }
      if (newPassword.trim()) payload.newPassword = newPassword.trim()

      const updated = await updateAdminUser(editing.id, payload)
      setUsers((prev) => prev.map((u) => (u.id === updated.id ? updated : u)))
      closeEdit()
    } catch (exception) {
      setFormError((exception as Error).message)
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (user: AdminUser) => {
    const label = user.role === 'RESTAURANT' ? user.restaurantName || user.fullName : user.fullName
    if (!window.confirm(`Permanently delete “${label}” (${user.email})? This cannot be undone.`)) return
    setDeletingId(user.id)
    try {
      await deleteAdminUser(user.id)
      setUsers((prev) => prev.filter((u) => u.id !== user.id))
      if (editing?.id === user.id) closeEdit()
    } catch (exception) {
      window.alert((exception as Error).message)
    } finally {
      setDeletingId(null)
    }
  }

  const roleBadgeClass = (role: string) => {
    if (role === 'ADMIN') return 'bg-amber-500/15 text-amber-300 ring-amber-500/30'
    if (role === 'RESTAURANT') return 'bg-violet-500/15 text-violet-200 ring-violet-500/30'
    return 'bg-slate-500/15 text-slate-300 ring-slate-500/30'
  }

  return (
    <div>
      <div className="mb-8">
        <h1 className="text-2xl font-semibold text-white">Users & restaurants</h1>
        <p className="mt-1 text-sm text-slate-400">
          View every registered account, update details or activation, or remove records (orders and menus are cleaned
          up for restaurants).
        </p>
      </div>

      <div className="mb-4 flex flex-wrap gap-2">
        {(['ALL', 'USER', 'RESTAURANT', 'ADMIN'] as const).map((tab) => (
          <button
            key={tab}
            type="button"
            onClick={() => setFilter(tab)}
            className={`rounded-lg px-4 py-2 text-sm font-medium transition ${
              filter === tab
                ? 'bg-indigo-600 text-white'
                : 'bg-slate-800 text-slate-400 hover:bg-slate-700 hover:text-white'
            }`}
          >
            {tab === 'ALL' ? 'All accounts' : tab === 'USER' ? 'Customers' : tab === 'RESTAURANT' ? 'Restaurants' : 'Admins'}
          </button>
        ))}
      </div>

      {listError ? (
        <p className="mb-4 rounded-lg border border-rose-500/30 bg-rose-950/40 px-4 py-3 text-sm text-rose-200">
          {listError}
        </p>
      ) : null}

      <div className="overflow-hidden rounded-xl border border-slate-800 bg-slate-900/50">
        <div className="overflow-x-auto">
          <table className="w-full min-w-[720px] text-left text-sm">
            <thead>
              <tr className="border-b border-slate-800 text-xs uppercase tracking-wide text-slate-500">
                <th className="px-4 py-3 font-medium">Name</th>
                <th className="px-4 py-3 font-medium">Email</th>
                <th className="px-4 py-3 font-medium">Role</th>
                <th className="px-4 py-3 font-medium">Restaurant</th>
                <th className="px-4 py-3 font-medium">Status</th>
                <th className="px-4 py-3 font-medium text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800">
              {loading ? (
                <tr>
                  <td colSpan={6} className="px-4 py-10 text-center text-slate-500">
                    Loading…
                  </td>
                </tr>
              ) : filtered.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-4 py-10 text-center text-slate-500">
                    No accounts in this view.
                  </td>
                </tr>
              ) : (
                filtered.map((user) => (
                  <tr key={user.id} className="text-slate-300">
                    <td className="px-4 py-3 font-medium text-white">{user.fullName}</td>
                    <td className="px-4 py-3 text-slate-400">{user.email}</td>
                    <td className="px-4 py-3">
                      <span className={`inline-flex rounded-md px-2 py-0.5 text-xs font-semibold ring-1 ${roleBadgeClass(user.role)}`}>
                        {user.role}
                      </span>
                    </td>
                    <td className="max-w-[200px] truncate px-4 py-3 text-slate-400">
                      {user.restaurantName ?? '—'}
                    </td>
                    <td className="px-4 py-3">
                      {user.active ? (
                        <span className="text-emerald-400">Active</span>
                      ) : (
                        <span className="text-slate-500">Disabled</span>
                      )}
                    </td>
                    <td className="px-4 py-3 text-right">
                      <div className="flex justify-end gap-1">
                        <button
                          type="button"
                          onClick={() => openEdit(user)}
                          className="rounded-lg p-2 text-slate-400 transition hover:bg-slate-800 hover:text-white"
                          aria-label={`Edit ${user.fullName}`}
                        >
                          <Pencil className="size-4" />
                        </button>
                        <button
                          type="button"
                          onClick={() => void handleDelete(user)}
                          disabled={deletingId === user.id}
                          className="rounded-lg p-2 text-slate-400 transition hover:bg-rose-950/50 hover:text-rose-300 disabled:opacity-40"
                          aria-label={`Delete ${user.fullName}`}
                        >
                          <Trash2 className="size-4" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {editing ? (
        <div
          className="fixed inset-0 z-50 flex items-end justify-center bg-black/60 p-4 sm:items-center"
          role="dialog"
          aria-modal="true"
          aria-labelledby="admin-edit-title"
        >
          <div className="max-h-[90vh] w-full max-w-lg overflow-y-auto rounded-2xl border border-slate-700 bg-slate-900 p-6 shadow-2xl">
            <h2 id="admin-edit-title" className="text-lg font-semibold text-white">
              Edit account
            </h2>
            <p className="mt-1 text-sm text-slate-500">{editing.email}</p>

            {formError ? (
              <p className="mt-4 rounded-lg bg-rose-950/50 px-3 py-2 text-sm text-rose-200">{formError}</p>
            ) : null}

            <form className="mt-6 space-y-4" onSubmit={handleSaveEdit}>
              <label className="block">
                <span className="text-xs font-medium text-slate-500">Full name</span>
                <input
                  value={fullName}
                  onChange={(e) => setFullName(e.target.value)}
                  className="mt-1 w-full rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-white outline-none focus:border-indigo-500 focus:ring-2 focus:ring-indigo-500/20"
                  required
                />
              </label>
              <label className="block">
                <span className="text-xs font-medium text-slate-500">Email</span>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="mt-1 w-full rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-white outline-none focus:border-indigo-500"
                  required
                />
              </label>
              <label className="block">
                <span className="text-xs font-medium text-slate-500">Phone</span>
                <input
                  value={phoneNumber}
                  onChange={(e) => setPhoneNumber(e.target.value)}
                  className="mt-1 w-full rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-white outline-none focus:border-indigo-500"
                />
              </label>
              {editing.role === 'RESTAURANT' ? (
                <label className="block">
                  <span className="text-xs font-medium text-slate-500">Restaurant name</span>
                  <input
                    value={restaurantName}
                    onChange={(e) => setRestaurantName(e.target.value)}
                    className="mt-1 w-full rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-white outline-none focus:border-indigo-500"
                  />
                </label>
              ) : null}
              <label className="flex cursor-pointer items-center gap-2">
                <input
                  type="checkbox"
                  checked={active}
                  onChange={(e) => setActive(e.target.checked)}
                  className="size-4 rounded border-slate-600 bg-slate-950 text-indigo-600"
                />
                <span className="text-sm text-slate-300">Account active (can sign in)</span>
              </label>
              <label className="block">
                <span className="text-xs font-medium text-slate-500">New password (optional)</span>
                <input
                  type="password"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  autoComplete="new-password"
                  placeholder="Leave blank to keep current"
                  className="mt-1 w-full rounded-lg border border-slate-700 bg-slate-950 px-3 py-2 text-sm text-white outline-none focus:border-indigo-500"
                />
              </label>
              <div className="flex gap-2 pt-2">
                <button
                  type="submit"
                  disabled={saving}
                  className="flex-1 rounded-lg bg-indigo-600 py-2.5 text-sm font-semibold text-white hover:bg-indigo-500 disabled:opacity-50"
                >
                  {saving ? 'Saving…' : 'Save changes'}
                </button>
                <button
                  type="button"
                  onClick={closeEdit}
                  className="rounded-lg border border-slate-600 px-4 py-2.5 text-sm font-medium text-slate-300 hover:bg-slate-800"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      ) : null}
    </div>
  )
}
