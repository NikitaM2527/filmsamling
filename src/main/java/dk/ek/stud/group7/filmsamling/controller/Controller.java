package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    private final MovieCollection movieCollection;
    private final FileHandler fileHandler;
    private boolean changed;

    public Controller(MovieCollection movieCollection, FileHandler fileHandler) {
        this.movieCollection = movieCollection;
        this.fileHandler = fileHandler;
    }

    public void addMovie(Movie movie) {
        movieCollection.addMovie(movie);
        changed = true;
    }

    public ArrayList<Movie> getMovies() {
        return new ArrayList<>(movieCollection.getMovies());
    }

    public ArrayList<Movie> searchMovies(String searchText) {
        return new ArrayList<>(movieCollection.searchMovies(searchText));
    }

    public boolean editMovie(Movie movie, String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        boolean edited = movieCollection.editMovie(movie, title, director, yearCreated, inColor, lengthInMinutes, genre);

        if (edited) {
            changed = true;
        }
        return edited;
    }

    public boolean deleteMovie(Movie movie) {
        boolean deleted = movieCollection.deleteMovie(movie);

        if (deleted) {
            changed = true;
        }
        return deleted;
    }

    public void markAsWatched(Movie movie) {
        movie.markAsWatched(LocalDate.now());
        changed = true;
    }

    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        LocalDate sinceDate = LocalDate.now().minusDays(days);
        return new ArrayList<>(movieCollection.getMoviesNotWatchedSince(sinceDate));
    }

    public ArrayList<Movie> getMoviesSortedByTitle() {
        return movieCollection.getMoviesSortedByTitle();
    }

    public ArrayList<Movie> getMoviesSortedBy(SortField sortField) {
        return movieCollection.getMoviesSorted(comparatorFor(sortField));
    }

    private Comparator<Movie> comparatorFor(SortField field) {
        return switch (field) {
            case TITLE -> new TitleComparator();
            case DIRECTOR -> new DirectorComparator();
            case YEAR -> new YearComparator();
            case LENGTH -> new LengthComparator();
            case GENRE -> new GenreComparator();
        };
    }

    public boolean saveMoviesIfChanged() throws FileNotFoundException {
        if (!changed) {
            return false;
        }
        fileHandler.saveMovies(movieCollection.getMovies());
        changed = false;
        return true;
    }

    public int loadMovies() throws FileNotFoundException {
        movieCollection.initialLoad(fileHandler.loadMovies());
        return movieCollection.getNumberOfMovies();
    }

    public int getSkippedLineCount() {
        return fileHandler.getSkippedLineCount();
    }
}
