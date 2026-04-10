import { Receipt } from 'lucide-react'
import { useEffect, useState } from 'react'
import { getUserOrders } from '../lib/api'
import type { Order } from '../types'

export function OrderHistoryPage() {
  const [orders, setOrders] = useState<Order[]>([])
  const [error, setError] = useState('')

  useEffect(() => {
    let mounted = true
    getUserOrders()
      .then((data) => {
        if (mounted) setOrders(data)
      })
      .catch((exception) => {
        if (mounted) setError((exception as Error).message)
      })
    return () => {
      mounted = false
    }
  }, [])

  return (
    <div className="space-y-6 text-left">
      <header className="rounded-3xl border border-zinc-200 bg-white p-6 shadow-sm">
        <div className="flex items-center gap-3">
          <span className="grid size-11 place-items-center rounded-2xl bg-orange-50 text-orange-600">
            <Receipt className="size-5" />
          </span>
          <div>
            <h1 className="text-xl font-semibold text-zinc-900">Order History</h1>
            <p className="mt-1 text-sm text-zinc-600">All your previous orders in one place.</p>
          </div>
        </div>
      </header>

      {error ? (
        <div className="rounded-2xl border border-rose-200 bg-rose-50 p-4 text-sm text-rose-700">
          {error}
        </div>
      ) : null}

      <div className="grid grid-cols-1 gap-4">
        {orders.length === 0 ? (
          <div className="rounded-2xl border border-dashed border-zinc-200 bg-zinc-50 p-6 text-sm text-zinc-600">
            No orders yet.
          </div>
        ) : orders.map((o) => (
          <div key={o.id} className="rounded-3xl border border-zinc-200 bg-white p-5 shadow-sm">
            <div className="flex items-start justify-between gap-4">
              <div>
                <div className="text-sm font-semibold text-zinc-900">{o.restaurantName}</div>
                <div className="mt-1 text-xs text-zinc-500">
                  Placed: {new Date(o.placedAt).toLocaleString()}
                </div>
              </div>
              <span
                className={
                  o.status === 'DELIVERED'
                    ? 'rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700'
                    : 'rounded-full bg-amber-50 px-3 py-1 text-xs font-semibold text-amber-700'
                }
              >
                {o.status}
              </span>
            </div>

            <div className="mt-4 flex items-center justify-between text-sm">
              <div className="text-zinc-600">
                Total: <span className="font-semibold text-zinc-900">₹{o.totalAmount}</span>
              </div>
              <button
                type="button"
                className="rounded-2xl bg-zinc-900 px-4 py-2 text-xs font-semibold text-white hover:bg-zinc-800 transition"
              >
                Reorder
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

