import java.time.LocalDate;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Controller controller;

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
        String title = promptString("Titel: ");
        String director = promptString("Instruktør: ");
        int yearCreated = promptYearCreated("Årstal: ");
        boolean inColor = promptBoolean("I farve (ja/nej): ");
        int lengthInMinutes = promptLengthInMinutes("Længde i minutter: ");
        String genre = promptString("Genre: ");

        controller.addMovie(
                title,
                director,
                yearCreated,
                inColor,
                lengthInMinutes,
                genre
        );

        System.out.println(title + " er blevet tilføjet til samlingen.");
    }

    private String promptString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int promptYearCreated(String prompt) {
        int maxYear = LocalDate.now().getYear() + 10;

        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                int output = Integer.parseInt(raw);
                if (output > maxYear) {
                    printInvalidInput(PromptError.MovieFromTheDistantFuture);
                } else if (output < 1888) {
                    printInvalidInput(PromptError.MovieTooOld);
                } else {
                    return output;
                }
            } catch (NumberFormatException e) {
                printInvalidInput(PromptError.InvalidInt);
            }
        }
    }

    private int promptLengthInMinutes(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                int output = Integer.parseInt(raw);
                if (output <= 0) {
                    printInvalidInput(PromptError.MovieTooShort);
                } else {
                    return output;
                }
            } catch (NumberFormatException e) {
                printInvalidInput(PromptError.InvalidInt);
            }
        }
    }

    private void printInvalidInput(PromptError promptError) {
        String message = switch (promptError) {
            case MovieTooOld -> "Verdens ældste film er fra 1888.";
            case MovieFromTheDistantFuture -> "Vi accepterer ikke film fra mere end 10 år i fremtiden.";
            case MovieTooShort -> "Film skal vare længere end ét minut.";
            case InvalidBoolean -> "Svar ja eller nej.";
            case InvalidInt -> "Indtast et heltal.";
        };

        System.out.println(message);
    }

    private boolean promptBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            if (raw.equalsIgnoreCase("ja")) {
                return true;
            } else if (raw.equalsIgnoreCase("nej")) {
                return false;
            }

            printInvalidInput(PromptError.InvalidBoolean);
        }
    }

    enum PromptError {
        MovieTooOld,
        MovieFromTheDistantFuture,
        MovieTooShort,
        InvalidBoolean,
        InvalidInt,
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
