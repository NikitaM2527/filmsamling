import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieCollectionTest {

    private MovieCollection searchCollection;

    @BeforeEach
    void setUp() {
        searchCollection = new MovieCollection();


        searchCollection.addMovie("Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi");

        searchCollection.addMovie("Dune 2",
                "Albert",
                2023,
                true,
                125,
                "Sci-Fi");

        searchCollection.addMovie("IT",
                "Stephen King",
                1960,
                false,
                80,
                "Thriller");
    }

    /* addMovie
    1) Tilføj én film
    2) Korrekt data lagring (getMovies)
     */


    @Test
    void addingMoviesCorrectly() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        collection.addMovie("IT",
                "Stephen King",
                1960,
                false,
                80,
                "Thriller");

        // Assert
        assertEquals(1, collection.getNumberOfMovies());
        assertEquals("IT", collection.getMovies().getFirst().getTitle());
    }

    @Test
    void addingMultipleMovies() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        collection.addMovie("IT",
                "Stephen King",
                1960,
                false,
                80,
                "Thriller");
        collection.addMovie("Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi");

        // Assert
        assertEquals(2, collection.getNumberOfMovies());
        assertEquals("IT", collection.getMovies().getFirst().getTitle());
        assertEquals("Dune", collection.getMovies().getLast().getTitle());
    }

    @Test
    void collectionWithOneMovie() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        collection.addMovie("IT",
                "Stephen King",
                1960,
                false,
                80,
                "Thriller");
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertEquals(1, numberOfMovies);
    }

    @Test
    void emptyCollection() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertEquals(0, numberOfMovies);
    }

    //searchMovies() - Testing

    @Test
    void searchMovies_noMatch() {

        ArrayList<Movie> results = searchCollection.searchMovies("Nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void searchMovies_exactTitleMatch() {
        ArrayList<Movie> results = searchCollection.searchMovies("IT");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    @Test
    void searchMovies_multipleMatches() {
        ArrayList<Movie> results = searchCollection.searchMovies("Dune");
        assertEquals(2, results.size());
    }

    @Test
    void searchMovies_caseInsensitive() {
        ArrayList<Movie> results = searchCollection.searchMovies("it");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    //Extra
    @Test
    void searchMovies_partialMatch() {
        ArrayList<Movie> results = searchCollection.searchMovies("I");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    @Test
    void searchMovies_emptySearchString() {
        ArrayList<Movie> results = searchCollection.searchMovies("");
        assertEquals(3, results.size());
    }

    @Test
    void searchMovies_mixedCaseSearch() {
        ArrayList<Movie> results = searchCollection.searchMovies("iT");
        assertEquals("IT", results.getFirst().getTitle());
    }

}
