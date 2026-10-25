import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MovieCollectionTest {
    private MovieCollection sharedCollection;

    @BeforeEach
    void setUp() {
        sharedCollection = new MovieCollection();

        sharedCollection.addMovie("Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi");

        sharedCollection.addMovie("Dune 2",
                "Albert",
                2023,
                true,
                125,
                "Sci-Fi");

        sharedCollection.addMovie("IT",
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

        ArrayList<Movie> results = sharedCollection.searchMovies("Nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void searchMovies_exactTitleMatch() {
        ArrayList<Movie> results = sharedCollection.searchMovies("IT");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    @Test
    void searchMovies_multipleMatches() {
        ArrayList<Movie> results = sharedCollection.searchMovies("Dune");
        assertEquals(2, results.size());
    }

    @Test
    void searchMovies_caseInsensitive() {
        ArrayList<Movie> results = sharedCollection.searchMovies("it");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    //Extra
    @Test
    void searchMovies_partialMatch() {
        ArrayList<Movie> results = sharedCollection.searchMovies("I");
        assertEquals(1, results.size());
        assertEquals("IT", results.getFirst().getTitle());
    }

    @Test
    void searchMovies_emptySearchString() {
        ArrayList<Movie> results = sharedCollection.searchMovies("");
        assertEquals(3, results.size());
    }

    @Test
    void searchMovies_mixedCaseSearch() {
        ArrayList<Movie> results = sharedCollection.searchMovies("iT");
        assertEquals("IT", results.getFirst().getTitle());
    }

    @Test
    void editedDirectorIsKeptAndTrueIsReturned() {
        // Arrange
        Movie itMovie = sharedCollection.searchMovies("IT").getFirst();

        // Act
        boolean success = sharedCollection.editMovie(
                itMovie,
                itMovie.getTitle(),
                "Me",
                itMovie.getYearCreated(),
                itMovie.isInColor(),
                itMovie.getLengthInMinutes(),
                itMovie.getGenre());
        itMovie = sharedCollection.searchMovies("IT").getFirst();
        String newDirector = itMovie.getDirector();

        // Assert
        assertTrue(success);
        assertEquals("Me", newDirector);
    }

    @Test
    void editingNonExistentMovieChangesNothingAndFalseIsReturned() {
        // Arrange
        String title = "Placeholder";
        String director = "Nobody";
        int yearCreated = 2000;
        boolean inColor = false;
        int lengthInMinutes = 60;
        String genre = "Avant-Garde";
        Movie nonExistentMovie = new Movie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre);

        // Act
        boolean success = sharedCollection.editMovie(nonExistentMovie,
                "Foobar",
                "Somebody",
                2001,
                true,
                90,
                "Run-of-the-mill");

        // Assert
        assertFalse(success);
        assertEquals(title, nonExistentMovie.getTitle());
        assertEquals(director, nonExistentMovie.getDirector());
        assertEquals(yearCreated, nonExistentMovie.getYearCreated());
        assertEquals(inColor, nonExistentMovie.isInColor());
        assertEquals(lengthInMinutes, nonExistentMovie.getLengthInMinutes());
        assertEquals(genre, nonExistentMovie.getGenre());
    }

    @Test
    void deletedMovieIsRemovedFromTheCollectionAndTrueIsReturned() {
        // Arrange
        Movie itMovie = sharedCollection.searchMovies("IT").getFirst();

        // Act
        int preDeleteMovieCount = sharedCollection.getNumberOfMovies();
        boolean success = sharedCollection.deleteMovie(itMovie);
        int postDeleteMovieCount = sharedCollection.getNumberOfMovies();

        // Assert
        assertTrue(success);
        assertEquals(3, preDeleteMovieCount);
        assertEquals(2, postDeleteMovieCount);
    }
}
