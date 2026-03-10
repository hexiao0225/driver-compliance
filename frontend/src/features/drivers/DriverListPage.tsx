import { useQuery } from '@apollo/client'
import { GET_DRIVERS } from '../../graphql/operations'
import DriverRow from './DriverRow'
import AddDriverForm from './AddDriverForm'

export default function DriverListPage() {
  const { data, loading, error } = useQuery(GET_DRIVERS)

  if (loading) {
    return <p className="text-sm text-gray-500">Loading drivers...</p>
  }

  if (error) {
    return <p className="text-sm text-red-600">Error: {error.message}</p>
  }

  return (
    <div>
      <h2 className="text-lg font-semibold text-gray-800 mb-4">Drivers</h2>
      <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
              <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
              <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">License Status</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-100">
            {data.drivers.length === 0 ? (
              <tr>
                <td colSpan={3} className="px-4 py-6 text-center text-sm text-gray-400">No drivers found.</td>
              </tr>
            ) : (
              data.drivers.map((driver: any) => (
                <DriverRow key={driver.id} driver={driver} />
              ))
            )}
          </tbody>
        </table>
      </div>
      <AddDriverForm />
    </div>
  )
}
