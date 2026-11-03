package dk.ek.stud.group7.filmsamling.model;

import java.util.Comparator;

public class GenreComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie movie1, Movie movie2) {
        return movie1.getGenre().compareTo(movie2.getGenre());
    }
}
