const CART_UPDATED_EVENT = 'fo:cart-updated'
const SESSION_UPDATED_EVENT = 'fo:session-updated'
const WISHLIST_UPDATED_EVENT = 'fo:wishlist-updated'

export function emitCartUpdated() {
  window.dispatchEvent(new Event(CART_UPDATED_EVENT))
}

export function emitSessionUpdated() {
  window.dispatchEvent(new Event(SESSION_UPDATED_EVENT))
}

export function emitWishlistUpdated() {
  window.dispatchEvent(new Event(WISHLIST_UPDATED_EVENT))
}

export function getCartUpdatedEventName() {
  return CART_UPDATED_EVENT
}

export function getSessionUpdatedEventName() {
  return SESSION_UPDATED_EVENT
}

export function getWishlistUpdatedEventName() {
  return WISHLIST_UPDATED_EVENT
}
