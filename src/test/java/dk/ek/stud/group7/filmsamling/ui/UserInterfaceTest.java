package dk.ek.stud.group7.filmsamling.ui;

import dk.ek.stud.group7.filmsamling.model.Movie;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInterfaceTest {
    private static final List<LastWatchedCase> lastWatchedCases = List.of(
            new LastWatchedCase(null, date(Month.OCTOBER, 31), "aldrig"),
            new LastWatchedCase(date(Month.OCTOBER, 31), date(Month.OCTOBER, 31), "31-10-2026 (for 0 dage siden)"),
            new LastWatchedCase(date(Month.OCTOBER, 30), date(Month.OCTOBER, 31), "30-10-2026 (for 1 dage siden)"),
            new LastWatchedCase(date(Month.OCTOBER, 29), date(Month.OCTOBER, 31), "29-10-2026 (for 2 dage siden)"),
            new LastWatchedCase(date(Month.OCTOBER, 1), date(Month.OCTOBER, 31), "01-10-2026 (for 30 dage siden)"),
            new LastWatchedCase(date(Month.SEPTEMBER, 30), date(Month.OCTOBER, 31), "30-09-2026 (for 31 dage siden)"),
            new LastWatchedCase(LocalDate.of(2025, Month.DECEMBER, 31), date(Month.JANUARY, 1), "31-12-2025 (for 1 dage siden)"),
            new LastWatchedCase(date(Month.NOVEMBER, 1), date(Month.OCTOBER, 31), "aldrig")
    );

    private static LocalDate date(Month month, int day) {
        return LocalDate.of(2026, month, day);
    }

    @ParameterizedTest
    @FieldSource("lastWatchedCases")
    void formatLastWatched(LastWatchedCase testCase) {
        // Arrange
        Movie movie = new Movie("_", "_", 1888, false, 1, "_", null);
        if (testCase.lastWatched != null)
            movie.markAsWatched(testCase.lastWatched);

        // Act
        String actual = UserInterface.formatLastWatched(movie, testCase.now);

        // Assert
        assertEquals(testCase.expected, actual);
    }

    private record LastWatchedCase(LocalDate lastWatched, LocalDate now, String expected) {
        private static final DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        private static final DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd-MM");

        @Override
        public String toString() {
            var formatter = lastWatched.getYear() == now.getYear() ? shortFormatter : normalFormatter;
            var watchedStr = lastWatched.format(formatter);
            var nowStr = now.format(formatter);
            return "watched: " + watchedStr + " now: " + nowStr + " -> '" + expected + "'";
        }
    }
}