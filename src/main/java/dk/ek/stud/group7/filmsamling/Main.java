package dk.ek.stud.group7.filmsamling;

import dk.ek.stud.group7.filmsamling.controller.Controller;
import dk.ek.stud.group7.filmsamling.model.MovieCollection;
import dk.ek.stud.group7.filmsamling.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        new UserInterface(new Controller(new MovieCollection())).startProgram();
    }
}
