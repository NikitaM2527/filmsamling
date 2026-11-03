package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.Movie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    @Test
    void parseMovie_validMovies() {
        // Arrange
        String line = "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03";

        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertEquals("Dune: Part One", movie.getTitle());
        assertEquals("Denis Villeneuve", movie.getDirector());
        assertEquals(2021, movie.getYearCreated());
        assertEquals(true, movie.isInColor());
        assertEquals(155, movie.getLengthInMinutes());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(LocalDate.of(2022, 11, 3), movie.getLastWatched());

    }

    @Test
    void parseMovie_invalidMovie() {
        // Arrange
        String line = "Gekijouban Steins;Gate: Fuka ryouiki no dejavu;Hiroshi Hamasaki; 2013;true;90;Anime;2016-02-18";

        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNull(movie);

    }
}
