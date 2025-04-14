package de.streamingservice.media;

public class Podcast extends Media {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024


    // Fields for Podcast
    private int episode;
    private String episodeTitle;

    // creates a new podcast with a passed id, podcast title, length, episode number and episode title
    public Podcast(int id, String title, int length, int episode, String episodeTitle) {
        super(id, title, length);
        this.episode = episode;
        this.episodeTitle = episodeTitle;
    }

    // getter Methods
    public int getEpisode() {
        return episode;
    }
    public String getEpisodeTitle() {
        return episodeTitle;
    }


    // returns a String visualisation of the Podcast with id, podcast title, length, episode number and episode title
    @Override
    public String toString() {
        return String.format("[<%d>] Podcast: %d. %s - %s",
                getId(),
                episode,
                getTitle(), episodeTitle);
    }
}