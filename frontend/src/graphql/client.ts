import { ApolloClient, InMemoryCache } from '@apollo/client'

const uri = import.meta.env.VITE_API_URL
  ? `${import.meta.env.VITE_API_URL}/graphql`
  : '/graphql'

export const client = new ApolloClient({
  uri,
  cache: new InMemoryCache(),
})
