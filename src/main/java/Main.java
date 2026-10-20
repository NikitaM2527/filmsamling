public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Velkommen til min filmsamling!
                """);

        new UserInterface(new Controller(new MovieCollection())).startProgram();
    }
}
