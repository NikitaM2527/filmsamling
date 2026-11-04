package dk.ek.stud.group7.filmsamling;

import dk.ek.stud.group7.filmsamling.controller.Controller;
import dk.ek.stud.group7.filmsamling.controller.FileHandler;
import dk.ek.stud.group7.filmsamling.model.Movie;
import dk.ek.stud.group7.filmsamling.model.MovieCollection;
import dk.ek.stud.group7.filmsamling.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        MovieCollection movieCollection = new MovieCollection();
        FileHandler fileHandler = new FileHandler("docs/movie.csv");

        Controller controller = new Controller(movieCollection, fileHandler);

        new UserInterface(controller).startProgram();
    }
}
