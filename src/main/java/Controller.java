import java.util.ArrayList;

public class Controller {
    MovieCollection movieCollection;

    public Controller(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        // TODO US1
    }

    public ArrayList<Movie> getMovies() {
        return null;
        // TODO US3
    }

    public ArrayList<Movie> searchMovies(String searchText) {
        return null;
        // TODO US4
    }

    public boolean editMovie(Movie movie, String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {
        return false;
        // TODO US5
    }

    public boolean deleteMovie(Movie movie) {
        return false;
        // TODO US6
    }
}
