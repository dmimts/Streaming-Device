package de.streamingservice.media;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024


    // Fields for Playlist
    private static int nextId = 1; // Automatische ID-Vergabe
    private int playlistId;
    private String title;
    private List<Media> media;
    private static List<Playlist> playlists = new ArrayList<>();

    // creates a new playlist with the passed id, title and media
    public Playlist(int id, String title, Media[] media){
        this.playlistId = id;
        this.title = title;
        this.media = List.of(media);
        playlists.add(this);
    }

    // creates a new playlist with a passed name
    public Playlist(String title) {
        this.playlistId = nextId++;
        this.title = title;
        this.media = new ArrayList<>();
        playlists.add(this); // add the new palylist to the list of playlists
    }

    // getter Methods
    public int getId() {
        return playlistId;
    }
    public String getTitle() {
        return title;
    }
    public Media[] getMedia() {
        return media.toArray(new Media[0]);
    }

    // adds a new media instance
    public void addMedia(Media mediaItem) {
        if (mediaItem != null && !media.contains(mediaItem)) { // checks if the instance already exists
            media.add(mediaItem);
        }
    }

    // removes a media instance
    public void removeMedia(Media mediaItem) {
        media.remove(mediaItem);
    }

    // returns a String visualisation of the Playlist with if, tile and all the instances
    @Override
    public String toString() {
        return "Playlist ID: " + playlistId + ", Title: " + title + ", Items: " + media.size();
    }
}