import { ApolloProvider } from '@apollo/client'
import { client } from './graphql/client'
import DriverListPage from './features/drivers/DriverListPage'

export default function App() {
  return (
    <ApolloProvider client={client}>
      <div className="min-h-screen bg-gray-50">
        <header className="bg-white border-b border-gray-200 px-6 py-4">
          <h1 className="text-xl font-semibold text-gray-900">Driver Compliance</h1>
        </header>
        <main className="max-w-3xl mx-auto px-6 py-8">
          <DriverListPage />
        </main>
      </div>
    </ApolloProvider>
  )
}
