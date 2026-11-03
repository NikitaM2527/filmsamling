package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.Movie;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FileHandlerTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03",
            "Jaws;Steven Spielberg;1975;true;124;Horror;",
    })
    void parseMovie_validMovies(String line) {
        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNotNull(movie);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Gekijouban Steins;Gate: Fuka ryouiki no dejavu;Hiroshi Hamasaki; 2013;true;90;Anime;2016-02-18",
            "Dune: Part One;Denis Villeneuve;2021;true;155;Sci-Fi;2022-11-03;",
            "Jaws;Steven Spielberg;1975;true;124;Horror",
            "Test;Albert;1880;false;60;Drama;2026-11-03"
    })
    void parseMovie_invalidMovie(String line) {
        // Act
        Movie movie = FileHandler.parseMovie(line);

        // Assert
        assertNull(movie);
    }
}
