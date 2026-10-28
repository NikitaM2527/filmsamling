import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private final MovieCollection movieCollection;

    public Controller(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public void addMovie(Movie movie) {
        movieCollection.addMovie(movie);
    }

    public ArrayList<Movie> getMovies() {
        return new ArrayList<>(movieCollection.getMovies());
    }

    public ArrayList<Movie> searchMovies(String searchText) {
        return new ArrayList<>(movieCollection.searchMovies(searchText));
    }

    public boolean editMovie(Movie movie, String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        return movieCollection.editMovie(movie, title, director, yearCreated, inColor, lengthInMinutes, genre);
    }

    public boolean deleteMovie(Movie movie) {
        return movieCollection.deleteMovie(movie);
    }

    public void markAsWatched(Movie movie) {
        movie.markAsWatched(LocalDate.now());
    }

    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        LocalDate sinceDate = LocalDate.now().minusDays(days);
        return new ArrayList<>(movieCollection.getMoviesNotWatchedSince(sinceDate));
    }
}
