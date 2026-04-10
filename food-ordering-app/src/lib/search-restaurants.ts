import type { Restaurant } from '../types'

function normalizeRestaurant(raw: Restaurant): Restaurant {
  return {
    ...raw,
    id: String(raw.id),
    name: raw.name ?? '',
    tags: Array.isArray(raw.tags) ? raw.tags : [],
  }
}

export function normalizeRestaurants(list: Restaurant[]): Restaurant[] {
  return list.map(normalizeRestaurant)
}

/** Match restaurant name + tags; supports multi-word queries (all tokens must match). */
export function filterRestaurantsByQuery(restaurants: Restaurant[], query: string): Restaurant[] {
  const trimmed = query.trim().toLowerCase()
  if (!trimmed) return restaurants

  const tokens = trimmed.split(/\s+/).filter(Boolean)
  return restaurants.filter((r) => {
    const tags = Array.isArray(r.tags) ? r.tags : []
    const hay = `${r.name} ${tags.join(' ')}`.toLowerCase()
    return tokens.every((token) => hay.includes(token))
  })
}
