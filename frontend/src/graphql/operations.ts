import { gql } from '@apollo/client'

export const GET_DRIVERS = gql`
  query GetDrivers {
    drivers {
      id
      name
      email
      status
    }
  }
`

export const ADD_DRIVER = gql`
  mutation AddDriver($name: String!, $email: String) {
    addDriver(name: $name, email: $email) {
      id
      name
      email
      status
    }
  }
`
