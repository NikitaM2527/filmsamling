package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03",
            "Jaws;Steven Spielberg;1975;true;124;Horror;",
    })
    void parseMovie_validMovies(String line) {
        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNotNull(movie);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Gekijouban Steins;Gate: Fuka ryouiki no dejavu;Hiroshi Hamasaki; 2013;true;90;Anime;2016-02-18",
            "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03;",
            "Jaws;Steven Spielberg;1975;true;124;Horror",
            "Test;Albert;1880;false;60;Drama;2026-11-03"
    })
    void parseMovie_invalidMovie(String line) {
        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNull(movie);
    }

    @Test
    void loadMovies_skipsInvalidLines() throws FileNotFoundException {
        // Arrange
        // movie.csv indeholder to gyldige film og to ugyldig film (Steins;Gate... indeholder 8 felter og returnerer null + Test... som er fra 1880)
        FileHandler fileHandler = new FileHandler("docs/movie.csv");

        // Act
        ArrayList<Movie> movies = fileHandler.loadMovies();

        // Assert
        assertEquals(2, movies.size());
        assertEquals(2, fileHandler.getSkippedLineCount());
    }

    @Test
    void saveMovies_saveToFileAndThenLoadFile() throws FileNotFoundException {
        // Arrange
        ArrayList<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie.Builder()
                .title("Dune: Part One")
                .director("Denis Villeneuve")
                .yearCreated(2021)
                .inColor(true)
                .lengthInMinutes(155)
                .genre("Sci-Fi")
                .lastWatched(LocalDate.of(2022, 11, 3))
                .build();

        Movie movie2 = new Movie.Builder()
                .title("Inception")
                .director("Christopher Nolan")
                .yearCreated(2010)
                .inColor(true)
                .lengthInMinutes(148)
                .genre("Sci-Fi")
                .lastWatched(LocalDate.of(2016, 10, 20))
                .build();

        Movie movie3 = new Movie.Builder()
                .title("Jaws")
                .director("Steven Spielberg")
                .yearCreated(1975)
                .inColor(true)
                .lengthInMinutes(124)
                .genre("Horror")
                // No lastWatched on purpose
                .build();

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        FileHandler fileHandler = new FileHandler("docs/test-movies.csv");

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(3, loadedMovies.size());
        assertEquals("Dune: Part One", loadedMovies.getFirst().getTitle());
        assertNull(loadedMovies.get(2).getLastWatched());
    }

    @Test
    void saveMovies_handlesEmptyList() throws FileNotFoundException {
        // Arrange
        ArrayList<Movie> movies = new ArrayList<>();
        FileHandler fileHandler = new FileHandler("docs/test-movies.csv");

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertTrue(loadedMovies.isEmpty());
    }

    @Test
    void saveMovies_overwriteExistingFile() throws FileNotFoundException {
        // Arrange
        FileHandler fileHandler = new FileHandler("docs/test-overwrite-movies.csv");

        ArrayList<Movie> firstMovies = new ArrayList<>();
        Movie movie1 = new Movie.Builder()
                .title("Dune: Part One")
                .director("Denis Villeneuve")
                .yearCreated(2021)
                .inColor(true)
                .lengthInMinutes(155)
                .genre("Sci-Fi")
                .build();

        firstMovies.add(movie1);

        ArrayList<Movie> secondMovies = new ArrayList<>();
        Movie movie2 = new Movie.Builder()
                .title("Jaws")
                .director("Steven Spielberg")
                .yearCreated(1975)
                .inColor(true)
                .lengthInMinutes(124)
                .genre("Horror")
                .build();

        secondMovies.add(movie2);

        // Act
        fileHandler.saveMovies(firstMovies);
        fileHandler.saveMovies(secondMovies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(1, loadedMovies.size());
        assertEquals("Jaws", loadedMovies.getFirst().getTitle());
    }

    @Test
    void saveMovies_handlesIfLastWatchedIsNull() throws FileNotFoundException {
        // Arrange
        ArrayList<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie.Builder()
                .title("Dune: Part One")
                .director("Denis Villeneuve")
                .yearCreated(2021)
                .inColor(true)
                .lengthInMinutes(155)
                .genre("Sci-Fi")
                .lastWatched(LocalDate.of(2022, 11, 3))
                .build();

        Movie movie2 = new Movie.Builder()
                .title("Jaws")
                .director("Steven Spielberg")
                .yearCreated(1975)
                .inColor(true)
                .lengthInMinutes(124)
                .genre("Horror")
                // No lastWatched on purpose
                .build();

        movies.add(movie1);
        movies.add(movie2);

        FileHandler fileHandler = new FileHandler("docs/test-movies.csv");

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(2, loadedMovies.size());
        assertEquals("Dune: Part One", loadedMovies.getFirst().getTitle());
        assertNull(loadedMovies.get(1).getLastWatched());
    }
}
