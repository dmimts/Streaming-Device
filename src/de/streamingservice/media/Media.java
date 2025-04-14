package de.streamingservice.media;

public abstract class Media {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024

    // Field for Media
    private int mediaId;
    private String title;
    private int length;

    // implements a new Media object and instantiates the id, title and length
    public Media(int id, String title, int length) {
        this.mediaId = id;
        this.title = title;
        this.length = length;
    }

    // getter Methods
    public int getId() {
        return mediaId;
    }
    public String getTitle() {
        return title;
    }
    public int getLength() {
        return length;
    }


    // checks if two Media objects are the same, based on their id
    @Override
    public boolean equals(Object obj) {
        if (((Media) obj).getId() == mediaId) {
            return true;
        }
        return false;
    }

    // returns a hash code for the Media object, based on its mediaId
    @Override
    public int hashCode() {
        return Integer.hashCode(mediaId);
    }

    // returns a String visualisation of the current Media based on id, title and length
    @Override
    public String toString() {
        return String.format("ID: %d\nTitle: %s\nLength: %d", mediaId, title, length);
    }
}