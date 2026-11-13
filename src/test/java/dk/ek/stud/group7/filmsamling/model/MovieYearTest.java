package dk.ek.stud.group7.filmsamling.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US11 - Kun gyldige film (årstal)")
class MovieYearTest {
    @Nested
    @DisplayName("Gyldige")
    class ValidYear {
        @Test
        @DisplayName("1888 er den nedre grænse og accepteres")
        void shouldConstruct_whenYearIsExactlyLowerBoundary() {
            // Arrange
            var lowerBoundary = MovieYear.EARLIEST_VALID_YEAR;

            // Act
            MovieYear movieYear = new MovieYear(lowerBoundary);

            // Assert
            assertEquals(1888, movieYear.value());
        }

        @ParameterizedTest(name = "{0} accepteres")
        @ValueSource(ints = {1975, 2000, 2015, 2026, 2027})
        @DisplayName("Værdier inden for grænserne accepteres")
        void shouldConstruct_whenYearIsWithinValidRange(int year) {
            // Act
            MovieYear movieYear = new MovieYear(year);

            // Assert
            assertEquals(year, movieYear.value());
        }

        @Test
        @DisplayName("Den øvre grænse er ét år i fremtiden")
        void shouldConstruct_whenYearIsExactlyUpperBoundary() {
            // Arrange
            var upperBoundary = LocalDate.now().getYear() + 1;

            // Act
            MovieYear movieYear = new MovieYear(upperBoundary);

            // Assert
            assertEquals(upperBoundary, movieYear.value());
        }
    }

    @Nested
    @DisplayName("Før 1888")
    class TooEarly {
        @ParameterizedTest(name = "{0} afvises")
        @ValueSource(ints = {1887, 0, -1, Integer.MIN_VALUE})
        @DisplayName("År under 1888 afvises")
        void shouldThrow_whenYearIsBelowBoundary(int year) {
            // Act + Assert
            assertThrows(MovieYear.YearBefore1888Exception.class, () ->
                    new MovieYear(year));
        }
    }

    @Nested
    @DisplayName("For langt i fremtiden")
    class TooFarInTheFuture {
        private static final int[] yearsInTheFutureTestCases = {2, 3, 100, 1000};

        // Streams are sequences of values.
        // In this case we're iterating the test cases array, and adding the current year to them.
        // This converts them from relative years to absolute years.
        // After that, we add the absolute year Integer.MAX_VALUE.
        private static Stream<Integer> yearsAboveBoundary() {
            var currentYear = LocalDate.now().getYear();
            return Stream.concat(
                    Arrays.stream(yearsInTheFutureTestCases).map(d -> currentYear + d).boxed(),
                    Stream.of(Integer.MAX_VALUE));
        }

        @ParameterizedTest(name = "{0} afvises")
        @MethodSource("yearsAboveBoundary")
        @DisplayName("År mere end ét år i fremtiden afvises")
        void shouldThrow_whenYearIsAboveBoundary(int year) {
            // Act + Assert
            assertThrows(MovieYear.YearAfterNextYearException.class, () -> new MovieYear(year));
        }
    }
}
