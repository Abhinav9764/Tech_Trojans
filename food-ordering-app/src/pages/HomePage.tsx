import { Clock, Heart, Receipt, ShoppingBag, Store } from 'lucide-react'
import { useEffect, useLayoutEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { QuickAccessCard } from '../components/QuickAccessCard'
import { RestaurantCard } from '../components/RestaurantCard'
import { useSearch } from '../contexts/SearchContext'
import { getRestaurants, getUserOrders } from '../lib/api'
import { scrollElementIntoViewSmooth } from '../lib/navigation'
import { filterRestaurantsByQuery, normalizeRestaurants } from '../lib/search-restaurants'
import type { Order, Restaurant } from '../types'

export function HomePage() {
  const { query } = useSearch()
  const location = useLocation()
  const navigate = useNavigate()
  const [restaurants, setRestaurants] = useState<Restaurant[]>([])
  const [recentOrders, setRecentOrders] = useState<Order[]>([])
  const [error, setError] = useState('')

  useLayoutEffect(() => {
    const state = location.state as { scrollToSection?: string } | null
    const sectionId = state?.scrollToSection
    if (!sectionId) return

    requestAnimationFrame(() => scrollElementIntoViewSmooth(sectionId))
    navigate(`${location.pathname}${location.search}`, {
      replace: true,
      state: {},
      preventScrollReset: true,
    })
  }, [location.state, location.pathname, location.search, navigate])

  useEffect(() => {
    let mounted = true

    async function load() {
      try {
        const restaurantData = await getRestaurants()
        if (mounted) setRestaurants(normalizeRestaurants(restaurantData))
      } catch (exception) {
        if (mounted) setError((exception as Error).message)
      }

      const hasToken = !!localStorage.getItem('fo_token')
      if (!hasToken) return

      try {
        const orders = await getUserOrders()
        if (mounted) setRecentOrders(orders.slice(0, 3))
      } catch {
        if (mounted) setRecentOrders([])
      }
    }

    load()
    return () => {
      mounted = false
    }
  }, [])

  const filtered = filterRestaurantsByQuery(restaurants, query)

  return (
    <div className="space-y-10">
      <section className="relative overflow-hidden rounded-3xl border border-zinc-200 bg-gradient-to-br from-orange-500 via-orange-500 to-rose-500 p-8 text-left text-white shadow-soft">
        <div className="absolute -right-24 -top-24 size-64 rounded-full bg-white/15 blur-2xl" />
        <div className="absolute -bottom-28 -left-24 size-72 rounded-full bg-white/10 blur-2xl" />

        <div className="relative max-w-2xl">
          <div className="inline-flex items-center gap-2 rounded-full bg-white/15 px-3 py-1 text-xs font-semibold">
            <ShoppingBag className="size-4" />
            50% OFF up to ₹200 today
          </div>
          <h1 className="mt-4 text-balance text-3xl font-bold leading-tight md:text-4xl">
            Delicious food, delivered fast—right when you want it.
          </h1>
          <p className="mt-3 text-sm text-white/90 md:text-base">
            Discover top-rated restaurants, track orders, and checkout in seconds.
          </p>
          <div className="mt-6 flex flex-wrap items-center gap-3">
            <button
              type="button"
              onClick={() => scrollElementIntoViewSmooth('restaurants')}
              className="rounded-2xl bg-white px-5 py-2.5 text-sm font-semibold text-zinc-900 shadow-sm transition hover:bg-zinc-100 focus:outline-none focus:ring-4 focus:ring-white/30"
            >
              Order Now
            </button>
            <div className="flex items-center gap-2 text-xs text-white/90">
              <Clock className="size-4" />
              Avg delivery 25–35 mins
            </div>
          </div>
        </div>
      </section>

      <section className="space-y-4">
        <div className="flex items-end justify-between gap-4">
          <div>
            <h2 className="text-lg font-semibold text-zinc-900">Quick access</h2>
            <p className="mt-1 text-sm text-zinc-600">
              Jump back into what you do most—faster.
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-4">
          <QuickAccessCard
            kind="section"
            title="Restaurants"
            description="Browse trending places near you"
            sectionId="restaurants"
            icon={Store}
          />
          <QuickAccessCard
            kind="route"
            title="Cart & Checkout"
            description="Review items and checkout quickly"
            to="/cart"
            icon={ShoppingBag}
          />
          <QuickAccessCard
            kind="route"
            title="Order History"
            description="Track and reorder favorites"
            to="/orders"
            icon={Receipt}
          />
          <QuickAccessCard
            kind="route"
            title="Wishlist"
            description="Dishes you saved from menus"
            to="/wishlist"
            icon={Heart}
          />
        </div>
      </section>

      <section id="restaurants" className="space-y-4 scroll-mt-24">
        {error ? (
          <div className="rounded-2xl border border-rose-200 bg-rose-50 p-4 text-sm text-rose-700">
            {error}
          </div>
        ) : null}

        <div className="flex flex-wrap items-end justify-between gap-4">
          <div>
            <h2 className="text-lg font-semibold text-zinc-900">Restaurants</h2>
            <p className="mt-1 text-sm text-zinc-600">
              {query.trim() ? (
                <>
                  Showing results for <span className="font-semibold text-zinc-900">“{query}”</span>
                </>
              ) : (
                'Handpicked picks for a great meal.'
              )}
            </p>
          </div>
          <div className="text-sm text-zinc-500">{filtered.length} found</div>
        </div>

        <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
          {filtered.map((r) => (
            <RestaurantCard key={r.id} restaurant={r} />
          ))}
        </div>
      </section>

      <section className="space-y-4">
        <div>
          <h2 className="text-lg font-semibold text-zinc-900">Recent orders</h2>
          <p className="mt-1 text-sm text-zinc-600">
            Track your latest purchases and reorder anytime.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-4 lg:grid-cols-2">
          {recentOrders.length === 0 ? (
            <div className="rounded-2xl border border-zinc-200 bg-white p-5 text-sm text-zinc-600">
              No recent orders yet. Login as user and place your first order.
            </div>
          ) : recentOrders.map((o) => (
            <div
              key={o.id}
              className="rounded-3xl border border-zinc-200 bg-white p-5 shadow-sm"
            >
              <div className="flex items-start justify-between gap-3">
                <div>
                  <div className="text-sm font-semibold text-zinc-900">
                    {o.restaurantName}
                  </div>
                  <div className="mt-1 text-xs text-zinc-500">
                    Order total: <span className="font-semibold text-zinc-900">₹{o.totalAmount}</span>
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
              <div className="mt-4 flex items-center justify-between text-xs text-zinc-500">
                <span>{new Date(o.placedAt).toLocaleString()}</span>
                <button
                  type="button"
                  className="rounded-xl bg-zinc-900 px-3 py-2 text-xs font-semibold text-white hover:bg-zinc-800 transition"
                >
                  Reorder
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}

