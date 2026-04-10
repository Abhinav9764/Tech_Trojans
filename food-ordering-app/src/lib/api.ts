import type {
  AdminUser,
  AdminUserUpdatePayload,
  AuthPayload,
  CartItemRequest,
  CartResponse,
  LoginRole,
  MenuItem,
  Order,
  Restaurant,
  SignupPayload,
} from '../types'

const API_BASE = 'http://127.0.0.1:8080'

function getToken() {
  return localStorage.getItem('fo_token')
}

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const token = getToken()
  const response = await fetch(`${API_BASE}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(options?.headers ?? {}),
    },
  })

  const data = await response.json().catch(() => ({}))
  if (!response.ok) throw new Error(data.error ?? `Request failed (${response.status})`)
  return data as T
}

export function signin(role: LoginRole, payload: AuthPayload) {
  return request<{ token: string; email: string; role: string; fullName: string }>(
    `/api/auth/signin/${role.toLowerCase()}`,
    { method: 'POST', body: JSON.stringify(payload) },
  )
}

export function signinAdmin(payload: AuthPayload) {
  return request<{ token: string; email: string; role: string; fullName: string }>(
    '/api/auth/signin/admin',
    { method: 'POST', body: JSON.stringify(payload) },
  )
}

export function listAdminUsers() {
  return request<AdminUser[]>('/api/admin/users')
}

export function updateAdminUser(id: number, payload: AdminUserUpdatePayload) {
  return request<AdminUser>(`/api/admin/users/${id}`, {
    method: 'PATCH',
    body: JSON.stringify(payload),
  })
}

export async function deleteAdminUser(id: number) {
  const token = getToken()
  const response = await fetch(`${API_BASE}/api/admin/users/${id}`, {
    method: 'DELETE',
    headers: {
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
  })
  const data = await response.json().catch(() => ({}))
  if (!response.ok) throw new Error((data as { error?: string }).error ?? `Request failed (${response.status})`)
}

export function signup(payload: SignupPayload) {
  return request<{ message: string; role: string }>('/api/auth/signup', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function getRestaurants() {
  return request<Restaurant[]>('/api/public/restaurants')
}

function parseMenuItem(raw: unknown): MenuItem {
  const r = raw as Record<string, unknown>
  const imageUrlRaw = r.imageUrl ?? r.image_url
  const imageUrl = typeof imageUrlRaw === 'string' ? imageUrlRaw : undefined
  const priceRaw = r.price
  const price = typeof priceRaw === 'number' ? priceRaw : Number(priceRaw)
  return {
    id: Number(r.id),
    name: String(r.name ?? ''),
    description: String(r.description ?? ''),
    price: Number.isFinite(price) ? price : 0,
    category: String(r.category ?? ''),
    imageUrl,
    restaurantName: String(r.restaurantName ?? r.restaurant_name ?? ''),
    available: Boolean(r.available ?? r.isAvailable ?? true),
  }
}

export async function getRestaurantMenu(restaurantId: string) {
  const data = await request<unknown[]>(`/api/public/restaurants/${restaurantId}/menu`)
  return data.map(parseMenuItem)
}

export function getCart() {
  return request<CartResponse>('/api/user/cart')
}

export function addCartItem(payload: CartItemRequest) {
  return request<CartResponse>('/api/user/cart/items', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export function updateCartItem(menuItemId: string, quantity: number) {
  return request<CartResponse>(`/api/user/cart/items/${menuItemId}?quantity=${quantity}`, {
    method: 'PUT',
  })
}

export function removeCartItem(menuItemId: string) {
  return request<CartResponse>(`/api/user/cart/items/${menuItemId}`, {
    method: 'DELETE',
  })
}

export function checkoutCart() {
  return request<Order>('/api/user/cart/checkout', { method: 'POST' })
}

export function getUserOrders() {
  return request<Order[]>('/api/user/orders')
}
