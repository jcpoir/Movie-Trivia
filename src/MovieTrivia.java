
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		// REMOVE THESE
		String[] movies = new String[3];
		MovieDB movieDB = new MovieDB();	
		Scanner sc = new Scanner(System.in);
		boolean done = false;
		
		while (done == false) {
			
			String name = sc.nextLine();	
			mt.insertActor(name, movies, movieDB.getActorsInfo());
		}
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		
		
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	/**
	 * Insert an actor into actorsInfo
	 * If actor is already in the arraylist, appends movies without duplicates
	 * @param actor String name of an actor
	 * @param movies String array movie names
	 * @param actorsInfo Arraylist of actor Info
	 */
	public void insertActor (String actor, String[] movies, ArrayList <Actor> actorsInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String actorClean = actor.toLowerCase().strip();
		
		// create new actor with arg specified name
		Actor argActorInfo = new Actor(actorClean);
		
		if (movies.length >= 1) {
			
			// initialize movies list, which must first be converted to an ArrayList
			ArrayList<String> moviesList = new ArrayList<String>(Arrays.asList(movies));
			
			// trim and lowercase input movie array
			for (int i = 0; i < moviesList.size(); i++) {
				
				// allow for null entries
				if (moviesList.get(i) != null) {
					moviesList.set(i, moviesList.get(i).trim());
				}
			}
					
			argActorInfo.setMoviesCast(moviesList);
			
			// check if actor is in actorsInfo already
			boolean done = false;
			
			// loop through actor info to see if actor name is in list.
			for (Actor actorInfo : actorsInfo) {
				
				// check if actor names are equal. If so, append actor movie list and set done to true
				if (argActorInfo.equals(actorInfo)) {
					
					// concatenate movie lists and add info to actor
					ArrayList <String> oldMoviesList = argActorInfo.getMoviesCast();
					oldMoviesList.addAll(actorInfo.getMoviesCast());
					actorInfo.setMoviesCast(removeRepeats(oldMoviesList));
					
					// indicate that info has been updated and break out (stop looking for name match)
					done = true;	
					break;
				}			
			}
			
			// if done is false here, that means that the actor in question was not in actorsInfo
			if (done == false) {
				
				// if that's the case, add the new actor instance to the arrayList
				actorsInfo.add(argActorInfo);
			}
		}
		
		else {
			
			if (actorsInfo.contains(argActorInfo) == false) {
				actorsInfo.add(argActorInfo);
			}			
			
		}
	}
	
	/**
	 * Inserts/updates movie ratings for specified movie
	 * @param movie String name of a movie
	 * @param ratings int array, length two, critic rating, audience rating
	 * @param moviesInfo ArrayList of Movie type, a list of movie instances
	 */
	public void insertRating(String movie, int[] ratings, ArrayList <Movie> moviesInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String movieClean = movie.toLowerCase().strip();
		
		// track whether update has occured yet
		boolean done = false;
		
		// check if ratings is the correct length. if not, function does nothing
		if (ratings.length == 2) {
			
			// get critic and audience ratings from ratings array
			int criticRating = ratings[0];
			int audienceRating = ratings[1];
			
			// iterate through moveInfo until movie is found
			for (Movie movieInfo : moviesInfo) {
				
				// if names are the same, reset ratings
				if (movieInfo.getName().equals(movieClean)) {
					
					// set criticRating, audienceRating
					movieInfo.setCriticRating(criticRating);
					movieInfo.setAudienceRating(audienceRating);
					
					break;
				}
			}
			
			// if movie hasn't been updated yet, that means it isn't in the arraylist
			if (done == false) {
				
				// define new movie instance and add to the end
				Movie newMovie = new Movie(movie, criticRating, audienceRating);
				moviesInfo.add(newMovie);
			}
		}
	}
	
	/**
	 * Returns all movies made by a given actor
	 * @param actor String, name of an actor
	 * @param actorsInfo ArrayList of actors
	 * @return result, an ArrayList of movie titles
	 */
	public ArrayList <String> selectWhereActorIs(String actor, ArrayList <Actor> actorsInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String actorClean = actor.toLowerCase().strip();
		
		// define output arraylist
		ArrayList<String> result = new ArrayList<String>();
		
		// iterate through actors looking for a name match
		for (Actor actorInfo: actorsInfo) {
			// when name match is found, get and store movies cast
			if (actorInfo.getName().equals(actorClean)) {
				result = actorInfo.getMoviesCast();
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Get list of all actors that were involved in a specified movie
	 * @param movie String name of a movie
	 * @param actorsInfo arraylist of Actor instances
	 * @return result, an ArrayList of String movie titles
	 */
	public ArrayList <String> selectWhereMovieIs(String movie, ArrayList <Actor> actorsInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String movieClean = movie.toLowerCase().strip();
		
		// define output arraylist
		ArrayList<String> result = new ArrayList<String>();

		// iterate through actors looking for a the movie title
		for (Actor actorInfo: actorsInfo) {
			
			// when name match is found, get and store ratings
			if (actorInfo.getMoviesCast().contains(movieClean)) {
				
				// add the two ratings to the result in the order critic, audience
				result.add(actorInfo.getName());
			}
		}
		
		return result;
		
	}

	/**
	 * Get a list of movie titles where rating is greater than, equal to, or less than a target rating
	 * @param comparison char, represents a method of comparison
	 * @param targetRating int, threshold for comparison
	 * @param isCritic boolean, should critic scores be used
	 * @param moviesInfo arraylist of movies
	 * @return
	 */
	public ArrayList <String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList<Movie> moviesInfo){
		
		// define output array
		ArrayList<String> result = new ArrayList<String>();
		
		// define boolean conditions
		
		// assign variables to user defined comparison arg
		boolean greaterThan = (comparison == '>');
		boolean equalTo = (comparison == '=');
		boolean lessThan = (comparison == '<');
		
		// iterate through movies looking for condition to be met relative to targetRating
		for (Movie movieInfo: moviesInfo) {
			
			int actualRating;
			// get appropriate rating based on args
			if (isCritic) {
				actualRating = movieInfo.getCriticRating();
			}
			
			else {
				actualRating = movieInfo.getAudienceRating();
			}
			
			// if rating is greater and should be or equal to and should be or less than an should be, then add the term
			if (((actualRating > targetRating) && greaterThan) || ((actualRating == targetRating) && equalTo) || ((actualRating < targetRating) && lessThan)) {
				result.add(movieInfo.getName());
			}
		}
		
		return result;
	}
	
	/**
	 * Gets all the actors that an actor has worked with
	 * @param actor String name of an actor
	 * @param actorsInfo a list of actor instances
	 * @return String arrayList of co-actors
	 */
	public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String actorClean = actor.toLowerCase().strip();
		
		// get list of all movies that actor was involved in
		ArrayList<String> movies = selectWhereActorIs(actorClean, actorsInfo);
		
		// define output list
		ArrayList<String> result = new ArrayList<String>();
		
		// aggregate a list of all co-stars with repeats
		for (String movie: movies) {
			
			// get all actors
			ArrayList<String> actors = selectWhereMovieIs(movie, actorsInfo);
			
			// add all to result matrix
			result.addAll(actors);
		}
		
		// remove all instances of the arg actor in the final set
		while (result.remove(actorClean)) {}
		// removes repeats
		result = removeRepeats(result);
		
		return result;
	}
	
	/**
	 * Get movies that two actors have in common
	 * @param actor1 String name of one actor
	 * @param actor2 String name of another actor
	 * @param actorsInfo list of actor info
	 * @return movies that both were in
	 */
	public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo) {
		
		// remove whitespace and send all letters to lowercase for the sake of comparison
		String actorClean1 = actor1.toLowerCase().strip();
		String actorClean2 = actor2.toLowerCase().strip();
		
		// get movies where each actor was cast
		ArrayList<String> movies1 = selectWhereActorIs(actorClean1, actorsInfo);
		ArrayList<String> movies2 = selectWhereActorIs(actorClean2, actorsInfo);
		
		ArrayList<String> result = new ArrayList<String>();
		// iterate through movies1 while checking if they're in movies2
		for (String movie: movies1) {
			
			// if both actors are in a movie, add the movie to output
			if (movies2.contains(movie)) {
				result.add(movie);
			}
		}
		
		return result;
	}
	
	/**
	 * Gets a list of movies that critics and audiences gave a score of >85
	 * @param moviesInfo a list of movies
	 * @return a String list of movie titles
	 */
	public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo) {
		
		// get movies that critics, audience liked (>85)
		ArrayList<String> criticGoodMovies = selectWhereRatingIs('>', 84, true, moviesInfo);
		ArrayList<String> audienceGoodMovies = selectWhereRatingIs('>', 84, false, moviesInfo);
		
		// return movies that are common between the two groups
		return intersect(criticGoodMovies, audienceGoodMovies);
		
	}
	
	/**
	 * Returns a list of actors that are common between two films
	 * @param movie1 String title of a movie
	 * @param movie2 String title of another movie
	 * @param actorsInfo a list of actors
	 * @return 
	 */
	public ArrayList <String> getCommonActors (String movie1, String movie2, ArrayList <Actor> actorsInfo) {
		
		// get lists of actors in both movies
		ArrayList<String> actors1 = selectWhereMovieIs(movie1, actorsInfo);
		ArrayList<String> actors2 = selectWhereMovieIs(movie2, actorsInfo);
		
		// return actors in common
		return intersect(actors1, actors2);
	}

	public static double [] getMean (ArrayList <Movie> moviesInfo) {
		
		double sumCriticRatings = 0;
		double sumAudienceRatings = 0;
		
		for (Movie movie: moviesInfo) {
			sumCriticRatings += movie.getCriticRating();
			sumAudienceRatings += movie.getAudienceRating();
		}
		
		int length = moviesInfo.size();
		
		double avgCriticRating = sumCriticRatings/length;
		double avgAudienceRating = sumAudienceRatings/length;
		
		double[] result = {avgCriticRating, avgAudienceRating};
		
		return result;
	}
	
	/**
	 * Checks to see if an actor is within an actor arraylist
	 * @param actor an actor instance
	 * @param actorList an arraylist of actors
	 * @return result a boolean indicating if actor is within actorlist
	 */
	public boolean isWithin(Actor actor, ArrayList <Actor> actorList) {
		
		boolean result = false;
		
		// for each actor, check if names are same
		for (Actor actorIter : actorList) {
			// if names are same, indicate that result is true and stop looking
			if (actorIter.getName().equals(actor.getName())) {
				result = true;
				break;
			}
			// if names never match, result will stay as false
		}	
		return result;
	}
	
	/**
	 * Checks to see if a string is within a string arraylist
	 * @param term, a string
	 * @param strList an arraylist of strings
	 * @return result a boolean indicating if actor is within actorlist
	 */
	public boolean isWithin(String term, ArrayList <String> strList) {
		
		boolean result = false;
		
		// for each actor, check if names are same
		for (String strIter : strList) {
			// if names are same, indicate that result is true and stop looking
			if (strIter.equals(term)) {
				result = true;
				break;
			}
			// if names never match, result will stay as false
		}	
		return result;
	}
	
	/**
	 * Checks to see if a string is within a string arraylist and returns index
	 * @param term, a string
	 * @param strList an arraylist of strings
	 * @return result an integer indicating the position of the string in the arraylist
	 */
	public int index(String term, ArrayList <String> strList) {
		
		// if position isn't found, method will return -1
		int result = -1;
		
		// for each actor, check if names are same
		for (int i = 0; i < strList.size(); i++) {
			// if names are same, indicate that result is true and stop looking
			if (strList.get(i).equals(term)) {
				result = i;
				break;
			}
			// if names never match, result will stay as false
		}	
		return result;
	}

	/**
	 * returns the intersection of two ArrayLists
	 * @param list1 a string arraylist
	 * @param list2 a string arraylist
	 * @return a string arraylist of common terms
	 */
	public ArrayList<String> intersect(ArrayList<String> list1, ArrayList<String> list2) {
		
		// define output array
		ArrayList<String> result = new ArrayList<String>();
		
		// for each item in list1, check if also in list2
		for (String item: list1) {
			if (list2.contains(item)) {
				result.add(item);
			}
		}
		
		return result;
	}
	
	/**
	 * Takes a string list as an input and returns the same list without repeats
	 * @param strList, a string ArrayList
	 * @return outList, a string ArrayList without repeats
	 */
	public ArrayList<String> removeRepeats(ArrayList <String> strList) {
		
		// define outlist, the ArrayList to be returned
		ArrayList <String> outList = new ArrayList<String>();
		
		// iterate through strList, checking to see if the term is in the list before adding
		for (String str : strList) {
			// if the given term is not yet in the output, add it.
			if (outList.contains(str) != true) {
				outList.add(str);
			}
		}
		
		return outList;
		
	}
}

