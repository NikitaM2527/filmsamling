package dk.ek.stud.group7.filmsamling.model;

import java.util.Comparator;

public class DirectorComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie movie1, Movie movie2) {
        return movie1.getDirector().compareTo(movie2.getDirector());
    }
}
