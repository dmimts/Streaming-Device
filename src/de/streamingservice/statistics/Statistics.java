package de.streamingservice.statistics;

public class Statistics {

    // Fields for statistics
    private int playedSeconds;
    private int playedSongs;
    private int playedPodcasts;

    // creates a new Statistics object with the default values
    public Statistics() {
        this.playedSeconds = 0;
        this.playedSongs = 0;
        this.playedPodcasts = 0;
    }

    // The following methods update the seconds, played songs and played podcasts of the user

    public void incrementPlayedSeconds(int value) {
        if (value > 0) {
            playedSeconds += value;
        }
    }
    public void incrementPlayedSongs() {
        playedSongs += 1;
    }
    public void incrementPlayedPodcasts() {
        playedPodcasts += 1;
    }

    // getter methods

    public String getPlayedSeconds() {          // String format for a more appealing look
        int hours = playedSeconds / 3600;
        int minutes = (playedSeconds % 3600) / 60;
        int seconds = playedSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }
    public int getPlayedSongs() {
        return playedSongs;
    }
    public int getPlayedPodcasts() {
        return playedPodcasts;
    }


    // returns a String visualisation with played seconds, songs and podcasts
    @Override
    public String toString() {
        return String.format("Played Seconds: %d\nPlayed Songs: %d\nPlayed Podcasts: %d",
                playedSeconds, playedSongs, playedPodcasts);
    }
}