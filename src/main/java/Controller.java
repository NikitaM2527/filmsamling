import java.util.ArrayList;

public class Controller {
    private final MovieCollection movieCollection;

    public Controller(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
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
        movieCollection.markAsWatched(movie);
    }

    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        return new ArrayList<>(movieCollection.getMoviesNotWatchedForDays(days));
    }
}
