import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @org.junit.jupiter.api.Test
    void setTitle() {
        // Arrange
        Movie movie = new Movie(
                "Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi"
        );
        String newTitle = "Dune 2";

        // Act
        movie.setTitle(newTitle);

        // Assert
        assertEquals(newTitle, movie.getTitle());
    }
}