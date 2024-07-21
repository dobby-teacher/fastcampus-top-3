// src/apollo.js
import {ApolloClient, InMemoryCache, createHttpLink, split} from '@apollo/client/core';
import { setContext } from '@apollo/client/link/context';
import { GraphQLWsLink } from "@apollo/client/link/subscriptions";
import {getMainDefinition} from "@apollo/client/utilities";
import { createClient } from "graphql-ws";

const httpLink = createHttpLink({
    uri: 'http://localhost:9000/graphql',
});

const wsLink = new GraphQLWsLink(
    createClient({
        url: "ws://localhost:9000/graphql",
        options: {
            reconnect: true,
        },
    })
);

const authLink = setContext((_, { headers }) => {
    const token = localStorage.getItem('jwt');
    return {
        headers: {
            ...headers,
            authorization: token ? `Bearer ${token}` : '',
        },
    };
});

const link = split(
    ({ query }) => {
        const definition = getMainDefinition(query);
        return (
            definition.kind === 'OperationDefinition' &&
            definition.operation === 'subscription'
        );
    },
    wsLink,
    authLink.concat(httpLink),
);

const apolloClient = new ApolloClient({
    link: link,
    cache: new InMemoryCache(),
});

export default apolloClient;
