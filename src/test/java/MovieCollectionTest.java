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

    // getNumberOfMovies

    @Test
    void getNumberOfMovies_newCollectionIsEmpty() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertEquals(0, numberOfMovies);
    }

    // addMovie

    @Test
    void addMovie_numberIsUpdatedAfterAdd() {
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
    void addMovie_isKeptInCollection() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        collection.addMovie("IT",
                "Stephen King",
                1960,
                false,
                80,
                "Thriller");
        int movieCountPostAdd = collection.getNumberOfMovies();
        String firstTitle = collection.getMovies().getFirst().getTitle();

        // Assert
        assertEquals(1, movieCountPostAdd);
        assertEquals("IT", firstTitle);
    }

    @Test
    void addMovie_multiple() {
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
        int movieCountPostAdd = collection.getNumberOfMovies();
        String firstTitle = collection.getMovies().getFirst().getTitle();
        String lastTitle = collection.getMovies().getLast().getTitle();

        // Assert
        assertEquals(2, movieCountPostAdd);
        assertEquals("IT", firstTitle);
        assertEquals("Dune", lastTitle);
    }

    // searchMovies

    @Test
    void searchMovies_noMatchReturnsEmptyList() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("Nonexistent");

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void searchMovies_exactTitleMatchReturnsRightTitle() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("IT");
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals("IT", fullTitle);
    }

    @Test
    void searchMovies_lowerCaseTitleMatches() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("it");
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals("IT", fullTitle);
    }

    @Test
    void searchMovies_mixedCaseSearchMatches() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("iT");
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals("IT", fullTitle);
    }

    //Extra
    @Test
    void searchMovies_partialMatchReturnsOneMovie() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("I");
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals("IT", fullTitle);
    }

    @Test
    void searchMovies_partialMatchReturnsTwoMovies() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("Dune");
        int matchCount = results.size();

        // Assert
        assertEquals(2, matchCount);
    }

    @Test
    void searchMovies_emptySearchStringReturnsAllMovies() {
        // Act
        ArrayList<Movie> results = sharedCollection.searchMovies("");

        // Assert
        assertEquals(3, results.size());
    }

    // editMovie

    @Test
    void editMovie_directorIsKeptAndTrueIsReturned() {
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
    void editMovie_nonExistentMovieReturnsFalse() {
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

    // deleteMovie

    @Test
    void deleteMovie_goneFromCollectionAndTrueIsReturned() {
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

    @Test
    void deleteMovie_nonExistentMovieReturnsFalse() {
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
        boolean success = sharedCollection.deleteMovie(nonExistentMovie);
        int numberOfMovies = sharedCollection.getNumberOfMovies();

        // Assert
        assertFalse(success);
        assertEquals(3, numberOfMovies);
    }
}
