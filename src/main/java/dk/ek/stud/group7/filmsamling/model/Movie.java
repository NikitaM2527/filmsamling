package dk.ek.stud.group7.filmsamling.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Movie implements Comparable<Movie> {
    private String title;
    private String director;
    private int yearCreated;
    private boolean inColor;
    private int lengthInMinutes;
    private String genre;
    private LocalDate lastWatched;

    private Movie(Builder builder) {
        this(
                builder.title,
                builder.director,
                builder.yearCreated,
                builder.inColor,
                builder.lengthInMinutes,
                builder.genre,
                builder.lastWatched);
    }

    public Movie(
            String title,
            String director,
            int yearCreated,
            boolean inColor,
            int lengthInMinutes,
            String genre,
            LocalDate lastWatched) {
        validateTitle(title);
        validateDirector(director);
        validateYearCreated(yearCreated);
        validateLengthInMinutes(lengthInMinutes);
        validateGenre(genre);

        this.title = title;
        this.director = director;
        this.yearCreated = yearCreated;
        this.inColor = inColor;
        this.lengthInMinutes = lengthInMinutes;
        this.genre = genre;
        this.lastWatched = lastWatched;
    }

    public String getTitle() {
        return title;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Filmen skal have en titel");
        }
        if (title.contains("|")) {
            throw new IllegalArgumentException("Filmens titel må ikke indeholde tegnet '|'");
        }
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    private static void validateDirector(String director) {
        if (director == null || director.isBlank()) {
            throw new IllegalArgumentException("Filmen skal have en instruktør");
        }
    }

    public void setDirector(String director) {
        validateDirector(director);
        this.director = director;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    private static void validateYearCreated(int year) {
        if (year < 1888) {
            throw new IllegalArgumentException("Den første film er fra år 1888");
        }
    }

    public void setYearCreated(int yearCreated) {
        validateYearCreated(yearCreated);
        this.yearCreated = yearCreated;
    }

    public boolean isInColor() {
        return inColor;
    }

    public void setInColor(boolean inColor) {
        this.inColor = inColor;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    private static void validateLengthInMinutes(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("En film skal være længere end 0 minutter");
        }
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        validateLengthInMinutes(lengthInMinutes);
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    private static void validateGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Filmen skal have en genre");
        }
    }

    public void setGenre(String genre) {
        validateGenre(genre);
        this.genre = genre;
    }

    public boolean hasBeenWatched() {
        return lastWatched != null;
    }

    public LocalDate getLastWatched() {
        return lastWatched;
    }

    public void markAsWatched(LocalDate date) {
        this.lastWatched = date;
    }

    public long getDaysSinceWatched(LocalDate now) {
        return ChronoUnit.DAYS.between(lastWatched, now);
    }

    @Override
    public int compareTo(Movie other) {
        return this.title.compareTo(other.title);
    }

    public static class Builder {
        private String title;
        private String director;
        private int yearCreated;
        private boolean inColor;
        private int lengthInMinutes;
        private String genre;
        private LocalDate lastWatched;

        public Builder title(String title) {
            validateTitle(title);
            this.title = title;
            return this;
        }

        public Builder director(String director) {
            validateDirector(director);
            this.director = director;
            return this;
        }

        public Builder yearCreated(int year) {
            validateYearCreated(year);
            this.yearCreated = year;
            return this;
        }

        public Builder inColor(boolean inColor) {
            this.inColor = inColor;
            return this;
        }

        public Builder lengthInMinutes(int length) {
            validateLengthInMinutes(length);
            this.lengthInMinutes = length;
            return this;
        }

        public Builder genre(String genre) {
            validateGenre(genre);
            this.genre = genre;
            return this;
        }

        public Builder lastWatched(LocalDate date) {
            this.lastWatched = date;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
