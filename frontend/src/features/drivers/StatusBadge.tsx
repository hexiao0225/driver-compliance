type Status = 'VALID' | 'EXPIRING' | 'EXPIRED'

const styles: Record<Status, string> = {
  VALID: 'bg-green-100 text-green-800',
  EXPIRING: 'bg-yellow-100 text-yellow-800',
  EXPIRED: 'bg-red-100 text-red-800',
}

const labels: Record<Status, string> = {
  VALID: 'Valid',
  EXPIRING: 'Expiring Soon',
  EXPIRED: 'Expired',
}

export default function StatusBadge({ status }: { status: Status }) {
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${styles[status]}`}>
      {labels[status]}
    </span>
  )
}
