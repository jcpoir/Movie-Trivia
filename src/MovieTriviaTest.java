
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;


class MovieTriviaTest {
	
	//instance of movie trivia object to test
	MovieTrivia mt;
	//instance of movieDB object
	MovieDB movieDB = new MovieDB();
	
	@BeforeEach
	void setUp() throws Exception {
		//initialize movie trivia object
		mt = new MovieTrivia ();
		
		//set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		//set up movieDB object
		movieDB.setUp("moviedata.txt", "movieratings.csv");
	}

	@Test
	void testSetUp() { 
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}
	
	@Test
	void testInsertActor () {
		mt.insertActor("test1", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());	
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		
		// TODO add additional test case scenarios
		mt.insertActor(" aaAAA	", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());
		assertEquals("aaaaa", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		mt.insertActor("loser", new String [] {}, movieDB.getActorsInfo());
		assertEquals(9, movieDB.getActorsInfo().size());
		assertEquals("loser", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
	}
	
	@Test
	void testInsertRating () {
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
		// TODO add additional test case scenarios
		mt.insertRating("one test", new int [] {100, 17}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals("one test", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		mt.insertRating("a second", new int [] {100, 17}, movieDB.getMoviesInfo());
		assertEquals(10, movieDB.getMoviesInfo().size());	
		assertEquals(100, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(17, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
	}
	
	@Test
	void testSelectWhereActorIs () {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		// TODO add additional test case scenarios
		assertEquals("the post", mt.selectWhereActorIs("tom Hanks", movieDB.getActorsInfo()).get(0));
		assertEquals("catch me if you can", mt.selectWhereActorIs("	tom HANKs", movieDB.getActorsInfo()).get(1));
		assertEquals("fight club", mt.selectWhereActorIs("brad pitt", movieDB.getActorsInfo()).get(1));
		assertEquals("seven", mt.selectWhereActorIs("	braD PITT", movieDB.getActorsInfo()).get(0));
	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO add additional test case scenarios
		assertEquals(true, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(true, mt.selectWhereMovieIs("catch me if you can", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(false, mt.selectWhereMovieIs("catch me if you can", movieDB.getActorsInfo()).contains("brandon krakowsky"));
		assertEquals(true, mt.selectWhereMovieIs("fight club", movieDB.getActorsInfo()).contains("brad pitt"));
		
	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		// TODO add additional test case scenarios
		assertEquals(7, mt.selectWhereRatingIs('<', 100, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('<', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(5, mt.selectWhereRatingIs('>', 30, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 80, true, movieDB.getMoviesInfo()).size());
		
	}
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO add additional test case scenarios
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("brad pitt"));
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("amy adams"));
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("robin williams"));
		assertTrue(mt.getCoActors("brad pitt", movieDB.getActorsInfo()).contains("amy adams"));
	}
	
	@Test
	void testGetCommonMovie () {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		// TODO add additional test case scenarios
		assertTrue(mt.getCommonMovie("amy adams", "tom hanks", movieDB.getActorsInfo()).contains("test"));
		assertTrue(mt.getCommonMovie("amy adams", "robin williams", movieDB.getActorsInfo()).contains("test"));
		assertTrue(mt.getCommonMovie("brad pitt", "tom hanks", movieDB.getActorsInfo()).contains("test"));
		assertTrue(mt.getCommonMovie("robin williams", "tom hanks", movieDB.getActorsInfo()).contains("test"));
	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		// TODO add additional test case scenarios
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("rocky ii"));
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("Wall-E"));
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("the incredibles"));
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		// TODO add additional test case scenarios
		assertEquals(4, mt.getCommonActors("test", "test", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("test", "test", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCommonActors("test", "test", movieDB.getActorsInfo()).contains("robin williams"));
		assertTrue(mt.getCommonActors("test", "test", movieDB.getActorsInfo()).contains("amy adams"));
	}
	
	@Test
	void testGetMean () {
		
		// TODO add ALL test case scenarios!
		mt.insertRating("Jellybeans", new int[] {33, 34}, movieDB.getMoviesInfo());
		assertEquals(mt.getMean(movieDB.getMoviesInfo())[0], 63.5);
		assertEquals(mt.getMean(movieDB.getMoviesInfo())[1], 61.75);
		
		mt.insertRating("Jellybeans", new int[] {131,1135}, movieDB.getMoviesInfo());
		assertEquals(mt.getMean(movieDB.getMoviesInfo())[0], 71.0);
		assertEquals(mt.getMean(movieDB.getMoviesInfo())[1], 181);
	}
}
