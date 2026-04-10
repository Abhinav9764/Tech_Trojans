/** Hero image when no valid URL is available */
export const DEFAULT_RESTAURANT_COVER =
  'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=1200&q=70'

/** Menu / dish thumbnail when URL missing, placeholder, or failed to load */
export const DEFAULT_DISH_IMAGE =
  'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=400&q=80'

/** Seeded menu items use example.com URLs that never load real images */
export function isBrokenOrPlaceholderImageUrl(url?: string | null): boolean {
  if (!url?.trim()) return true
  const normalized = url.trim().toLowerCase()
  if (normalized.includes('example.com')) return true
  if (normalized.startsWith('https://') || normalized.startsWith('http://')) return false
  return true
}
