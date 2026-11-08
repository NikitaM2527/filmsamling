package dk.ek.stud.group7.filmsamling.ui;

public interface ConsoleIO {
    void print(String s);

    void println(String s);

    default void println() { println(""); }

    String readLine();
}
