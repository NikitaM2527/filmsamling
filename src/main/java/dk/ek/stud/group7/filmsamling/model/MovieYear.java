package dk.ek.stud.group7.filmsamling.model;

import java.time.LocalDate;

// This is a value type; its value is never changed.
// If you need a new value, you create a new object.
public record MovieYear(int value) {
    public static final int EARLIEST_VALID_YEAR = 1888;

    // You can leave out the list of parameters,
    // and it will use the known list of attributes.
    // (This only works for records.)
    public MovieYear {
        if (value < EARLIEST_VALID_YEAR)
            throw new YearBefore1888Exception(value);
        if (value > latestValidYear()) {
            throw new YearAfterNextYearException(value);
        }
    }

    private static int latestValidYear() {
        return LocalDate.now().getYear() + 1;
    }

    // The class defines its own domain-level exceptions describing the failure states.
    // This is important: the UI will be able to translate this into a user-friendly error message.
    // For this reason, we must capture all the relevant information about the error.
    // (In this case what the invalid year was.)
    // The earliest valid year is already defined publicly, so that the UI can inform the user.
    public static class YearBefore1888Exception extends RuntimeException {
        public final int invalidYear; // public so that the user can be notified.

        public YearBefore1888Exception(int invalidYear) {
            super("Year must not be less than " + MovieYear.EARLIEST_VALID_YEAR + " (was " + invalidYear + ")");
            this.invalidYear = invalidYear;
        }
    }

    public static class YearAfterNextYearException extends RuntimeException {
        public final int invalidYear;

        public YearAfterNextYearException(int invalidYear) {
            super("Year " + invalidYear + " is too far in the future (should be less than or equal to " + latestValidYear() + ")");

            this.invalidYear = invalidYear;
        }
    }
}
