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
