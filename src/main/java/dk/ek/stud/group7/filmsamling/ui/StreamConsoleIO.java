package dk.ek.stud.group7.filmsamling.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StreamConsoleIO implements ConsoleIO {
    private final Scanner in;
    private final PrintStream out;

    public StreamConsoleIO(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
    }

    @Override
    public void print(String s) {
        out.print(s);
    }

    @Override
    public void println(String s) {
        out.println(s);
    }

    @Override
    public String readLine() {
        return in.nextLine();
    }
}
