package dk.ek.stud.group7.filmsamling.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Contains immutable Movie data.
 * Can be used as test case source data to share Movie data without mutability.
 * @param title
 * @param director
 * @param yearCreated
 * @param inColor
 * @param lengthInMinutes
 * @param genre
 * @param lastWatched
 */
record MovieCase(
        String title,
        String director,
        int yearCreated,
        boolean inColor,
        int lengthInMinutes,
        String genre,
        LocalDate lastWatched) {
    Movie toMovie() {
        return new Movie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre,
                lastWatched
        );
    }

    static MovieCase fromMovie(Movie movie) {
        return new MovieCase(
                movie.getTitle(),
                movie.getDirector(),
                movie.getYearCreated(),
                movie.isInColor(),
                movie.getLengthInMinutes(),
                movie.getGenre(),
                movie.getLastWatched()
        );
    }

    static MovieCollection toCollection(List<MovieCase> cases) {
        MovieCollection collection = new MovieCollection();
        for (MovieCase movieCase : cases) {
            collection.addMovie(movieCase.toMovie());
        }
        return collection;
    }
}
