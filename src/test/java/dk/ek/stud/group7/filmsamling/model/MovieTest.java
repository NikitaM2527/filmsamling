package dk.ek.stud.group7.filmsamling.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
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
    @FieldSource("blankStrings")
    void builder_invalidDirector(String director) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().director(director));
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
    @FieldSource("blankStrings")
    void builder_invalidGenre(String genre) {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie.Builder().genre(genre));
    }

    @Test
    void setTitle() {
        // Arrange
        Movie movie = new Movie(
                "Dune",
                "Albert",
                2021,
                true,
                120,
                "Sci-Fi",
                null
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
                "Thriller",
                null);
        jaws.markAsWatched(LocalDate.of(2025, 9, 30));

        // Act
        long days = jaws.getDaysSinceWatched(LocalDate.of(2025, 10, 26));

        // Assert
        assertEquals(26, days);
    }
}