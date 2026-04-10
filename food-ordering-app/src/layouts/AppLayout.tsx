import { Outlet } from 'react-router-dom'
import { Navbar } from '../components/Navbar'
import { SearchProvider } from '../contexts/SearchContext'

export function AppLayout() {
  return (
    <SearchProvider>
      <div className="min-h-dvh bg-zinc-50">
        <Navbar />
        <main className="container-app py-8">
          <Outlet />
        </main>
      </div>
    </SearchProvider>
  )
}

