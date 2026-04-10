export type Restaurant = {
  id: string
  name: string
  imageUrl: string
  rating: number
  deliveryMins: number
  priceForTwo: number
  tags: string[]
}

export type Order = {
  id: string
  restaurantName: string
  totalAmount: number
  status: string
  placedAt: string
}

export type MenuItem = {
  id: number
  name: string
  description: string
  price: number
  category: string
  imageUrl?: string
  restaurantName: string
  available: boolean
}

export type CartItem = {
  menuItemId: number
  menuItemName: string
  quantity: number
  unitPrice: number
  lineTotal: number
}

export type CartResponse = {
  cartId: number
  userEmail: string
  items: CartItem[]
  totalAmount: number
}

export type LoginRole = 'USER' | 'RESTAURANT' | 'ADMIN'

export type AuthPayload = {
  email: string
  password: string
}

export type SignupRole = 'USER' | 'RESTAURANT'

export type SignupPayload = {
  fullName: string
  email: string
  password: string
  phoneNumber?: string
  role: SignupRole
  restaurantName?: string
}

export type AdminUser = {
  id: number
  email: string
  fullName: string
  phoneNumber?: string | null
  role: string
  restaurantName?: string | null
  active: boolean
  createdAt?: string
}

export type AdminUserUpdatePayload = {
  fullName?: string
  email?: string
  phoneNumber?: string
  restaurantName?: string
  active?: boolean
  newPassword?: string
}

export type CartItemRequest = {
  menuItemId: number
  quantity: number
}
