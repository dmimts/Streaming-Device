package de.streamingservice.media;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaLibrary {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024

    // Fields for MediaLibrary
    private Media[] media;
    private Playlist[] playlists;

    // creates a new MediaLibrary
    public MediaLibrary() {
        //creates the ArrayLists to be used later
        List<Media> mediaList = new ArrayList<>();
        List<Playlist> playlistList = new ArrayList<>();

        //loads all songs and podcasts from the .csv files with the methods below
        mediaList.addAll(loadSongs("src/resources/songs_with_ids.csv"));
        mediaList.addAll(loadPodcasts("src/resources/podcasts_with_ids.csv"));

        //checks if the ArrayList is empty and puts out an error message if it is
        if (mediaList.isEmpty()) {
            System.err.println("No media found.");
        }

        // adds all the podcasts and songs into the arraylists
        this.media = mediaList.toArray(new Media[0]);
        this.playlists = playlistList.toArray(new Playlist[0]);
    }

    // uses a BufferedReader to get all the songs and belonging information
    private List<Song> loadSongs(String filePath) {
        List<Song> songs = new ArrayList<>();

        //creates the Buffered reader
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Process the line with the parseCSVLine method
                    String[] parts = parseCSVLine(line);
                    if (parts.length < 5) {     // checks if the current song has more information than allowed
                        System.err.println("Ungültige Zeile erkannt (Übersprungen): " + line);
                        continue;
                    }

                    int id = Integer.parseInt(parts[0].trim());
                    String artist = parts[1].trim();
                    String title = parts[2].trim();
                    int length = Integer.parseInt(parts[3].trim());
                    String genreString = parts[4].replaceAll("\"", "").trim();
                    Genre parsedGenre;

                    // Mapping from String to Genre
                    switch (genreString) {
                        case "Pop" -> parsedGenre = Genre.POP;
                        case "Rock" -> parsedGenre = Genre.ROCK;
                        case "Metal" -> parsedGenre = Genre.METAL;
                        case "K-Pop" -> parsedGenre = Genre.K_POP;
                        default -> {
                            System.err.println("Unbekanntes Genre erkannt: " + genreString + " (Standardwert POP wird verwendet)");
                            parsedGenre = Genre.POP; // Standardwert
                        }
                    }

                    // adding song
                    songs.add(new Song(id, title, length, artist, parsedGenre));
                } catch (Exception e) {
                    System.err.println("Fehler beim Parsen der Zeile (Übersprungen): " + line); //error message
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs; //returns the ArrayList with all the songs
    }

    // Helper method for parsing a CSV line with quotation marks
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"' && (current.length() == 0 || insideQuotes)) {
                // Switching the inner status when quotation marks are found
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                // Close column if a comma is found outside quotation marks
                result.add(current.toString());
                current.setLength(0);
            } else {
                // adding char to the current column
                current.append(c);
            }
        }
        // adds to the last column
        result.add(current.toString());
        return result.toArray(new String[0]);
    }

    // Does the exact same as in loadSongs but for the podcasts
    private List<Podcast> loadPodcasts(String filePath) {
        List<Podcast> podcasts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Verwende die vorhandene parseCSVLine-Methode
                    String[] parts = parseCSVLine(line);
                    if (parts.length < 5) {
                        System.err.println("Ungültige Zeile erkannt (Übersprungen): " + line);
                        continue;
                    }

                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    int episode = Integer.parseInt(parts[2].trim());
                    String episodeTitle = parts[3].trim();
                    int length = Integer.parseInt(parts[4].trim());

                    podcasts.add(new Podcast(id, title, length, episode, episodeTitle));
                } catch (Exception e) {
                    System.err.println("Fehler beim Parsen der Zeile (Übersprungen): " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return podcasts;
    }

    //creates a new playlist based on Name and genre and it differentiates between podcast and song
    public Playlist createPlaylist(String playlistName, boolean isPodcastPlaylist, Genre genreFilter) {
        Playlist newPlaylist = new Playlist(playlistName);

        for (Media media : this.media) {
            if (isPodcastPlaylist && media instanceof Podcast) {
                newPlaylist.addMedia(media); // Adds only podcasts
            } else if (!isPodcastPlaylist && media instanceof Song) {
                Song song = (Song) media;
                if (genreFilter == null || song.getGenre() == genreFilter) {
                    newPlaylist.addMedia(song); // Either add all songs or only with a certain genre
                }
            }
        }

        return newPlaylist;
    }

    // shuffles the order in a certain playlist with the Collections.shuffle method
    public Playlist shufflePlaylist(Playlist playlist) {
        java.util.List<Media> mediaList = new ArrayList<>(java.util.Arrays.asList(playlist.getMedia()));
        Collections.shuffle(mediaList);

        Playlist shuffledPlaylist = new Playlist(playlist.getTitle() + " (Shuffled)"); // creates new shuffled playlist
        for (Media media : mediaList) {
            shuffledPlaylist.addMedia(media); // adds all the instances of the original playlist into the new one
        }

        return shuffledPlaylist;
    }

    // searches an instance of a Media object based on its unique id
    public Media searchMedia(int id) {
        for (Media m : media) {         // uses a for loop to search for a matching id
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    // searches for an instance based on its title
    public Media[] searchMedia(String title) {
        return List.of(media).stream()
                .filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase()))  //turns all the titles into lowercase for easier and more reliable search
                .toArray(Media[]::new);
    }

    // searches a Playlist based on its unique id
    public Playlist searchPlaylist(int id) {
        for (Playlist p : playlists) {  // uses a for loop to search for a matching id
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // searches for an instance based on its title
    public Playlist[] searchPlaylist(String title) {
        return List.of(playlists).stream()
                .filter(p -> p.getTitle().toLowerCase().contains(title.toLowerCase()))  //turns all the titles into lowercase for easier and more reliable search
                .toArray(Playlist[]::new);
    }

    //getter Methods
    public Media[] getMedia() {
        return media;
    }
    public Playlist[] getPlaylists() {
        return playlists;
    }
}