import type { Order, Restaurant } from '../types'

export const restaurants: Restaurant[] = [
  {
    id: 'r1',
    name: 'Spice Route Kitchen',
    imageUrl:
      'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=1200&q=70',
    rating: 4.5,
    deliveryMins: 28,
    priceForTwo: 350,
    tags: ['North Indian', 'Biryani'],
  },
  {
    id: 'r2',
    name: 'Urban Pizza Co.',
    imageUrl:
      'https://images.unsplash.com/photo-1601924638867-3ec9c3f1f85d?auto=format&fit=crop&w=1200&q=70',
    rating: 4.3,
    deliveryMins: 32,
    priceForTwo: 499,
    tags: ['Pizza', 'Italian'],
  },
  {
    id: 'r3',
    name: 'Green Bowl Salads',
    imageUrl:
      'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=1200&q=70',
    rating: 4.6,
    deliveryMins: 24,
    priceForTwo: 299,
    tags: ['Healthy', 'Salads'],
  },
  {
    id: 'r4',
    name: 'Street Treats',
    imageUrl:
      'https://images.unsplash.com/photo-1604908554027-4623dbb7c3ed?auto=format&fit=crop&w=1200&q=70',
    rating: 4.2,
    deliveryMins: 20,
    priceForTwo: 199,
    tags: ['Snacks', 'Chaat'],
  },
  {
    id: 'r5',
    name: 'Sushi & Co.',
    imageUrl:
      'https://images.unsplash.com/photo-1553621042-f6e147245754?auto=format&fit=crop&w=1200&q=70',
    rating: 4.7,
    deliveryMins: 40,
    priceForTwo: 799,
    tags: ['Japanese', 'Sushi'],
  },
  {
    id: 'r6',
    name: 'Dessert Lab',
    imageUrl:
      'https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=1200&q=70',
    rating: 4.4,
    deliveryMins: 26,
    priceForTwo: 259,
    tags: ['Desserts', 'Ice Cream'],
  },
]

export const recentOrders: Order[] = [
  {
    id: 'o1',
    restaurantName: 'Urban Pizza Co.',
    totalAmount: 689,
    status: 'DELIVERED',
    placedAt: new Date(Date.now() - 1000 * 60 * 60 * 20).toISOString(),
  },
  {
    id: 'o2',
    restaurantName: 'Spice Route Kitchen',
    totalAmount: 459,
    status: 'PLACED',
    placedAt: new Date(Date.now() - 1000 * 60 * 18).toISOString(),
  },
]

