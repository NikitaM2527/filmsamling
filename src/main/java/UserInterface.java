import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

public final class UserInterface {
    private final Scanner scanner;
    private final Controller controller;

    public UserInterface(Controller controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
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
        printMenuOptions();

        boolean running = true;
        while (running) {
            System.out.print("Vælg: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
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
           0. Afslut
           """);
    }

    private void createMovie() {
        String title = prompt("Titel", ParseResult::success);
        String director = prompt("Instruktør", ParseResult::success);
        int yearCreated = this.<Integer>prompt("Årstal", UserInterface::parseYearCreated);
        boolean inColor = prompt("I farver (ja/nej)", UserInterface::parseBoolean);
        int lengthInMinutes = prompt("Længde i minutter", UserInterface::parseLengthInMinutes);
        String genre = prompt("Genre", ParseResult::success);

        controller.addMovie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre
        );
    }

    private <T> T prompt(String prompt, Function<String, ParseResult<T>> parser) {
        ParseResult<T> result;
        while (true) {
            System.out.print(prompt);
            System.out.print(": ");
            String input = scanner.nextLine();
            result = parser.apply(input);
            if (result.success) break;
            System.out.print("Ugyldigt input: ");
            System.out.println(result.reason);
        }

        return result.output;
    }

    private <T> T promptWithDefault(
            String prompt, Function<String, ParseResult<T>> parser,
            T defaultValue, Function<T, String> format) {
        ParseResult<T> result;
        while (true) {
            System.out.print(prompt + " [" + format.apply(defaultValue) + "]: ");
            String input = scanner.nextLine();
            if (input.isEmpty()) return defaultValue;
            result = parser.apply(input);
            if (result.success) break;
            System.out.print("Ugyldigt input: ");
            System.out.println(result.reason);
        }

        return result.output;
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

    }

    private void searchMovies() {

    }

    private void editMovie() {
        String query = prompt("Søg efter en titel", ParseResult::success);

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
                        if (output <= 0 || output > results.size()) {
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
            return;
        }

        System.out.println("Tryk Enter for at beholde den nuværende værdi.");

        String title = promptWithDefault(
                "Titel", ParseResult::success,
                selected.getTitle(), Object::toString);
        String director = promptWithDefault(
                "Instruktør", ParseResult::success,
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
                "Genre", ParseResult::success,
                selected.getGenre(), Object::toString);

        controller.editMovie(selected, title, director, yearCreated, inColor, lengthInMinutes, genre);
    }

    private void printSearchResults(ArrayList<Movie> results) {
        for (int i = 0; i < results.size(); i++) {
            Movie m = results.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getYearCreated() + ")");
        }
    }

    private void deleteMovie() {

    }

    private void printGoodbye() {
        System.out.println("Farvel! Vi har IKKE gemt dine ændringer.");
    }
}
