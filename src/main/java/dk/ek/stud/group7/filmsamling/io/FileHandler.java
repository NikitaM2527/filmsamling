package dk.ek.stud.group7.filmsamling.io;

import dk.ek.stud.group7.filmsamling.model.Movie;
import dk.ek.stud.group7.filmsamling.model.MovieYear;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private static final String HEADER = "title|director|yearCreated|inColor|lengthInMinutes|genre|lastWatched";

    private final String fileName;
    private int skippedLineCount;

    public FileHandler(String fileName) {
        this.fileName = fileName;
        this.skippedLineCount = 0;
    }

    public void saveMovies(ArrayList<Movie> movies) throws FileNotFoundException {
        PrintStream output = new PrintStream(fileName);
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

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        } else {
            return movies;
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Movie movie = parseMovie(line);
            if (movie != null) {
                movies.add(movie);
            } else {
                skippedLineCount++;
            }
        }
        scanner.close();
        return movies;
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    private String toCsvLine(Movie movie) {
        String title = movie.getTitle();
        String director = movie.getDirector();
        String yearCreated = String.valueOf(movie.getYearCreated().value());
        String isInColor = String.valueOf(movie.isInColor());
        String lengthInMinutes = String.valueOf(movie.getLengthInMinutes());
        String genre = movie.getGenre();
        String lastWatched = movie.hasBeenWatched() ? movie.getLastWatched().toString() : "";

        return String.join("|", title, director, yearCreated, isInColor, lengthInMinutes, genre, lastWatched);
    }

    static Movie parseMovie(String line) {
        String[] cells = line.split("\\|", -1);

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

            return new Movie(title, director, new MovieYear(yearCreated), inColor, lengthInMinutes, genre, lastWatched);

        } catch (DateTimeParseException | IllegalArgumentException | MovieYear.YearBefore1888Exception e) {
            return null;
        }
    }
}
