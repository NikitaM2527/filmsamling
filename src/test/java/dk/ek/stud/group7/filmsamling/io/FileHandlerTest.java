package dk.ek.stud.group7.filmsamling.io;

import dk.ek.stud.group7.filmsamling.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUpTestFiles() throws IOException {
        Files.writeString(tempDir.resolve(TestFile.TWO_GOOD_ONE_BAD.fileName), """
                title|director|yearCreated|inColor|lengthInMinutes|genre|lastWatched
                Dune: Part One|Denis Villeneuve|2021|true|155|Sci-Fi|2022-11-03
                Jaws|Steven Spielberg|1975|true|124|Horror|
                Invalid Movie|Christopher Nolan|1800|true|120|Drama|""");
    }

    private String getTestFileName(@SuppressWarnings("SameParameterValue") TestFile testFile) {
        return tempDir.resolve(testFile.fileName).toString();
    }

    private String getTempFileName(String fileName) {
        return tempDir.resolve(fileName).toString();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Dune: Part One|Denis Villeneuve|2021|true|155|Sci-Fi|2022-11-03",
            "Jaws|Steven Spielberg|1975|true|124|Horror|",
            "Gekijouban Steins;Gate: Fuka ryouiki no dejavu|Hiroshi Hamasaki|2013|true|90|Anime|2016-02-18",
    })
    void parseMovie_validMovies(String line) {
        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNotNull(movie);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03",
            "Dune: Part One|Denis Villeneuve|2021|true|155|Sci-Fi|2022-11-03|",
            "Jaws|Steven Spielberg|1975|true|124|Horror",
            "Test|Albert|1880|false|60|Drama|2026-11-03"
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
        FileHandler fileHandler = new FileHandler(getTestFileName(TestFile.TWO_GOOD_ONE_BAD));

        // Act
        ArrayList<Movie> movies = fileHandler.loadMovies();

        // Assert
        assertEquals(2, movies.size());
        assertEquals(1, fileHandler.getSkippedLineCount());
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

        FileHandler fileHandler = new FileHandler(getTempFileName("saveToFileAndThenLoadFile"));

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
        FileHandler fileHandler = new FileHandler(getTempFileName("handlesEmptyList"));

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertTrue(loadedMovies.isEmpty());
    }

    @Test
    void saveMovies_overwriteExistingFile() throws FileNotFoundException {
        // Arrange
        FileHandler fileHandler = new FileHandler(getTempFileName("overwriteExistingFile"));

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

        FileHandler fileHandler = new FileHandler(getTempFileName("handlesIfLastWatchedIsNull"));

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(2, loadedMovies.size());
        assertEquals("Dune: Part One", loadedMovies.getFirst().getTitle());
        assertNull(loadedMovies.get(1).getLastWatched());
    }

    enum TestFile {
        TWO_GOOD_ONE_BAD("2good1bad");

        public final String fileName;

        TestFile(String fileName) {
            this.fileName = fileName;
        }
    }
}
