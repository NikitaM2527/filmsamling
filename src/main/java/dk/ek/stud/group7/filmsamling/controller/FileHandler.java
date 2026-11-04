package dk.ek.stud.group7.filmsamling.controller;

import dk.ek.stud.group7.filmsamling.model.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private String fileName;
    private int skippedLineCount;
    private static final String HEADER = "title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched";

    public FileHandler(String fileName) {
        this.fileName = fileName;
        this.skippedLineCount = 0;
    }

    public void saveMovies(ArrayList<Movie> movies) throws FileNotFoundException {
        PrintStream output = new PrintStream(new File(fileName));
        output.println(HEADER);

        for (Movie movie : movies) {
            output.println(toCsvLine(movie));
        }

        output.close();
    }

    public ArrayList<Movie> loadMovies() throws FileNotFoundException {
        ArrayList<Movie> movies = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));
        skippedLineCount = 0;

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Movie movie = parseMovie(line);
            if (movie != null) {
                movies.add(movie);
            } else {
                skippedLineCount++;
            }
        }
        return movies;
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    private String toCsvLine(Movie movie) {
        String title = movie.getTitle();
        String director = movie.getDirector();
        String yearCreated = String.valueOf(movie.getYearCreated());
        String isInColor = String.valueOf(movie.isInColor());
        String lengthInMinutes = String.valueOf(movie.getLengthInMinutes());
        String genre = movie.getGenre();
        String lastWatched = "";
        if (movie.hasBeenWatched()) {
            lastWatched = movie.getLastWatched().toString();
        }

        return String.join(";", title, director, yearCreated, isInColor, lengthInMinutes, genre, lastWatched);
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
