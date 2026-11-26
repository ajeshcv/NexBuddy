export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api/v1'

export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'nexbuddy_access_token',
  REFRESH_TOKEN: 'nexbuddy_refresh_token',
  USER: 'nexbuddy_user',
}

export const ACTIVITY_CATEGORIES = [
  { name: 'Travel Buddy', icon: '✈️', description: 'Find travel companions for trips and weekend getaways.' },
  { name: 'Study Buddy', icon: '📚', description: 'Connect with study partners and project teammates.' },
  { name: 'Gaming Buddy', icon: '🎮', description: 'Team up for multiplayer matches and esports.' },
  { name: 'Movie Buddy', icon: '🎬', description: 'Discover people to watch movies and discuss cinema.' },
  { name: 'Food & Tea Buddy', icon: '☕', description: 'Meet for chai, coffee, or exploring new restaurants.' },
  { name: 'Sports & Gym Buddy', icon: '⚽', description: 'Join football, cricket, running, or workout sessions.' },
]
