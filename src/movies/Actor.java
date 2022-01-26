package movies;

import java.util.ArrayList;

/**
 * Represents an Actor with name and list of movies the actor has acted in.
 */
public class Actor {
	
	/**
	 * Name of actor.
	 */
	private String name;
	
	/**
	 * Movies the actor has acted in.
	 */
	private ArrayList <String> moviesCasted = new ArrayList <String> ();
	
	/**
	 * Creates Actor with given name.
	 * @param name of actor
	 */
	public Actor (String name) {
		this.name = name;
	}
	
	/**
	 * Set actor name
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name of the actor
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set movies casted
	 * @param moviesList String array
	 */
	public void setMoviesCast(ArrayList<String> moviesList) {
		this.moviesCasted = moviesList;
	}
	/**
	 * @return the movies the actor has acted in
	 */
	public ArrayList<String> getMoviesCast() {
		return moviesCasted;
	}
	
	/**
	 * Returns String containing the actor's name and the list of movies the actor has acted in.
	 */
	@Override
	public String toString () {
		return "Name: " + name + " Acted in: " + moviesCasted.toString(); 
	}
	
	/**
	 * Checks if actors are same based on their names
	 * @param actor
	 * @return
	 */
	public boolean equals(Actor actor) {
		
		boolean isSame;
		
		// if actor name is same as this actor's name, consider them to be equal
		if (this.name.equals(actor.getName())) {
			isSame = true;
		}
		
		// otherwise, consider actor object to not be equal.
		else {
			isSame = false;
		}
		
		return isSame;
	}
	

}
