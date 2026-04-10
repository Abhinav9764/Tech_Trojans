import { Link, useLocation, useNavigate } from 'react-router-dom'
import type { LucideIcon } from 'lucide-react'
import { goToHomeSection } from '../lib/navigation'

const cardClassName =
  'group block w-full rounded-3xl border border-zinc-200 bg-white p-5 text-left shadow-sm transition hover:-translate-y-0.5 hover:shadow-soft focus:outline-none focus:ring-4 focus:ring-orange-100'

type QuickAccessCardProps = {
  title: string
  description: string
  icon: LucideIcon
} & (
  | { kind: 'route'; to: string }
  | { kind: 'section'; sectionId: string }
)

export function QuickAccessCard(props: QuickAccessCardProps) {
  const navigate = useNavigate()
  const location = useLocation()
  const { title, description, icon: Icon } = props

  if (props.kind === 'section') {
    return (
      <button
        type="button"
        className={cardClassName}
        onClick={() => goToHomeSection(navigate, location.pathname, props.sectionId)}
      >
        <div className="flex items-start gap-4">
          <span className="grid size-11 place-items-center rounded-2xl bg-orange-50 text-orange-600 transition group-hover:bg-orange-100">
            <Icon className="size-5" />
          </span>
          <div className="min-w-0">
            <div className="text-sm font-semibold text-zinc-900">{title}</div>
            <div className="mt-1 text-xs text-zinc-500">{description}</div>
          </div>
        </div>
      </button>
    )
  }

  return (
    <Link to={props.to} className={cardClassName}>
      <div className="flex items-start gap-4">
        <span className="grid size-11 place-items-center rounded-2xl bg-orange-50 text-orange-600 transition group-hover:bg-orange-100">
          <Icon className="size-5" />
        </span>
        <div className="min-w-0">
          <div className="text-sm font-semibold text-zinc-900">{title}</div>
          <div className="mt-1 text-xs text-zinc-500">{description}</div>
        </div>
      </div>
    </Link>
  )
}
