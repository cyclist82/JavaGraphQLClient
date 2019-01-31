# JavaGraphQLClient

A very basic implementation GraphQL implementation I wrote, as I need to send one GraphQL-Mutation to another Backend for a project I'm working on. 

This Main Program sends a GraphQL-query for a List of Actors, a GraphQL-query for a single Actor, and a GraphQL-mutation to create a Actor. It's designated to work with my little [GraphQL-SPQR-Demo Project](https://github.com/cyclist82/GraphQL-SPQR-Demo-Movies).

It therefor uses the methods the GraphQLClient class delivers. Either if you expect a single Object or a List of Objects as response to the Mutation of Query.
