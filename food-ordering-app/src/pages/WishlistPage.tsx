import { Heart, Trash2 } from 'lucide-react'
import { useCallback, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { addCartItem } from '../lib/api'
import { getSession } from '../lib/auth'
import { emitCartUpdated, emitWishlistUpdated, getWishlistUpdatedEventName } from '../lib/events'
import { DEFAULT_DISH_IMAGE, isBrokenOrPlaceholderImageUrl } from '../lib/restaurant-images'
import { getWishlist, removeWishlistItem, type WishlistEntry } from '../lib/wishlist'

export function WishlistPage() {
  const [items, setItems] = useState<WishlistEntry[]>([])
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  const reload = useCallback(() => {
    setItems(getWishlist())
  }, [])

  useEffect(() => {
    reload()
    const handle = () => reload()
    window.addEventListener(getWishlistUpdatedEventName(), handle)
    window.addEventListener('storage', handle)
    return () => {
      window.removeEventListener(getWishlistUpdatedEventName(), handle)
      window.removeEventListener('storage', handle)
    }
  }, [reload])

  function handleRemove(menuItemId: number) {
    removeWishlistItem(menuItemId)
    emitWishlistUpdated()
    reload()
    setMessage('')
    setError('')
  }

  async function handleAddToCart(entry: WishlistEntry) {
    setMessage('')
    setError('')
    const session = getSession()
    if (!session) {
      setError('Please login as USER to add items to cart.')
      return
    }
    if (session.role !== 'USER') {
      setError('Please login with USER role to add items to cart.')
      return
    }

    try {
      await addCartItem({ menuItemId: entry.menuItemId, quantity: 1 })
      emitCartUpdated()
      setMessage(`“${entry.name}” added to cart.`)
    } catch (exception) {
      setError((exception as Error).message)
    }
  }

  return (
    <div className="rounded-3xl border border-zinc-200 bg-white p-8 text-left shadow-sm">
      <div className="flex items-center gap-3">
        <span className="grid size-11 place-items-center rounded-2xl bg-rose-50 text-rose-600">
          <Heart className="size-5" />
        </span>
        <div>
          <h1 className="text-xl font-semibold text-zinc-900">Wishlist</h1>
          <p className="mt-1 text-sm text-zinc-600">
            Dishes you saved from menus. Tap the heart on any dish to add it here.
          </p>
        </div>
      </div>

      {error ? (
        <div className="mt-5 rounded-2xl border border-rose-200 bg-rose-50 p-3 text-sm text-rose-700">
          {error}
        </div>
      ) : null}
      {message ? (
        <div className="mt-5 rounded-2xl border border-emerald-200 bg-emerald-50 p-3 text-sm text-emerald-700">
          {message}
        </div>
      ) : null}

      <div className="mt-8 space-y-3">
        {items.length === 0 ? (
          <div className="rounded-3xl border border-dashed border-zinc-200 bg-zinc-50 p-8 text-center">
            <div className="text-sm font-semibold text-zinc-900">No dishes saved yet</div>
            <div className="mt-1 text-sm text-zinc-600">
              Open a restaurant menu and tap the heart next to a dish.
            </div>
            <Link
              to="/"
              className="mt-4 inline-flex rounded-2xl bg-zinc-900 px-5 py-2.5 text-sm font-semibold text-white hover:bg-zinc-800 transition"
            >
              Browse restaurants
            </Link>
          </div>
        ) : (
          items.map((entry) => (
            <div
              key={entry.menuItemId}
              className="flex flex-wrap items-center justify-between gap-3 rounded-2xl border border-zinc-200 p-4"
            >
              <img
                src={
                  entry.imageUrl && !isBrokenOrPlaceholderImageUrl(entry.imageUrl)
                    ? entry.imageUrl
                    : DEFAULT_DISH_IMAGE
                }
                alt=""
                className="size-16 shrink-0 rounded-xl object-cover bg-zinc-100"
                loading="lazy"
                onError={(event) => {
                  event.currentTarget.onerror = null
                  event.currentTarget.src = DEFAULT_DISH_IMAGE
                }}
              />
              <div className="min-w-0 flex-1">
                <div className="text-sm font-semibold text-zinc-900">{entry.name}</div>
                <div className="mt-1 text-xs text-zinc-600">{entry.description}</div>
                <div className="mt-2 text-xs text-zinc-500">
                  {entry.restaurantName} · {entry.category} · ₹{entry.price}
                </div>
                <Link
                  to={`/restaurants/${entry.restaurantId}`}
                  className="mt-2 inline-block text-xs font-semibold text-orange-600 hover:text-orange-700"
                >
                  View restaurant menu
                </Link>
              </div>
              <div className="flex shrink-0 flex-wrap items-center gap-2">
                <button
                  type="button"
                  onClick={() => handleAddToCart(entry)}
                  className="rounded-xl bg-zinc-900 px-3 py-2 text-xs font-semibold text-white"
                >
                  Add to cart
                </button>
                <button
                  type="button"
                  onClick={() => handleRemove(entry.menuItemId)}
                  className="grid size-10 place-items-center rounded-xl border border-zinc-200 text-zinc-600 hover:border-rose-200 hover:bg-rose-50 hover:text-rose-700"
                  aria-label={`Remove ${entry.name} from wishlist`}
                >
                  <Trash2 className="size-4" />
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  )
}
