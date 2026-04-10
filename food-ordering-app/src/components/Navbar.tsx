import { Link, NavLink, useLocation, useNavigate } from 'react-router-dom'
import { Shield, ShoppingCart, UtensilsCrossed } from 'lucide-react'
import { useEffect, useState } from 'react'
import { getCart } from '../lib/api'
import { clearSession, getSession, isLoggedIn } from '../lib/auth'
import {
  getCartUpdatedEventName,
  getSessionUpdatedEventName,
  getWishlistUpdatedEventName,
} from '../lib/events'
import { getWishlist } from '../lib/wishlist'
import { useSearch } from '../contexts/SearchContext'

const navLinkBase =
  'text-sm font-medium text-zinc-600 hover:text-zinc-900 transition-colors'

export function Navbar() {
  const navigate = useNavigate()
  const location = useLocation()
  const { query, setQuery } = useSearch()
  const [cartCount, setCartCount] = useState(0)
  const [wishlistCount, setWishlistCount] = useState(0)
  const session = getSession()
  const loggedIn = isLoggedIn()

  useEffect(() => {
    async function refreshCartCount() {
      if (!loggedIn || session?.role !== 'USER') {
        setCartCount(0)
        return
      }

      try {
        const cart = await getCart()
        const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0)
        setCartCount(totalItems)
      } catch {
        setCartCount(0)
      }
    }

    const handleRefresh = () => {
      void refreshCartCount()
    }

    void refreshCartCount()
    window.addEventListener(getCartUpdatedEventName(), handleRefresh)
    window.addEventListener(getSessionUpdatedEventName(), handleRefresh)
    window.addEventListener('storage', handleRefresh)

    return () => {
      window.removeEventListener(getCartUpdatedEventName(), handleRefresh)
      window.removeEventListener(getSessionUpdatedEventName(), handleRefresh)
      window.removeEventListener('storage', handleRefresh)
    }
  }, [loggedIn, session?.role, location.pathname])

  useEffect(() => {
    function refreshWishlistCount() {
      setWishlistCount(getWishlist().length)
    }
    refreshWishlistCount()
    window.addEventListener(getWishlistUpdatedEventName(), refreshWishlistCount)
    window.addEventListener('storage', refreshWishlistCount)
    return () => {
      window.removeEventListener(getWishlistUpdatedEventName(), refreshWishlistCount)
      window.removeEventListener('storage', refreshWishlistCount)
    }
  }, [location.pathname])

  const isAdminSession = session?.role === 'ADMIN'

  return (
    <header className="sticky top-0 z-40 border-b border-zinc-200 bg-white/80 backdrop-blur">
      <div className="container-app">
        <div className="flex h-16 items-center gap-4">
          <Link to="/" className="flex items-center gap-2">
            <span className="grid size-9 place-items-center rounded-xl bg-orange-500 text-white shadow-soft">
              <UtensilsCrossed className="size-5" />
            </span>
            <div className="leading-tight">
              <div className="text-sm font-semibold text-zinc-900">Food Delivery</div>
              <div className="text-xs text-zinc-500">Order smart, eat happy</div>
            </div>
          </Link>

          {!isAdminSession ? (
            <div className="mx-auto hidden w-full max-w-xl md:block">
              <div className="relative">
                <input
                  value={query}
                  onChange={(e) => setQuery(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === 'Enter') navigate('/')
                  }}
                  placeholder="Search restaurants or dishes…"
                  className="w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm text-zinc-900 shadow-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
                />
                <div className="pointer-events-none absolute inset-y-0 right-3 grid place-items-center text-xs text-zinc-400">
                  Enter
                </div>
              </div>
            </div>
          ) : (
            <div className="mx-auto hidden text-sm text-zinc-500 md:block">
              Signed in as administrator — use the console to manage accounts.
            </div>
          )}

          <nav className="ml-auto flex items-center gap-4">
            <NavLink to="/" className={navLinkBase}>
              Home
            </NavLink>
            {isAdminSession ? (
              <Link
                to="/admin"
                className={`${navLinkBase} inline-flex items-center gap-1.5 font-semibold text-indigo-700`}
              >
                <Shield className="size-4" aria-hidden />
                Admin console
              </Link>
            ) : (
              <>
                <NavLink to="/orders" className={navLinkBase}>
                  Orders
                </NavLink>
                <NavLink
                  to="/wishlist"
                  className={`${navLinkBase} inline-flex items-center gap-1.5`}
                >
                  <span>Wishlist</span>
                  {wishlistCount > 0 ? (
                    <span className="grid min-w-5 place-items-center rounded-full bg-rose-100 px-1.5 py-0.5 text-[11px] font-semibold text-rose-700">
                      {wishlistCount}
                    </span>
                  ) : null}
                </NavLink>

                <Link
                  to="/cart"
                  className="relative grid size-10 place-items-center rounded-2xl border border-zinc-200 bg-white shadow-sm hover:shadow transition"
                  aria-label="Cart"
                >
                  <ShoppingCart className="size-5 text-zinc-800" />
                  {cartCount > 0 ? (
                    <span className="absolute -right-1 -top-1 grid min-w-5 place-items-center rounded-full bg-orange-500 px-1.5 py-0.5 text-[11px] font-semibold text-white shadow">
                      {cartCount}
                    </span>
                  ) : null}
                </Link>
              </>
            )}

            {loggedIn ? (
              <button
                type="button"
                onClick={() => {
                  clearSession()
                  navigate(isAdminSession ? '/admin/login' : '/login')
                }}
                className="rounded-2xl bg-zinc-900 px-4 py-2 text-sm font-semibold text-white shadow-soft hover:bg-zinc-800 transition"
              >
                Logout
              </button>
            ) : (
              <Link
                to="/login"
                className="rounded-2xl bg-zinc-900 px-4 py-2 text-sm font-semibold text-white shadow-soft hover:bg-zinc-800 transition"
              >
                Login
              </Link>
            )}
          </nav>
        </div>

        {!isAdminSession ? (
          <div className="pb-4 md:hidden">
            <input
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') navigate('/')
              }}
              placeholder="Search restaurants or dishes…"
              className="w-full rounded-2xl border border-zinc-200 bg-white px-4 py-2.5 text-sm text-zinc-900 shadow-sm outline-none transition focus:border-orange-300 focus:ring-4 focus:ring-orange-100"
            />
          </div>
        ) : null}
      </div>
    </header>
  )
}

