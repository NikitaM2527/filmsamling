import java.util.Scanner;
import java.util.function.Function;

public final class UserInterface {
    private final Scanner scanner;
    private final Controller controller;

    public UserInterface(Controller controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
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
        String title = prompt("Titel: ", ParseResult::success);
        String director = prompt("Instruktør: ", ParseResult::success);

        int yearCreated = this.<Integer>prompt("Årstal: ", (input) -> {
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
        });
        boolean inColor = prompt("I farver (ja/nej): ", (input) -> {
            if (input.equalsIgnoreCase("ja")) {
                return ParseResult.success(true);
            } else if (input.equalsIgnoreCase("nej")) {
                return ParseResult.success(false);
            }

            return ParseResult.failure("Svar ja eller nej");
        });
        int lengthInMinutes = prompt("Længde i minutter: ", (input) -> {
            try {

                int output = Integer.parseInt(input);
                if (output <= 0) {
                    return ParseResult.failure("En film er mindst 1 minut lang");
                }
                return ParseResult.success(output);
            } catch (NumberFormatException e) {
                return ParseResult.failure("Indtast et heltal");
            }
        });
        String genre = prompt("Genre: ", ParseResult::success);

        controller.addMovie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre
        );
    }

    private static ParseResult<String> parseName(String name) {
        return ParseResult.success("placeholder");
    }

    private <T> T prompt(String prompt, Function<String, ParseResult<T>> parser) {
        ParseResult<T> result;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            result = parser.apply(input);
            if (result.success) {
                System.out.print("Ugyldigt input: ");
                System.out.println(result.reason);
            } else break;
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

    }

    private void deleteMovie() {

    }

    private void printGoodbye() {
        System.out.println("Farvel! Vi har IKKE gemt dine ændringer.");
    }
}
