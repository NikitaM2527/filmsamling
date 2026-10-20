import java.util.ArrayList;

public class Controller {
    MovieCollection movieCollection;

    public Controller(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        // TODO US1
    }

    public ArrayList<Movie> getMovies() {
        return new ArrayList<>(movieCollection.getMovies());
        // TODO US3
    }

    public ArrayList<Movie> searchMovies(String searchText) {
        return new ArrayList<>(movieCollection.getMovies());
        // TODO US4
    }

    public boolean editMovie(Movie movie, String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        return editMovie(movie, title, director, yearCreated, inColor, lengthInMinutes, genre);
        // TODO US5
    }

    public boolean deleteMovie(Movie movie) {
        return deleteMovie(movie);
        // TODO US6
    }
}
