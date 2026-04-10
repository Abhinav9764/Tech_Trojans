import type { MenuItem } from '../types'

const STORAGE_KEY = 'fo_wishlist'

export interface WishlistEntry {
  menuItemId: number
  restaurantId: string
  restaurantName: string
  name: string
  description: string
  price: number
  category: string
  imageUrl?: string
  savedAt: string
}

function readRaw(): WishlistEntry[] {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (!raw) return []
    const parsed = JSON.parse(raw) as unknown
    if (!Array.isArray(parsed)) return []
    return parsed.filter(
      (row): row is WishlistEntry =>
        typeof row === 'object' &&
        row !== null &&
        typeof (row as WishlistEntry).menuItemId === 'number',
    )
  } catch {
    return []
  }
}

function write(entries: WishlistEntry[]) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(entries))
}

export function getWishlist(): WishlistEntry[] {
  return readRaw().sort(
    (a, b) => new Date(b.savedAt).getTime() - new Date(a.savedAt).getTime(),
  )
}

export function isMenuItemInWishlist(menuItemId: number): boolean {
  return readRaw().some((e) => e.menuItemId === menuItemId)
}

export function addWishlistItem(
  restaurantId: string,
  item: MenuItem,
): WishlistEntry[] {
  const list = readRaw()
  const next = list.filter((e) => e.menuItemId !== item.id)
  next.push({
    menuItemId: item.id,
    restaurantId,
    restaurantName: item.restaurantName,
    name: item.name,
    description: item.description,
    price: item.price,
    category: item.category,
    imageUrl: item.imageUrl,
    savedAt: new Date().toISOString(),
  })
  write(next)
  return getWishlist()
}

export function removeWishlistItem(menuItemId: number): WishlistEntry[] {
  const next = readRaw().filter((e) => e.menuItemId !== menuItemId)
  write(next)
  return getWishlist()
}

export function toggleWishlistItem(
  restaurantId: string,
  item: MenuItem,
): { inWishlist: boolean; items: WishlistEntry[] } {
  if (isMenuItemInWishlist(item.id)) {
    return { inWishlist: false, items: removeWishlistItem(item.id) }
  }
  return { inWishlist: true, items: addWishlistItem(restaurantId, item) }
}
