package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.Movie;

import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class FileHandler {
    private String fileName;
    private int skippedLineCount;

    public FileHandler(String fileName) {
        this.fileName = fileName;
        this.skippedLineCount = 0;
    }

    public void saveMovies(ArrayList<Movie> movies) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Movie> loadMovies() {
        throw new UnsupportedOperationException();
    }

    public int getSkippedLineCount() {
        throw new UnsupportedOperationException();
    }

    private String toCsvLine(Movie movie) {
        throw new UnsupportedOperationException();
    }

    static Movie parseMovie(String line) {
        String[] cells = line.split(";", -1);

        if (cells.length != 7) {
            return null;
        }

        try {
            String title = cells[0];
            String director = cells[1];
            int yearCreated = Integer.parseInt(cells[2]);
            boolean inColor = Boolean.parseBoolean(cells[3]);
            int lengthInMinutes = Integer.parseInt(cells[4]);
            String genre = cells[5];
            LocalDate lastWatched = cells[6].isEmpty() ? null : LocalDate.parse(cells[6]);

            Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre, lastWatched);
            return movie;

        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }
}
