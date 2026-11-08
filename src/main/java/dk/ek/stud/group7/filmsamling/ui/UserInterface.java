package dk.ek.stud.group7.filmsamling.ui;

import dk.ek.stud.group7.filmsamling.controller.Controller;
import dk.ek.stud.group7.filmsamling.controller.SortField;
import dk.ek.stud.group7.filmsamling.model.Movie;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public final class UserInterface {
    private static final String dateFormat = "dd-MM-yyyy";

    private final ConsoleIO io;
    private final Controller controller;

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

    static String dateFormatDK() {
        return dateFormat
                .replace('M', 'm')
                .replace('y', 'å');
    }

    public UserInterface(ConsoleIO io, Controller controller) {
        this.io = io;
        this.controller = controller;
    }

    public void startProgram() {
        io.println("""
                ====================================
                
                   Velkommen til din filmsamling!
                
                ====================================
                """);
        loadMovies();
        io.println();
        printMenuOptions();

        boolean running = true;
        while (running) {
            try {
                if (controller.saveMoviesIfChanged()) {
                    io.println();
                    io.println("Din filmsamling er blevet gemt");
                }
            } catch (FileNotFoundException e) {
                io.println();
                io.println("Fejl: Filmsamlingen kunne ikke gemmes");
            }
            io.println();
            io.print("Vælg: ");
            String choice = io.readLine().trim();
            io.println();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies(controller.getMoviesSortedByTitle());
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "6" -> markMovieAsWatched();
                case "7" -> showDaysSinceLastWatched();
                case "8" -> sortByOptionsMenu();
                case "0" -> running = false;
                default -> {
                    io.println("Ukendt valg: " + choice);
                    io.println();
                    printMenuOptions();
                }
            }
            if (running) {
                waitForEnter();
                io.println();
                printMenuOptions();
            }
        }
        printGoodbye();
    }

    private void printMenuOptions() {
        io.print("""
                1. Opret en film
                2. Vis alle film
                3. Søg efter film
                4. Redigér en film
                5. Slet en film
                6. Marker en film som set
                7. Vis film du ikke har set længe
                8. Sortér filmlisten
                0. Afslut
                """);
    }

    private void printSortOptions() {
        io.println("""
                Sortér efter:
                1. Titel
                2. Instruktør
                3. Årstal
                4. Længde
                5. Genre
                0. Gå tilbage
                """);
    }

    private void waitForEnter() {
        io.println();
        io.print("Tryk 'Enter' for at gå tilbage til menuen...");
        io.readLine();
    }

    private void createMovie() {
        Movie.Builder builder = new Movie.Builder();

        promptString("Indtast filmens titel: ", builder::title);
        promptString("Indtast filmens instruktør: ", builder::director);
        promptInt("Indtast udgivelsesår: ", builder::yearCreated);
        promptBoolean("Film i farve (ja/nej): ", builder::inColor);
        promptInt("Indtast filmens længde: ", builder::lengthInMinutes);
        promptString("Indtast filmens genre: ", builder::genre);

        Movie movie = builder.build();
        controller.addMovie(movie);
        io.println();
        io.println(movie.getTitle() + " (" + movie.getYearCreated() + ") " + "er nu tilføjet til samlingen.");
        io.println();
    }

    private void promptString(String prompt, Consumer<String> setter) {
        boolean valid = false;
        do {
            io.print(prompt);
            String input = io.readLine();
            try {
                setter.accept(input);
                valid = true;
            } catch (IllegalArgumentException e) {
                io.println(e.getMessage());
            }
        } while (!valid);
    }

    private void promptInt(String prompt, Consumer<Integer> setter) {
        boolean valid = false;
        do {
            io.print(prompt);
            String input = io.readLine().trim();
            try {
                int intInput = Integer.parseInt(input);
                setter.accept(intInput);
                valid = true;
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    io.println("Indtast et helt tal.");
                } else {
                    io.println(input + " er ikke er helt tal. Prøv igen.");
                }
            } catch (IllegalArgumentException e) {
                io.println(e.getMessage());
            }
        } while (!valid);
    }

    private void promptBoolean(String prompt, Consumer<Boolean> setter) {
        boolean valid = false;
        do {
            io.print(prompt);
            String input = io.readLine();

            try {
                boolean boolInput;
                if (input.equalsIgnoreCase("ja")) {
                    boolInput = true;
                } else if (input.equalsIgnoreCase("nej")) {
                    boolInput = false;
                } else {
                    throw new IllegalArgumentException("Indtast ja eller nej");
                }
                setter.accept(boolInput);
                valid = true;
            } catch (IllegalArgumentException e) {
                io.println(e.getMessage());
            }
        } while (!valid);
    }

    private void showAllMovies(ArrayList<Movie> sorted) {
        if (sorted.isEmpty()) {
            io.println("Filmlisten er tom!");
        } else {
            for (Movie movie : sorted) {
                printAllMovieInfo(movie);
            }
        }
    }

    static String formatLastWatched(Movie movie, LocalDate now) {
        LocalDate watchedDate = movie.getLastWatched();
        if (movie.hasBeenWatched() && !watchedDate.isAfter(now)) {
            long days = movie.getDaysSinceWatched(now);
            return watchedDate.format(dateFormatter) + " (for " + days + " dage siden)";
        } else {
            return "aldrig";
        }
    }

    private void searchMovies() {
        String title = prompt("Titel", Parsing.Result::success);

        ArrayList<Movie> foundMovies = controller.searchMovies(title);

        if (controller.getMovies().isEmpty()) {
            io.println("Der er ingen film i filmsamlingen.");
            return;
        }

        if (foundMovies.isEmpty()) {
            io.println("Søgeresultatet: '" + title + "' fandt ingen resultater.");
        } else {
            for (Movie movie : foundMovies) {
                printAllMovieInfo(movie);
            }
        }
    }

    private void printAllMovieInfo(Movie movie) {
        io.println(movie.getTitle() + " (" + movie.getYearCreated() + ")");
        io.println("  Instruktør: " + movie.getDirector());
        io.println("  Genre: " + movie.getGenre());
        io.println("  Længde: " + movie.getLengthInMinutes() + " minutter, " + (movie.isInColor() ? "farver" : "uden farver"));
        io.println("  Sidst set: " + formatLastWatched(movie, LocalDate.now()));
    }

    private void editMovie() {
        String query = prompt("Søg efter en titel", Parsing.Result::success);

        Movie selected = searchSingleMovie(query);
        if (selected == null) return;

        io.println("Tryk Enter for at beholde den nuværende værdi.");

        String title = promptWithDefault(
                "Titel", Parsing::parseNotBlank,
                selected.getTitle(), Object::toString);
        String director = promptWithDefault(
                "Instruktør", Parsing::parseNotBlank,
                selected.getDirector(), Object::toString);
        int yearCreated = this.<Integer>promptWithDefault(
                "Årstal", Parsing::parseYearCreated,
                selected.getYearCreated(), Object::toString);
        boolean inColor = promptWithDefault(
                "I farver (ja/nej)", Parsing::parseBoolean,
                selected.isInColor(), (color) -> color ? "ja" : "nej");
        int lengthInMinutes = promptWithDefault(
                "Længde i minutter", Parsing::parseLengthInMinutes,
                selected.getLengthInMinutes(), Object::toString);
        String genre = promptWithDefault(
                "Genre", Parsing::parseNotBlank,
                selected.getGenre(), Object::toString);

        controller.editMovie(selected, title, director, yearCreated, inColor, lengthInMinutes, genre);
        io.println();
        printAllMovieInfo(selected);
    }

    private void printSearchResults(ArrayList<Movie> results) {
        io.println("Søgningen giver flere resultater: ");
        for (int i = 0; i < results.size(); i++) {
            Movie movie = results.get(i);
            io.println((i + 1) + ". " + movie.getTitle() + " (" + movie.getYearCreated() + ")");
        }
    }

    private void deleteMovie() {
        String title = prompt("Angiv filmens titel", Parsing::parseNotBlank);

        Movie selected = searchSingleMovie(title);

        if (selected == null) {
            io.println("Film findes ikke i samling.");
            return;
        }

        boolean answer = prompt("Er du sikker på at du vil fjerne filmen fra samlingen?", Parsing::parseBoolean);

        if (answer) {
            controller.deleteMovie(selected);
            io.println("Filmen er fjernet fra samlingen.");
        } else {
            io.println("Filmen forbliver i samlingen.");
        }
    }

    private void markMovieAsWatched() {
        String query = prompt("Søg efter en titel", Parsing.Result::success);

        Movie selected = searchSingleMovie(query);
        if (selected == null) {
            io.println("Ingen film med den titel blev fundet.");
            return;
        }

        LocalDate date = promptWithDefault("Indtast datoen (" + dateFormatDK() + ")", Parsing::parsePastDate, LocalDate.now(), dateFormatter::format);

        controller.markAsWatched(selected, date);

        String watchedDate = selected.getLastWatched().format(dateFormatter);
        io.println(selected.getTitle() + " er markeret som set " + watchedDate);
    }

    private Movie searchSingleMovie(String query) {
        ArrayList<Movie> results = controller.searchMovies(query);

        Movie selected = switch (results.size()) {
            case 0 -> null;
            case 1 -> results.getFirst();
            default -> {
                printSearchResults(results);

                int num = prompt("Vælg nummer", Parsing.parseIntWithinRange(1, results.size()));

                yield results.get(num - 1);
            }
        };

        if (selected == null) {
            io.println("Ingen film med den titel blev fundet");
            return null;
        }
        return selected;
    }

    private void showDaysSinceLastWatched() {
        int days = prompt("Indtast dage", Parsing::parsePositiveInt);

        io.println("Film, du ikke har set i "
                + days + " " + (days == 1 ? "dag" : "dage")
                + " eller aldrig har set:");

        ArrayList<Movie> movies = controller.getMoviesNotWatchedForDays(days);

        for (Movie movie : movies) {
            printAllMovieInfo(movie);
        }
    }

    private void printGoodbye() {
        try {
            if (controller.saveMoviesIfChanged()) {
                io.println("Din filmsamling er blevet gemt");
            }
        } catch (FileNotFoundException e) {
            io.println("Fejl: Filmsamlingen kunne ikke gemmes");
        }
        io.println("Farvel! Vi har gemt dine ændringer.");
    }

    private <T> T prompt(String prompt, Function<String, Parsing.Result<T>> parser) {
        return promptWithDefault(prompt, parser, null, null);
    }

    private <T> T promptWithDefault(
            String prompt, Function<String, Parsing.Result<T>> parser,
            T defaultValue, Function<T, String> format) {
        while (true) {
            String hint = defaultValue == null ? "" : " [" + format.apply(defaultValue) + "]";
            io.print(prompt + hint + ": ");
            String input = io.readLine().trim();
            if (defaultValue != null && input.isEmpty()) return defaultValue;
            Parsing.Result<T> result = parser.apply(input);
            if (result.success()) return result.output();
            io.println("Ugyldigt input: " + result.reason());
        }
    }

    private void sortByOptionsMenu() {
        printSortOptions();
        SortField field = null;

        boolean running = true;
        while (running) {
            io.print("Vælg: ");
            String choice = io.readLine().trim().toLowerCase();

            switch (choice) {
                case "1", "titel" -> {
                    field = SortField.TITLE;
                    running = false;
                }
                case "2", "instruktør" -> {
                    field = SortField.DIRECTOR;
                    running = false;
                }
                case "3", "årstal" -> {
                    field = SortField.YEAR;
                    running = false;
                }
                case "4", "længde" -> {
                    field = SortField.LENGTH;
                    running = false;
                }
                case "5", "genre" -> {
                    field = SortField.GENRE;
                    running = false;
                }
                case "0" -> running = false;
                default -> {
                    io.println("Ukendt valg: " + choice);
                    io.println();
                    printSortOptions();
                }
            }
            if (running) {
                waitForEnter();
                io.println();
                printMenuOptions();
            }
        }
        if (field != null) {
            ArrayList<Movie> sorted = controller.getMoviesSortedBy(field);
            showAllMovies(sorted);
        }
    }

    private void loadMovies() {
        try {
            int count = controller.loadMovies();
            io.println(count + " film er indlæst.");
            int skipped = controller.getSkippedLineCount();
            if (skipped > 0) {
                io.println("Advarsel: " + skipped + " linje(r) i filen kunne ikke læses og er sprunget over.");
            }
        } catch (FileNotFoundException e) {
            io.println("Der er ingen gemt filmsamling endnu – du starter med en tom samling.");
        }
    }
}
