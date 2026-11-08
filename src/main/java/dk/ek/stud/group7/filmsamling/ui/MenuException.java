package dk.ek.stud.group7.filmsamling.ui;

import java.util.List;

sealed abstract class MenuException extends Exception {
    static final class NotFound extends MenuException {
    }

    static final class Ambiguous extends MenuException {
        public final List<String> matches;

        Ambiguous(List<String> matches) {
            this.matches = matches;
        }
    }
}
