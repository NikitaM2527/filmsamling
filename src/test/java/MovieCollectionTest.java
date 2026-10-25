import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieCollectionTest {

    @BeforeEach
    void setUp() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi");

        collection.addMovie("Dune 2",
                "Albert",
                2023,
                true,
                125,
                "Sci-Fi");

        collection.addMovie("IT",
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
    void collectionWithOneMovie () {
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

}
