package dk.ek.stud.group7.filmsamling.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

final class Parsing {
    static Result<String> parseNotBlank(String input) {
        return input.isBlank()
                ? Result.failure("Feltet må ikke være tomt")
                : Result.success(input);
    }

    static Result<Integer> parseYearCreated(String input) {
        try {
            int output = Integer.parseInt(input);
            if (output < 1888) {
                return Result.failure(
                        "Verdens første film blev udgivet i 1888");
            }
            return Result.success(output);
        } catch (NumberFormatException e) {
            return Result.failure("Indtast et heltal");
        }
    }

    static Result<Boolean> parseBoolean(String input) {
        if (input.equalsIgnoreCase("ja")) {
            return Result.success(true);
        } else if (input.equalsIgnoreCase("nej")) {
            return Result.success(false);
        }

        return Result.failure("Svar ja eller nej");
    }

    static Result<Integer> parseLengthInMinutes(String input) {
        try {
            int output = Integer.parseInt(input);
            if (output <= 0) {
                return Result.failure("En film er mindst 1 minut lang");
            }
            return Result.success(output);
        } catch (NumberFormatException e) {
            return Result.failure("Indtast et heltal");
        }
    }

    static Function<String, Result<Integer>> parseIntWithinRange(int minNum, int maxNum) {
        return (input) -> {
            try {
                int output = Integer.parseInt(input);
                if (output >= minNum && output <= maxNum) {
                    return Result.success(output);
                }
                return Result.failure("Tallet skal være mellem " + minNum + " og " + maxNum);
            } catch (NumberFormatException e) {
                return Result.failure("Indtast et heltal mellem " + minNum + " og " + maxNum);
            }
        };
    }

    static Result<Integer> parsePositiveInt(String input) {
        try {
            int parsedInt = Integer.parseInt(input);
            if (parsedInt > 0) {
                return Result.success(parsedInt);
            }
            return Result.failure("Indtast et positivt tal.");
        } catch (NumberFormatException e) {
            return Result.failure("Vælg et positivt heltal.");
        }
    }

    static Result<LocalDate> parsePastDate(String input) {
        try {
            LocalDate parsedDate = LocalDate.parse(input, UserInterface.dateFormatter);
            return parsedDate.isAfter(LocalDate.now())
                    ? Result.failure("Du kan ikke have set filmen i fremtiden.")
                    : Result.success(parsedDate);
        } catch (DateTimeParseException e) {
            return Result.failure("Datoer skal være i formatet " + UserInterface.dateFormat + ".");
        }
    }

    record Result<T>(T output, boolean success, String reason) {
        static <T> Result<T> success(T value) {
            return new Result<>(value, true, "Valid");
        }

        static <T> Result<T> failure(String reason) {
            return new Result<>(null, false, reason);
        }
    }
}
