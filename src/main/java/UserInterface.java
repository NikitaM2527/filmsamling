import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

public final class UserInterface {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final Scanner scanner;
    private final Controller controller;

    public UserInterface(Controller controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    private static ParseResult<String> parseNotBlank(String input) {
        return input.isBlank()
                ? ParseResult.failure("Feltet må ikke være tomt")
                : ParseResult.success(input);
    }

    private static ParseResult<Integer> parseYearCreated(String input) {
        try {
            int output = Integer.parseInt(input);
            if (output < 1888) {
                return ParseResult.failure(
                        "Verdens første film blev udgivet i 1888");
            }
            return ParseResult.success(output);
        } catch (NumberFormatException e) {
            return ParseResult.failure("Indtast et heltal");
        }
    }

    private static ParseResult<Boolean> parseBoolean(String input) {
        if (input.equalsIgnoreCase("ja")) {
            return ParseResult.success(true);
        } else if (input.equalsIgnoreCase("nej")) {
            return ParseResult.success(false);
        }

        return ParseResult.failure("Svar ja eller nej");
    }

    private static ParseResult<Integer> parseLengthInMinutes(String input) {
        try {
            int output = Integer.parseInt(input);
            if (output <= 0) {
                return ParseResult.failure("En film er mindst 1 minut lang");
            }
            return ParseResult.success(output);
        } catch (NumberFormatException e) {
            return ParseResult.failure("Indtast et heltal");
        }
    }

    public void startProgram() {
        System.out.println("""
                Velkommen til vores filmsamling!
                """);

        printMenuOptions();

        boolean running = true;
        while (running) {
            System.out.print("Vælg: ");
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "6" -> markMovieAsWatched();
                case "7" -> showDaysSinceLastWatched();
                case "0" -> running = false;
                default -> {
                    System.out.println("Ukendt valg: " + choice);
                    System.out.println();
                    printMenuOptions();
                }
            }
            System.out.println();
        }

        printGoodbye();
    }

    private void printMenuOptions() {
        System.out.println("""
                1. Opret en film
                2. Vis alle film
                3. Søg efter film
                4. Redigér en film
                5. Slet en film
                6. Marker en film som set
                7. Vis film du ikke har set længe
                0. Afslut
                """);
    }

    private void createMovie() {
        String title = prompt("Titel", UserInterface::parseNotBlank);
        String director = prompt("Instruktør", UserInterface::parseNotBlank);
        int yearCreated = this.<Integer>prompt("Årstal", UserInterface::parseYearCreated);
        boolean inColor = prompt("I farver (ja/nej)", UserInterface::parseBoolean);
        int lengthInMinutes = prompt("Længde i minutter", UserInterface::parseLengthInMinutes);
        String genre = prompt("Genre", UserInterface::parseNotBlank);

        controller.addMovie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre
        );
        // TODO Confirm with movie title (print)
    }

    private <T> T prompt(String prompt, Function<String, ParseResult<T>> parser) {
        return promptWithDefault(prompt, parser, null, null);
    }

    private <T> T promptWithDefault(
            String prompt, Function<String, ParseResult<T>> parser,
            T defaultValue, Function<T, String> format) {
        while (true) {
            String hint = defaultValue == null ? "" : " [" + format.apply(defaultValue) + "]";
            System.out.print(prompt + hint + ": ");
            String input = scanner.nextLine().trim();
            if (defaultValue != null && input.isEmpty()) return defaultValue;
            ParseResult<T> result = parser.apply(input);
            if (result.success) return result.output;
            System.out.println("Ugyldigt input: " + result.reason);
        }
    }

    private record ParseResult<T>(T output, boolean success, String reason) {
        private static <T> ParseResult<T> success(T value) {
            return new ParseResult<>(value, true, "Valid");
        }

        private static <T> ParseResult<T> failure(String reason) {
            return new ParseResult<>(null, false, reason);
        }
    }

    private void showAllMovies() {
        ArrayList<Movie> movies = controller.getMovies();

        // TODO Print message if empty

        for (Movie movie : movies) {
            System.out.println("- " + movie.getTitle() + " (" + movie.getYearCreated() + ")");
        }
    }

    private static String formatLastWatched(Movie movie) {
        LocalDate now = LocalDate.now();
        LocalDate watchedDate = movie.getLastWatched();
        if (movie.hasBeenWatched() && now.isBefore(watchedDate)) {
            long days = movie.getDaysSinceWatched(now);
            return watchedDate.format(dateFormatter) + " (for " + days + " dage siden)";
        } else {
            return "aldrig";
        }
    }

    private void searchMovies() {
        String title = prompt("Titel", ParseResult::success);

        ArrayList<Movie> foundMovies = controller.searchMovies(title);

        if (controller.getMovies().isEmpty()) {
            System.out.println("Der er ingen film i filmsamlingen.");
            return;
        }

        switch (foundMovies.size()) {
            case 0 -> System.out.println("Søgeresultatet: '" + title + "' fandt ingen resultater.");
            case 1 -> {
                Movie movie = foundMovies.getFirst();
                System.out.println("Titel (år): " + movie.getTitle() + " (" + movie.getYearCreated() + ")");
                System.out.println("Instruktør: " + movie.getDirector());
                System.out.println("I farve: " + (movie.isInColor() ? "ja" : "nej"));
                System.out.println("Varighed: " + movie.getLengthInMinutes());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Sidst set: " + formatLastWatched(movie));
            }
            default -> {
                for (Movie movie : foundMovies) {
                    System.out.println("- " + movie.getTitle() + " (" + movie.getYearCreated() + ")");
                }
            }
        }


        //No movies in ArrayList
        //Found 0 movies
        //Found exact match
        //Found multiple matches
    }


    private void editMovie() {
        String query = prompt("Søg efter en titel: ", ParseResult::success);

        Movie selected = searchSingleMovie(query);
        if (selected == null) return;

        System.out.println("Tryk Enter for at beholde den nuværende værdi.");

        String title = promptWithDefault(
                "Titel", UserInterface::parseNotBlank,
                selected.getTitle(), Object::toString);
        String director = promptWithDefault(
                "Instruktør", UserInterface::parseNotBlank,
                selected.getDirector(), Object::toString);
        int yearCreated = this.<Integer>promptWithDefault(
                "Årstal", UserInterface::parseYearCreated,
                selected.getYearCreated(), Object::toString);
        boolean inColor = promptWithDefault(
                "I farver (ja/nej)", UserInterface::parseBoolean,
                selected.isInColor(), (color) -> color ? "ja" : "nej");
        int lengthInMinutes = promptWithDefault(
                "Længde i minutter", UserInterface::parseLengthInMinutes,
                selected.getLengthInMinutes(), Object::toString);
        String genre = promptWithDefault(
                "Genre", UserInterface::parseNotBlank,
                selected.getGenre(), Object::toString);

        controller.editMovie(selected, title, director, yearCreated, inColor, lengthInMinutes, genre);

        // TODO Print all movie info
    }

    private void printSearchResults(ArrayList<Movie> results) {
        System.out.println("Søgningen giver flere resultater: ");
        for (int i = 0; i < results.size(); i++) {
            Movie movie = results.get(i);
            System.out.println((i + 1) + ". " + movie.getTitle() + " (" + movie.getYearCreated() + ")");
        }
    }

    private void deleteMovie() {
        String title = prompt("Angiv filmens titel", UserInterface::parseNotBlank);

        Movie selected = searchSingleMovie(title);

        if (selected == null) {
            System.out.println("Film findes ikke i samling.");
            return;
        }

        boolean answer = prompt("Er du sikker på at du vil fjerne filmen fra samlingen?", UserInterface::parseBoolean);

        if (answer) {
            controller.deleteMovie(selected);
            System.out.println("Filmen er fjernet fra samlingen.");
        } else {
            System.out.println("Filmen forbliver i samlingen.");
        }
    }

    private void markMovieAsWatched() {
        String query = prompt("Søg efter en titel", ParseResult::success);

        Movie selected = searchSingleMovie(query);
        if (selected == null) {
            System.out.println("Ingen film med den titel blev fundet.");
            return;
        }

        controller.markAsWatched(selected);

        String watchedDate = selected.getLastWatched().format(dateFormatter);
        System.out.println(selected.getTitle() + " er markeret som set " + watchedDate);
    }

    private Movie searchSingleMovie(String query) {
        ArrayList<Movie> results = controller.searchMovies(query);

        Movie selected = switch (results.size()) {
            case 0 -> null;
            case 1 -> results.getFirst();
            default -> {
                int minNum = 1;
                int maxNum = results.size();

                printSearchResults(results);

                int num = prompt("Vælg nummer", (input) -> {
                    try {
                        int output = Integer.parseInt(input);
                        if (output < maxNum || output > maxNum) {
                            return ParseResult.failure("Tallet skal være mellem " + minNum + " og " + maxNum);
                        }
                        return ParseResult.success(output);
                    } catch (NumberFormatException e) {
                        return ParseResult.failure("Indtast et heltal mellem " + minNum + " og " + maxNum);
                    }
                });

                yield results.get(num - 1);
            }
        };

        if (selected == null) {
            System.out.println("Ingen film med den titel blev fundet");
            return null;
        }
        return selected;
    }

    private void showDaysSinceLastWatched() {
        int minValue = 1;

        Function<String, ParseResult<Integer>> parsePositiveIntFromString = (String input) -> {
            try {
                int parsedInt = Integer.parseInt(input);
                if (parsedInt >= minValue) {
                    return ParseResult.success(parsedInt);
                }
                return ParseResult.failure("Indtast et positivt tal.");
            } catch (NumberFormatException e) {
                return ParseResult.failure("Vælg et positivt heltal.");
            }
        };

        int days = prompt("Indtast dage", parsePositiveIntFromString);

        System.out.println("Film, du ikke har set i 30 dage eller aldrig har set:");

        ArrayList<Movie> movies = controller.getMoviesNotWatchedForDays(days);

        for (Movie movie : movies) {
            System.out.println("  " + movie.getTitle() + " - sidst set: " + formatLastWatched(movie));
        }
    }

    private void printGoodbye() {
        System.out.println("Farvel! Vi har IKKE gemt dine ændringer.");
    }
}
