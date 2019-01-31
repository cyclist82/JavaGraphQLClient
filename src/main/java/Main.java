import graphQLClient.GraphQLClient;

import java.net.URL;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		GraphQLClient graphQLClient = new GraphQLClient();
		graphQLClient.setUrl(new URL("http://localhost:9000/graphql"));
		graphQLClient.addHeaders("Authorization", "Token " + System.getenv());

		// Here you need to insert a valid actor id
		// The Query creates a new Java Object with the data of the found Actor, will throw error without valid IDs
		Actor foundActor = graphQLClient.objectGraphQLQuery("query {   findActorByID    (id:\"<-- TODO insert valid actor id -->\") {id name movies {id title}}}", Actor.class);

		// Query creates a ArrayList of Actor Objects
		List<Actor> actors = graphQLClient.listGraphQLQuery("query {  allActors { id name movies{id title description}}}", Actor.class);

		// Mutation creates a new actor. You can give the Actor a name, will throw error without valid IDs
		Actor newActor = graphQLClient.objectGraphQLQuery("mutation {createActor(name:\"<-- TODO insert actor name -->\"){id name}}", Actor.class);

		// Sout the results
		System.out.println(foundActor);
		for (Actor actor : actors) {
			System.out.println(actor);
		}
		System.out.println(newActor);
	}


}
