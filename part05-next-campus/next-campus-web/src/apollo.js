// src/apollo.js
import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client/core';
import { setContext } from '@apollo/client/link/context';
import { createApolloProvider } from '@vue/apollo-option';

const httpLink = createHttpLink({
    uri: 'http://localhost:9000/graphql',
});

const authLink = setContext((_, { headers }) => {
    const token = localStorage.getItem('jwt');
    return {
        headers: {
            ...headers,
            authorization: token ? `Bearer ${token}` : '',
        },
    };
});

const apolloClient = new ApolloClient({
    link: authLink.concat(httpLink),
    cache: new InMemoryCache(),
});

export const apolloProvider = createApolloProvider({
    defaultClient: apolloClient,
});

export default apolloClient;
