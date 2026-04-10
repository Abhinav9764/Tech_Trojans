import { Link } from 'react-router-dom'
import { ShoppingCart } from 'lucide-react'
import { useEffect, useState } from 'react'
import { checkoutCart, getCart, removeCartItem, updateCartItem } from '../lib/api'
import { getSession } from '../lib/auth'
import { emitCartUpdated } from '../lib/events'
import type { CartResponse } from '../types'

export function CartPage() {
  const [cart, setCart] = useState<CartResponse | null>(null)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const [loading, setLoading] = useState(false)
  const session = getSession()
  const isUser = session?.role === 'USER'

  async function loadCart() {
    if (!session) {
      setCart(null)
      setError('Please login to access cart.')
      return null
    }
    if (!isUser) {
      setCart(null)
      setError('Cart is available only for USER accounts.')
      return null
    }

    setError('')
    try {
      const data = await getCart()
      setCart(data)
      return data
    } catch (exception) {
      setError((exception as Error).message)
      return null
    }
  }

  useEffect(() => {
    loadCart()
  }, [])

  async function handleQuantity(menuItemId: number, quantity: number) {
    setLoading(true)
    setError('')
    setSuccess('')
    try {
      await updateCartItem(String(menuItemId), quantity)
      await loadCart()
      emitCartUpdated()
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  async function handleRemove(menuItemId: number) {
    setLoading(true)
    setError('')
    setSuccess('')
    try {
      await removeCartItem(String(menuItemId))
      await loadCart()
      emitCartUpdated()
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  async function handleCheckout() {
    setLoading(true)
    setError('')
    setSuccess('')
    try {
      const latestCart = await loadCart()
      if (!latestCart?.items.length) {
        setError('Your cart is empty. Please add items before checkout.')
        return
      }

      await checkoutCart()
      setSuccess('Payment successful. Your order has been placed.')
      await loadCart()
      emitCartUpdated()
    } catch (exception) {
      setError((exception as Error).message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="rounded-3xl border border-zinc-200 bg-white p-8 text-left shadow-sm">
      <div className="flex items-center gap-3">
        <span className="grid size-11 place-items-center rounded-2xl bg-orange-50 text-orange-600">
          <ShoppingCart className="size-5" />
        </span>
        <div>
          <h1 className="text-xl font-semibold text-zinc-900">Cart & Checkout</h1>
          <p className="mt-1 text-sm text-zinc-600">
            Live cart connected to backend.
          </p>
        </div>
      </div>

      {error ? (
        <div className="mt-5 rounded-2xl border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">
          {error}
        </div>
      ) : null}
      {success ? (
        <div className="mt-5 rounded-2xl border border-emerald-200 bg-emerald-50 p-3 text-sm text-emerald-700">
          {success}
        </div>
      ) : null}

      <div className="mt-6 space-y-3">
        {cart?.items.length ? (
          cart.items.map((item) => (
            <div key={item.menuItemId} className="rounded-2xl border border-zinc-200 p-4">
              <div className="flex items-center justify-between gap-3">
                <div>
                  <div className="text-sm font-semibold text-zinc-900">{item.menuItemName}</div>
                  <div className="text-xs text-zinc-600">
                    Qty {item.quantity} · ₹{item.lineTotal}
                  </div>
                </div>
                <div className="flex items-center gap-2">
                  <button
                    type="button"
                    onClick={() => handleQuantity(item.menuItemId, Math.max(1, item.quantity - 1))}
                    className="rounded-lg border border-zinc-200 px-2 py-1 text-xs"
                  >
                    -
                  </button>
                  <button
                    type="button"
                    onClick={() => handleQuantity(item.menuItemId, item.quantity + 1)}
                    className="rounded-lg border border-zinc-200 px-2 py-1 text-xs"
                  >
                    +
                  </button>
                  <button
                    type="button"
                    onClick={() => handleRemove(item.menuItemId)}
                    className="rounded-lg border border-rose-200 bg-rose-50 px-2 py-1 text-xs text-rose-700"
                  >
                    Remove
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="rounded-2xl border border-dashed border-zinc-200 bg-zinc-50 p-5 text-sm text-zinc-600">
            Cart is empty
          </div>
        )}
      </div>

      <div className="mt-6 rounded-2xl border border-zinc-200 bg-zinc-50 p-4">
        <div className="flex items-center justify-between text-sm">
          <span className="text-zinc-600">Items</span>
          <span className="font-semibold text-zinc-900">
            {cart?.items.reduce((sum, item) => sum + item.quantity, 0) ?? 0}
          </span>
        </div>
        <div className="mt-2 flex items-center justify-between text-sm">
          <span className="text-zinc-600">Payable amount</span>
          <span className="text-base font-semibold text-zinc-900">₹{cart?.totalAmount ?? 0}</span>
        </div>
      </div>

      <div className="mt-8 flex flex-wrap items-center gap-3">
        <Link
          to="/"
          className="rounded-2xl bg-zinc-900 px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-zinc-800 transition"
        >
          Continue browsing
        </Link>
        <button
          type="button"
          onClick={handleCheckout}
          disabled={loading || !isUser || !cart?.items.length}
          className="rounded-2xl border border-zinc-200 bg-white px-5 py-2.5 text-sm font-semibold text-zinc-900 hover:bg-zinc-50 transition"
        >
          {loading ? 'Working...' : 'Proceed to payment'}
        </button>
      </div>
    </div>
  )
}

