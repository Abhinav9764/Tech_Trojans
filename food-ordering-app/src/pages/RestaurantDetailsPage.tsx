import { Link, useLocation, useParams } from 'react-router-dom'
import { ArrowLeft, Heart } from 'lucide-react'
import { useCallback, useEffect, useMemo, useState } from 'react'
import { addCartItem, getRestaurantMenu, getRestaurants } from '../lib/api'
import { getSession } from '../lib/auth'
import { emitCartUpdated, emitWishlistUpdated, getWishlistUpdatedEventName } from '../lib/events'
import { toggleWishlistItem } from '../lib/wishlist'
import {
  DEFAULT_DISH_IMAGE,
  DEFAULT_RESTAURANT_COVER,
  isBrokenOrPlaceholderImageUrl,
} from '../lib/restaurant-images'
import { getWishlist } from '../lib/wishlist'
import type { MenuItem } from '../types'

interface RestaurantLocationState {
  coverImageUrl?: string
  restaurantName?: string
}

export function RestaurantDetailsPage() {
  const { restaurantId } = useParams()
  const location = useLocation()
  const navState = location.state as RestaurantLocationState | null
  const [menu, setMenu] = useState<MenuItem[]>([])
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const [coverFromList, setCoverFromList] = useState<string | null>(null)

  useEffect(() => {
    setCoverFromList(null)
  }, [restaurantId])

  useEffect(() => {
    if (!restaurantId) return
    let mounted = true
    getRestaurantMenu(restaurantId)
      .then((data) => {
        if (mounted) setMenu(data)
      })
      .catch((exception) => {
        if (mounted) setError((exception as Error).message)
      })
    return () => {
      mounted = false
    }
  }, [restaurantId])

  useEffect(() => {
    if (!restaurantId) return
    const fromNav = navState?.coverImageUrl
    if (fromNav && !isBrokenOrPlaceholderImageUrl(fromNav)) return

    let mounted = true
    getRestaurants()
      .then((list) => {
        if (!mounted) return
        const match = list.find((r) => String(r.id) === String(restaurantId))
        if (match?.imageUrl && !isBrokenOrPlaceholderImageUrl(match.imageUrl))
          setCoverFromList(match.imageUrl)
      })
      .catch(() => {
        if (mounted) setCoverFromList(null)
      })
    return () => {
      mounted = false
    }
  }, [restaurantId, navState?.coverImageUrl])

  async function handleAddToCart(menuItemId: number) {
    setError('')
    setSuccess('')
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
      await addCartItem({ menuItemId, quantity: 1 })
      emitCartUpdated()
      setSuccess('Added to cart successfully')
    } catch (exception) {
      setError((exception as Error).message)
    }
  }

  const restaurantName =
    menu[0]?.restaurantName ?? navState?.restaurantName ?? 'Restaurant'

  const firstWorkingMenuImage = useMemo(() => {
    for (const item of menu) {
      const url = item.imageUrl
      if (!isBrokenOrPlaceholderImageUrl(url)) return url as string
    }
    return null
  }, [menu])

  const computedHeroUrl = useMemo(() => {
    const fromNav = navState?.coverImageUrl
    if (fromNav && !isBrokenOrPlaceholderImageUrl(fromNav)) return fromNav
    if (coverFromList) return coverFromList
    if (firstWorkingMenuImage) return firstWorkingMenuImage
    return DEFAULT_RESTAURANT_COVER
  }, [navState?.coverImageUrl, coverFromList, firstWorkingMenuImage])

  const [heroSrc, setHeroSrc] = useState(computedHeroUrl)
  useEffect(() => {
    setHeroSrc(computedHeroUrl)
  }, [computedHeroUrl])

  const [wishlistIds, setWishlistIds] = useState<Set<number>>(() => {
    return new Set(getWishlist().map((e) => e.menuItemId))
  })

  const syncWishlistIds = useCallback(() => {
    setWishlistIds(new Set(getWishlist().map((e) => e.menuItemId)))
  }, [])

  useEffect(() => {
    syncWishlistIds()
  }, [restaurantId, syncWishlistIds])

  useEffect(() => {
    const handleWishlist = () => syncWishlistIds()
    window.addEventListener(getWishlistUpdatedEventName(), handleWishlist)
    window.addEventListener('storage', handleWishlist)
    return () => {
      window.removeEventListener(getWishlistUpdatedEventName(), handleWishlist)
      window.removeEventListener('storage', handleWishlist)
    }
  }, [syncWishlistIds])

  function handleToggleWishlist(item: MenuItem) {
    if (!restaurantId) return
    toggleWishlistItem(restaurantId, item)
    emitWishlistUpdated()
    syncWishlistIds()
    setSuccess('')
    setError('')
  }

  return (
    <div className="space-y-6 text-left">
      <Link
        to="/"
        className="inline-flex items-center gap-2 text-sm font-semibold text-zinc-700 hover:text-zinc-900"
      >
        <ArrowLeft className="size-4" />
        Back to home
      </Link>

      {!!error ? (
        <div className="rounded-2xl border border-rose-200 bg-rose-50 p-4 text-sm text-rose-700">
          {error}
        </div>
      ) : null}
      {!!success ? (
        <div className="rounded-2xl border border-emerald-200 bg-emerald-50 p-4 text-sm text-emerald-700">
          {success}
        </div>
      ) : null}

      {menu.length > 0 ? (
        <div className="overflow-hidden rounded-3xl border border-zinc-200 bg-white shadow-sm">
          <img
            src={heroSrc}
            alt={restaurantName}
            className="h-52 w-full object-cover bg-zinc-100"
            onError={() => setHeroSrc(DEFAULT_RESTAURANT_COVER)}
          />
          <div className="p-6">
            <h1 className="text-2xl font-semibold text-zinc-900">{restaurantName}</h1>
            <p className="mt-2 text-sm text-zinc-600">Live menu from backend API.</p>
            <div className="mt-3 inline-flex rounded-full bg-orange-50 px-3 py-1 text-xs font-semibold text-orange-700">
              {menu.length} dishes available
            </div>

            <div className="mt-5 grid gap-3">
              {menu.map((item) => {
                const inWishlist = wishlistIds.has(item.id)
                const dishSrc =
                  item.imageUrl && !isBrokenOrPlaceholderImageUrl(item.imageUrl)
                    ? item.imageUrl
                    : DEFAULT_DISH_IMAGE
                return (
                  <div key={item.id} className="rounded-2xl border border-zinc-200 p-4">
                    <div className="flex items-start justify-between gap-3">
                      <img
                        src={dishSrc}
                        alt=""
                        className="size-20 shrink-0 rounded-xl object-cover bg-zinc-100"
                        loading="lazy"
                        onError={(event) => {
                          event.currentTarget.onerror = null
                          event.currentTarget.src = DEFAULT_DISH_IMAGE
                        }}
                      />
                      <div className="min-w-0 flex-1">
                        <div className="text-sm font-semibold text-zinc-900">{item.name}</div>
                        <div className="mt-1 text-xs text-zinc-600">{item.description}</div>
                        <div className="mt-2 text-xs text-zinc-500">
                          {item.category} · ₹{item.price}
                        </div>
                      </div>
                      <div className="flex shrink-0 items-center gap-2">
                        <button
                          type="button"
                          onClick={() => handleToggleWishlist(item)}
                          className={`grid size-10 place-items-center rounded-xl border transition focus:outline-none focus:ring-4 focus:ring-rose-100 ${
                            inWishlist
                              ? 'border-rose-200 bg-rose-50 text-rose-600'
                              : 'border-zinc-200 bg-white text-zinc-500 hover:border-rose-200 hover:text-rose-600'
                          }`}
                          aria-label={inWishlist ? 'Remove from wishlist' : 'Add to wishlist'}
                          aria-pressed={inWishlist}
                        >
                          <Heart className={`size-5 ${inWishlist ? 'fill-current' : ''}`} />
                        </button>
                        <button
                          type="button"
                          onClick={() => handleAddToCart(item.id)}
                          className="rounded-xl bg-zinc-900 px-3 py-2 text-xs font-semibold text-white"
                        >
                          Add
                        </button>
                      </div>
                    </div>
                  </div>
                )
              })}
            </div>
          </div>
        </div>
      ) : (
        <div className="rounded-3xl border border-zinc-200 bg-white p-8 shadow-sm">
          <div className="text-sm font-semibold text-zinc-900">Restaurant not found</div>
          <div className="mt-1 text-sm text-zinc-600">
            No menu found for restaurant id <span className="font-mono">{restaurantId}</span>.
          </div>
        </div>
      )}
    </div>
  )
}

