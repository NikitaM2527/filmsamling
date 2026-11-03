package dk.ek.stud.group7.filmsamling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    // Validation
    private static final List<String> blankStrings = List.of(
            "",    // Empty
            " ",   // Blank
            "   ", // Multiple spaces
            "\t",  // Tabs
            "\n"   // Newlines
    );

    @ParameterizedTest
    @ValueSource(strings = {
            "Jaws", // Normal
            "Dune: Part One", // Colons
            "Se7en", // Digits
            "M*A*S*H", // Asterisks
            "WALL·E", // Special characters
            "Mr. & Mrs. Smith", // Periods and ampersands
            "Who’s Afraid of Virginia Woolf?", // Single-quotes and question marks
            "Birdman or (The Unexpected Virtue of Ignorance)", // Parentheses
            "!Three Amigos", // Exclamation marks
            "Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror", // Long
            "9", // Digits (full title)
            "1917", // Numbers (full title)
            "Amélie", // Accents
            "Crouching Tiger, Hidden Dragon", // Commas
            // Non-English
            "千と千尋の神隠し",
            "映画ドラえもん",
            "三体",
            "La vita è bella",
            "Tři oříšky pro Popelku"
    })
    void builder_validTitles(String title) {
        assertDoesNotThrow(() ->
                new Movie.Builder().title(title));
    }

    @ParameterizedTest
    @NullSource
    @FieldSource("blankStrings")
    @ValueSource(strings = {
            // BUG: Breaks FileHandler because it is seen as a valid title
            "Steins;Gate: The Movie - Load Region of Déjà Vu", // Semi-colons
    })
    void builder_invalidTitles(String title) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().title(title));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Steven Spielberg", // Normal
            "Jean-Luc Godard", // Hyphens
            "Robert Downey Sr.", // Periods
            // Diacritics
            "Pedro Almodóvar",
            "Chloé Zhao",
            "François Truffaut",
            "吳宇森", // Chinese
            "黒澤 明", // Japanese
            "عبد الرحمن", // Arabic
            "אבי נשר", // Hebrew
    })
    void builder_validDirectors(String director) {
        assertDoesNotThrow(() ->
                new Movie.Builder().director(director));
    }

    @ParameterizedTest
    @NullSource
    @FieldSource("blankStrings")
    void builder_invalidDirector(String director) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().director(director));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1888,
            Integer.MAX_VALUE
    })
    void builder_validYearCreated(int yearCreated) {
        assertDoesNotThrow(() ->
                new Movie.Builder().yearCreated(yearCreated));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            Integer.MIN_VALUE,
            -1,
            0,
            1,
            1887
    })
    void builder_invalidYearCreated(int yearCreated) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().yearCreated(yearCreated));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void builder_validInColor(boolean inColor) {
        assertDoesNotThrow(() ->
                new Movie.Builder().inColor(inColor));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1,
            Integer.MAX_VALUE
    })
    void builder_validLengthInMinutes(int lengthInMinutes) {
        assertDoesNotThrow(() ->
                new Movie.Builder().lengthInMinutes(lengthInMinutes));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            Integer.MIN_VALUE,
            -1,
            0
    })
    void builder_invalidLengthInMinutes(int lengthInMinutes) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().lengthInMinutes(lengthInMinutes));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Action",
            "Science Fiction",
            "Action-Comedy",
            "Épouvante",
            "ドラマ",
            "コメディ",
            "恐怖",
            "科幻",
            "喜剧",
            "العائلة",
    })
    void builder_validGenre(String genre) {
        assertDoesNotThrow(() ->
                new Movie.Builder().genre(genre));
    }

    @ParameterizedTest
    @NullSource
    @FieldSource("blankStrings")
    void builder_invalidGenre(String genre) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().genre(genre));
    }

    private static final List<LocalDate> validLastWatchedCases = List.of(
            LocalDate.of(-1, Month.JANUARY, 1),
            LocalDate.of(0, Month.JANUARY, 1),
            LocalDate.of(1887, Month.DECEMBER, 30),
            LocalDate.of(1888, Month.JANUARY, 1),
            LocalDate.of(2026, Month.NOVEMBER, 3)
    );

    @ParameterizedTest
    @NullSource
    @FieldSource("validLastWatchedCases")
    void builder_validLastWatched(LocalDate lastWatched) {
        assertDoesNotThrow(() ->
                new Movie.Builder().lastWatched(lastWatched));
    }

    @Test
    void builder_buildValidMovie() {
        // Arrange
        Movie.Builder builder = new Movie.Builder()
                .title("Jaws")
                .director("Steven Spielberg")
                .yearCreated(1975)
                .inColor(true)
                .lengthInMinutes(2 * 60 + 4)
                .genre("Monster Horror")
                .lastWatched(LocalDate.of(2026, Month.NOVEMBER, 3));

        // Act + Assert
        assertDoesNotThrow(builder::build);
    }

    // Mutation
    private Movie movieToChange;

    @BeforeEach
    void createNewMovieToChange() {
        movieToChange = new Movie(
                "Jaws",
                "Steven Spielberg",
                1975,
                true,
                2 * 60 + 4,
                "Monster Horror",
                LocalDate.of(2026, Month.NOVEMBER, 3)
        );
    }

    @Test
    void title_changePersists() {
        // Arrange
        Movie original = movieToChange;
        String newTitle = "Citizen Kane";

        // Act
        original.setTitle(newTitle);

        // Assert
        assertEquals(newTitle, original.getTitle());
    }

    @Test
    void director_changePersists() {
        // Arrange
        Movie original = movieToChange;
        String newDirector = "Orson Welles";

        // Act
        original.setDirector(newDirector);

        // Assert
        assertEquals(newDirector, original.getDirector());
    }

    @Test
    void yearCreated_changePersists() {
        // Arrange
        Movie original = movieToChange;
        int newYearCreated = 1941;

        // Act
        original.setYearCreated(newYearCreated);

        // Assert
        assertEquals(newYearCreated, original.getYearCreated());
    }

    @Test
    void inColor_changePersists() {
        // Arrange
        Movie original = movieToChange;
        boolean newInColor = false;

        // Act
        original.setInColor(newInColor);

        // Assert
        assertEquals(newInColor, original.isInColor());
    }

    @Test
    void lengthInMinutes_changePersists() {
        // Arrange
        Movie original = movieToChange;
        int newLengthInMinutes = 60 + 59;

        // Act
        original.setLengthInMinutes(newLengthInMinutes);

        // Assert
        assertEquals(newLengthInMinutes, original.getLengthInMinutes());
    }

    @Test
    void genre_changePersists() {
        // Arrange
        Movie original = movieToChange;
        String newGenre = "Epic";

        // Act
        original.setGenre(newGenre);

        // Assert
        assertEquals(newGenre, original.getGenre());
    }

    @Test
    void lastWatched_changePersists() {
        // Arrange
        Movie original = movieToChange;
        LocalDate newLastWatched = null;

        // Act
        original.markAsWatched(newLastWatched);

        // Assert
        assertEquals(newLastWatched, original.getLastWatched());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2026-01-01;2026-01-01;0",    // Watched same day
            "2026-01-01;2026-01-02;1",    // Watched day after
            "2026-06-01;2026-01-01;-151", // Watched in the future
            "2026-02-28;2026-03-01;1"     // Watched across months
    },
            delimiter = ';')
    void getDaysSinceWatched(LocalDate lastWatched, LocalDate now, int expectedDays) {
        // Arrange
        Movie movie = movieToChange;
        movie.markAsWatched(lastWatched);

        // Act
        long days = movie.getDaysSinceWatched(now);

        // Assert
        assertEquals(expectedDays, days);
    }
}