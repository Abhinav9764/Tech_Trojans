import { Link } from 'react-router-dom'
import { Plus, Star } from 'lucide-react'
import { useEffect, useState } from 'react'
import { DEFAULT_RESTAURANT_COVER } from '../lib/restaurant-images'
import type { Restaurant } from '../types'

function formatPriceForTwo(priceForTwo: number) {
  return `₹${priceForTwo} for two`
}

export function RestaurantCard({ restaurant }: { restaurant: Restaurant }) {
  const [cardImageSrc, setCardImageSrc] = useState(restaurant.imageUrl)

  useEffect(() => {
    setCardImageSrc(restaurant.imageUrl)
  }, [restaurant.id, restaurant.imageUrl])

  return (
    <div className="rounded-3xl border border-zinc-200 bg-white shadow-sm transition hover:-translate-y-0.5 hover:shadow-soft">
      <Link
        to={`/restaurants/${restaurant.id}`}
        state={{ coverImageUrl: restaurant.imageUrl, restaurantName: restaurant.name }}
        className="block"
      >
        <div className="relative overflow-hidden rounded-t-3xl">
          <img
            src={cardImageSrc}
            alt={restaurant.name}
            className="h-40 w-full object-cover"
            loading="lazy"
            onError={() => setCardImageSrc(DEFAULT_RESTAURANT_COVER)}
          />
          <div className="absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/60 to-transparent p-4">
            <div className="text-sm font-semibold text-white">{restaurant.name}</div>
            <div className="mt-1 flex flex-wrap gap-2 text-[11px] text-white/90">
              {restaurant.tags.slice(0, 2).map((t) => (
                <span key={t} className="rounded-full bg-white/15 px-2 py-0.5">
                  {t}
                </span>
              ))}
            </div>
          </div>
        </div>
      </Link>

      <div className="p-4">
        <div className="flex items-center justify-between gap-3">
          <div className="flex items-center gap-2 text-xs text-zinc-600">
            <span className="inline-flex items-center gap-1 rounded-full bg-emerald-50 px-2 py-1 font-semibold text-emerald-700">
              <Star className="size-3.5" />
              {restaurant.rating.toFixed(1)}
            </span>
            <span>•</span>
            <span>{restaurant.deliveryMins} mins</span>
          </div>
          <div className="text-xs font-medium text-zinc-600">
            {formatPriceForTwo(restaurant.priceForTwo)}
          </div>
        </div>

        <button
          type="button"
          className="mt-4 inline-flex w-full items-center justify-center gap-2 rounded-2xl bg-orange-500 px-4 py-2.5 text-sm font-semibold text-white shadow-sm transition hover:bg-orange-600 focus:outline-none focus:ring-4 focus:ring-orange-100"
        >
          <Plus className="size-4" />
          Add to cart
        </button>
      </div>
    </div>
  )
}

