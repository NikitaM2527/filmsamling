import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    @Test
    void getDaysSinceWatched_countsAcrossMonths() {
        // Arrange
        Movie jaws = new Movie(
                "Jaws",
                "Steven Spielberg",
                1975,
                true,
                124,
                "Thriller");
        jaws.markAsWatched(LocalDate.of(2025, 9, 30));

        // Act
        long days = jaws.getDaysSinceWatched(LocalDate.of(2025, 10, 26));

        // Assert
        assertEquals(26, days);
    }
}