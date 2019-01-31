import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class Actor {

	private String id;
	private String name;
	private List<Movie> movies=new ArrayList<Movie>();

	@JsonGetter
	public String getId() {
		return id;
	}

	@JsonSetter
	public void setId(String id) {
		this.id = id;
	}

	@JsonGetter
	public String getName() {
		return name;
	}

	@JsonSetter
	public void setName(String name) {
		this.name = name;
	}

	@JsonGetter
	public List<Movie> getMovies() {
		return movies;
	}

	@JsonSetter
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "To String Methode für Actor{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", movies=" + movies +
				'}';
	}
}
