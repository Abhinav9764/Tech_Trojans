import type { NavigateFunction } from 'react-router-dom'

export function scrollElementIntoViewSmooth(elementId: string) {
  const el = document.getElementById(elementId)
  el?.scrollIntoView({ behavior: 'smooth', block: 'start' })
}

/**
 * Go to home and scroll to a section. Hash links are unreliable with client-side routing.
 */
export function goToHomeSection(navigate: NavigateFunction, pathname: string, sectionId: string) {
  if (pathname === '/') {
    scrollElementIntoViewSmooth(sectionId)
    return
  }
  navigate('/', { state: { scrollToSection: sectionId } })
}
