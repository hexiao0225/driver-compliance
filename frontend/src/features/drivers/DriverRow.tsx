import StatusBadge from './StatusBadge'

interface Driver {
  id: string
  name: string
  email: string | null
  status: 'VALID' | 'EXPIRING' | 'EXPIRED'
}

export default function DriverRow({ driver }: { driver: Driver }) {
  return (
    <tr className="hover:bg-gray-50">
      <td className="px-4 py-3 text-sm font-medium text-gray-900">{driver.name}</td>
      <td className="px-4 py-3 text-sm text-gray-500">{driver.email ?? '\u2014'}</td>
      <td className="px-4 py-3">
        <StatusBadge status={driver.status} />
      </td>
    </tr>
  )
}
