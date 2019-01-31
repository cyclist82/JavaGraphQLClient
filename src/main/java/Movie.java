import java.util.ArrayList;
import java.util.List;

public class Movie {

	private String id;
	private String title;
	private String description;
	private List<Actor> actors = new ArrayList<>();
	private String director;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", actors=" + actors +
				", director='" + director + '\'' +
				'}';
	}
}
