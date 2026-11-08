package dk.ek.stud.group7.filmsamling;

import dk.ek.stud.group7.filmsamling.controller.Controller;
import dk.ek.stud.group7.filmsamling.io.FileHandler;
import dk.ek.stud.group7.filmsamling.model.MovieCollection;
import dk.ek.stud.group7.filmsamling.ui.ConsoleIO;
import dk.ek.stud.group7.filmsamling.ui.StreamConsoleIO;
import dk.ek.stud.group7.filmsamling.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        ConsoleIO io = new StreamConsoleIO(System.in, System.out);

        MovieCollection movieCollection = new MovieCollection();
        FileHandler fileHandler = new FileHandler("movies.csv");
        Controller controller = new Controller(movieCollection, fileHandler);

        new UserInterface(io, controller).startProgram();
    }
}
