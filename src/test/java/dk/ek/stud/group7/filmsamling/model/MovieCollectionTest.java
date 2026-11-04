package dk.ek.stud.group7.filmsamling.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MovieCollectionTest {
    private static final List<MovieCase> duneMovies = List.of(
            new MovieCase("Dune: Part One", "Denis Villeneuve", 2021, true,
                    2 * 60 + 35, "Action Epic", null),
            new MovieCase("Dune: Part Two", "Denis Villeneuve", 2024, true,
                    2 * 60 + 46, "Action Epic", null)
    );

    private static final List<MovieCase> validMovies = Stream.concat(
            Stream.of(
                    new MovieCase("Jaws", "Steven Spielberg", 1975, true,
                            2 * 60 + 4, "Monster Horror", null),
                    new MovieCase("WALL·E", "Andrew Stanton", 2008, true,
                            60 + 38, "Adventure Epic", LocalDate.of(2025, Month.DECEMBER, 31)),
                    new MovieCase("It", "Andy Muschietti", 2017, true,
                            2 * 60 + 15, "Coming-of-Age", LocalDate.of(2027, Month.JANUARY, 1))
            ),
            duneMovies.stream()
    ).toList();

    // addMovie

    @Test
    void addMovie_numberIsUpdatedAfterAdd() {
        // Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie = validMovies.getFirst().toMovie();

        // Act
        collection.addMovie(movie);
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertEquals(1, numberOfMovies);
    }

    @Test
    void addMovie_isKeptInCollection() {
        // Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie = validMovies.getFirst().toMovie();

        // Act
        collection.addMovie(movie);
        String firstTitle = collection.getMovies().getFirst().getTitle();

        // Assert
        assertEquals(movie.getTitle(), firstTitle);
    }

    @Test
    void addMovie_multiple() {
        // Arrange
        MovieCollection collection = new MovieCollection();
        Set<String> titlesInTestCases = validMovies.stream()
                .map(MovieCase::title)
                .collect(Collectors.toUnmodifiableSet());

        // Act
        for (MovieCase movie : validMovies) {
            collection.addMovie(movie.toMovie());
        }
        int movieCountPostAdd = collection.getNumberOfMovies();
        Set<String> titlesInCollection = collection.getMovies()
                .stream()
                .map(Movie::getTitle) // Movie -> String (select title from every movie)
                .collect(Collectors.toUnmodifiableSet()); // Put into a Set (unordered,

        // Assert
        assertEquals(validMovies.size(), movieCountPostAdd);
        assertEquals(titlesInTestCases, titlesInCollection);
    }

    // getNumberOfMovies

    @Test
    void getNumberOfMovies_newCollectionIsEmpty() {
        // Arrange
        MovieCollection collection = new MovieCollection();

        // Act
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertEquals(0, numberOfMovies);
    }

    // getMoviesSorted

    @Test
    void getMoviesSorted_byDefault() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<String> expectedTitles = List.of(
                "Dune: Part One",
                "Dune: Part Two",
                "It",
                "Jaws",
                "WALL·E"
        );

        // Act
        List<Movie> movies = collection.getMoviesSortedByTitle();
        List<String> actualTitles = movies.stream()
                .map(Movie::getTitle)
                .toList();

        // Assert
        assertEquals(expectedTitles, actualTitles);
    }

    @Test
    void getMoviesSorted_byTitle() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<String> expectedTitles = List.of(
                "Dune: Part One",
                "Dune: Part Two",
                "It",
                "Jaws",
                "WALL·E"
        );

        // Act
        List<Movie> movies = collection.getMoviesSorted(new TitleComparator());
        List<String> actualTitles = movies.stream()
                .map(Movie::getTitle)
                .toList();

        // Assert
        assertEquals(expectedTitles, actualTitles);
    }

    @Test
    void getMoviesSorted_byDirector() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<String> expectedDirectors = List.of(
                "Andrew Stanton",
                "Andy Muschietti",
                "Denis Villeneuve",
                "Denis Villeneuve",
                "Steven Spielberg"
        );

        // Act
        List<Movie> movies = collection.getMoviesSorted(new DirectorComparator());
        List<String> actualDirectors = movies.stream()
                .map(Movie::getDirector)
                .toList();

        // Assert
        assertEquals(expectedDirectors, actualDirectors);
    }

    @Test
    void getMoviesSorted_byYearCreated() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<Integer> expectedYearCreated = List.of(
                1975,
                2008,
                2017,
                2021,
                2024
        );

        // Act
        List<Movie> movies = collection.getMoviesSorted(new YearComparator());
        List<Integer> actualYearCreated = movies.stream()
                .map(Movie::getYearCreated)
                .toList();

        // Assert
        assertEquals(expectedYearCreated, actualYearCreated);
    }

    @Test
    void getMoviesSorted_byLengthInMinutes() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<Integer> expectedLengthInMinutes = List.of(
                60 + 38,
                2 * 60 + 4,
                2 * 60 + 15,
                2 * 60 + 35,
                2 * 60 + 46
        );

        // Act
        List<Movie> movies = collection.getMoviesSorted(new LengthComparator());
        List<Integer> actualLengthInMinutes = movies.stream()
                .map(Movie::getLengthInMinutes)
                .toList();

        // Assert
        assertEquals(expectedLengthInMinutes, actualLengthInMinutes);
    }

    @Test
    void getMoviesSorted_byGenre() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        List<String> expectedGenre = List.of(
                "Action Epic",
                "Action Epic",
                "Adventure Epic",
                "Coming-of-Age",
                "Monster Horror"
        );

        // Act
        List<Movie> movies = collection.getMoviesSorted(new GenreComparator());
        List<String> actualGenre = movies.stream()
                .map(Movie::getGenre)
                .toList();

        // Assert
        assertEquals(expectedGenre, actualGenre);
    }

    // getMoviesNotWatchedSince

    @ParameterizedTest
    @CsvSource(value = {
            "2027-01-02;5", // All movies
            "2027-01-01;4", // 'since' is exclusive
            "2026-01-01;4",
            // Movies with 'null' dates will always be returned
            "2025-12-31;3",
            "2020-01-01;3",
            "2000-01-01;3",
            "0000-01-01;3",
    }, delimiter = ';')
    void getMoviesNotWatchedSince_returnsExpectedNumberOfMovies(LocalDate since, int expectedNumber) {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);

        // Act
        int number = collection.getMoviesNotWatchedSince(since).size();

        // Assert
        assertEquals(expectedNumber, number);
    }

    // searchMovies

    @Test
    void searchMovies_noMatchReturnsEmptyList() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);

        // Act
        ArrayList<Movie> results = collection.searchMovies("Nonexistent");

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void searchMovies_exactTitleMatchReturnsRightTitle() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String exactTitle = "Jaws";

        // Act
        ArrayList<Movie> results = collection.searchMovies(exactTitle);
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals(exactTitle, fullTitle);
    }

    @Test
    void searchMovies_lowerCaseTitleMatches() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String exactTitle = "It";
        String lowerCaseTitle = exactTitle.toLowerCase(Locale.ROOT);

        // Act
        ArrayList<Movie> results = collection.searchMovies(lowerCaseTitle);
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals(exactTitle, fullTitle);
    }

    @Test
    void searchMovies_mixedCaseSearchMatches() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String exactTitle = "It";
        String mixedCaseTitle = "iT";

        // Act
        ArrayList<Movie> results = collection.searchMovies(mixedCaseTitle);
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals(exactTitle, fullTitle);
    }

    // Extra
    @Test
    void searchMovies_partialMatchReturnsOneMovie() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String exactTitle = "It";
        String partialTitle = "I";

        // Act
        ArrayList<Movie> results = collection.searchMovies(partialTitle);
        int matchCount = results.size();
        String fullTitle = results.getFirst().getTitle();

        // Assert
        assertEquals(1, matchCount);
        assertEquals(exactTitle, fullTitle);
    }

    @Test
    void searchMovies_partialMatchReturnsTwoMovies() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String partialTitle = "Dune";

        // Act
        ArrayList<Movie> results = collection.searchMovies(partialTitle);
        int matchCount = results.size();
        Set<MovieCase> matchedCases = results.stream()
                .map(MovieCase::fromMovie)
                .collect(Collectors.toUnmodifiableSet());

        // Assert
        assertEquals(duneMovies.size(), matchCount);
        assertEquals(new HashSet<>(duneMovies), matchedCases);
    }

    @Test
    void searchMovies_emptySearchStringReturnsAllMovies() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        int fullCount = validMovies.size();
        Set<String> testCaseTitles = validMovies.stream()
                .map(MovieCase::title)
                .collect(Collectors.toUnmodifiableSet());

        // Act
        ArrayList<Movie> results = collection.searchMovies("");
        Set<String> matchedTitles = results.stream()
                .map(Movie::getTitle)
                .collect(Collectors.toUnmodifiableSet());

        // Assert
        assertEquals(fullCount, results.size());
        assertEquals(testCaseTitles, matchedTitles);
    }

    // editMovie

    @Test
    void editMovie_directorIsKeptAndTrueIsReturned() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        String exactTitle = "IT";
        String intendedDirector = "Me";
        Movie movie = collection.searchMovies(exactTitle).getFirst();

        // Act
        boolean success = collection.editMovie(
                movie,
                movie.getTitle(),
                intendedDirector,
                movie.getYearCreated(),
                movie.isInColor(),
                movie.getLengthInMinutes(),
                movie.getGenre());
        Movie foundMovie = collection.searchMovies(exactTitle).getFirst();
        String newDirector = movie.getDirector();

        // Assert
        assertTrue(success);
        assertSame(movie, foundMovie);
        assertEquals(intendedDirector, newDirector);
    }

    @Test
    void editMovie_nonExistentMovieReturnsFalse() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        MovieCase movieCase = new MovieCase(
                "Placeholder",
                "Nobody",
                2000,
                false,
                60,
                "Avant-Garde",
                null);

        // Act
        Movie movie = movieCase.toMovie();
        boolean success = collection.editMovie(movie,
                "Foobar",
                "Somebody",
                2001,
                true,
                90,
                "Run-of-the-mill");

        // Assert
        assertFalse(success);
        assertEquals(movieCase, MovieCase.fromMovie(movie));
    }

    // deleteMovie

    @Test
    void deleteMovie_goneFromCollectionAndTrueIsReturned() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        int caseCount = validMovies.size();
        Movie movieToDelete = collection.searchMovies("IT").getFirst();
        MovieCase movieToDeleteAsCase = MovieCase.fromMovie(movieToDelete);

        // Act
        int preDeleteMovieCount = collection.getNumberOfMovies();
        boolean success = collection.deleteMovie(movieToDelete);
        int postDeleteMovieCount = collection.getNumberOfMovies();
        Set<MovieCase> remainingMovies = collection.getMovies()
                .stream().map(MovieCase::fromMovie)
                .collect(Collectors.toUnmodifiableSet());

        // Assert
        assertTrue(success);
        assertEquals(caseCount, preDeleteMovieCount);
        assertEquals(caseCount - 1, postDeleteMovieCount);
        assertFalse(remainingMovies.contains(movieToDeleteAsCase));
    }

    @Test
    void deleteMovie_nonExistentMovieReturnsFalse() {
        // Arrange
        MovieCollection collection = MovieCase.toCollection(validMovies);
        int caseCount = validMovies.size();
        MovieCase movieCase = new MovieCase(
                "Placeholder",
                "Nobody",
                2000,
                false,
                60,
                "Avant-Garde",
                null);

        // Act
        boolean success = collection.deleteMovie(movieCase.toMovie());
        int numberOfMovies = collection.getNumberOfMovies();

        // Assert
        assertFalse(success);
        assertEquals(caseCount, numberOfMovies);
    }
}
