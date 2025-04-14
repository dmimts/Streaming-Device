package de.streamingservice.media;

public class Song extends Media {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024

    // Fields for Song
    private String artist;
    private Genre genre;

    // creates a new song with an id, title, length, artist and genre
    public Song(int id, String title, int length, String artist, Genre genre) {
        super(id, title, length);
        if (artist == null || artist.isEmpty()) {
            throw new IllegalArgumentException("Der Künstlername darf nicht leer sein."); // checks if there is an artist
        }
        if (genre == null) {
            throw new IllegalArgumentException("Genre darf nicht null sein."); // checks if there is a genre
        }
        this.artist = artist;
        this.genre = genre;
    }

    // getter Methods
    public String getArtist() {
        return artist;
    }
    public Genre getGenre() {
        return genre;
    }

    // returns a String visualisation with id, artist, title and genre
    @Override
    public String toString() {
        return String.format("[<%d>] Song: %s. %s, Genre: %s", getId(), artist, getTitle(), genre);
    }
}