package de.streamingservice;

import de.streamingservice.media.*;
import de.streamingservice.statistics.Statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024

    // Fields for Player
    private User user;
    private Media currentlyPlaying;
    private Playlist currentPlaylist;
    private boolean isPlaying;
    private int level;
    private int pastSeconds;
    private Statistics statistics;
    private MediaLibrary MediaLibrary;

    // All the Fields, Buttons and Labels for the GUI;
    private JFrame playerFrame;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton nextButton;
    private JButton previousButton;
    private JButton volumeDownButton;
    private JButton volumeUpButton;
    private JLabel audioNameLabel;
    private JLabel timeLabel;

    // creates a new Player with a passed user as the user and sets all the other adjectives to the default values
    public Player(User user) {
        this.user = user;
        this.level = 50;
        this.pastSeconds = 0;
        this.isPlaying = false;
        this.statistics = new Statistics();
        this.MediaLibrary = new MediaLibrary();
    }

    // plays a song or podcast
    public void play(Media media) {
        this.currentlyPlaying = media;
        this.pastSeconds = 0;
        this.isPlaying = true;
        if (media instanceof Song) {            // checks if the media is a song or a podcast
            statistics.incrementPlayedSongs();
        } else if (media instanceof Podcast) {
            statistics.incrementPlayedPodcasts();
        }
    }

    // sets a playlist as the current playlist and start the player
    public void play(Playlist playlist){
        this.currentPlaylist = playlist;
        play();
    }

    // Starts playback of the first medium in the current playlist
    public void play() {
        if (currentPlaylist == null) { // checks if there is a playlist to play and puts out an error message if there is not
            JOptionPane.showMessageDialog(null, "Create a playlist or Search for a Song first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isPlaying) { // checks if some media is already playing
            System.out.println("Already playing...");
            return;
        }
        if (currentlyPlaying == null) {
            currentlyPlaying = currentPlaylist.getMedia()[0];
        }
        if (currentlyPlaying != null) {
            isPlaying = true;
            System.out.println("Playing: " + currentlyPlaying.getTitle());
            updateLabels();
        } else {
            System.out.println("Nothing to play...");
        }
    }

    // pauses the playback
    public void pause() {
        if (isPlaying && currentlyPlaying != null) {
            statistics.incrementPlayedSeconds(pastSeconds); // updates the statistics of the user
        }
        isPlaying = false;
    }

    // stops the playback completely and resets the player
    public void stop() {
        if (currentlyPlaying != null) {
            statistics.incrementPlayedSeconds(pastSeconds); // updates the statistics of the user
            updateLabels();
        }
        isPlaying = false;
        currentlyPlaying = null;
        currentPlaylist = null;
        pastSeconds = 0;
    }

    // plays the next media
    public void next() {
        if (currentPlaylist != null) { // checks if the a next instance is available, puts out a message if there is not
            Media[] playlistMedia = currentPlaylist.getMedia();
            int currentIndex = -1;
            for (int i = 0; i < playlistMedia.length; i++) { // sets the currentIndex to the index of the current media
                if (playlistMedia[i].equals(currentlyPlaying)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex >= 0 && currentIndex + 1 < playlistMedia.length) { //plays the media with index +1 to the current index
                currentlyPlaying = playlistMedia[currentIndex + 1];
                pastSeconds = 0;
                if (currentlyPlaying instanceof Song) {
                    statistics.incrementPlayedSongs(); // updates the statistics
                } else if (currentlyPlaying instanceof Podcast) {
                    statistics.incrementPlayedPodcasts();
                }
                updateLabels();
            } else {
                System.out.println("End of playlist...");
                stop();
                updateLabels();
            }
        } else {
            stop();
        }
    }

    // Plays the previous media the same way as the next media above
    public void previous() {
        if (currentPlaylist != null) {
            Media[] playlistMedia = currentPlaylist.getMedia();
            int currentIndex = -1;
            for (int i = 0; i < playlistMedia.length; i++) {
                if (playlistMedia[i].equals(currentlyPlaying)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex > 0) {
                currentlyPlaying = playlistMedia[currentIndex - 1];
                pastSeconds = 0;
                updateLabels();
            } else {
                System.out.println("Start of playlist...");
                stop();
            }
        } else {
            stop();
        }
    }

    // skips 10 seconds of the song
    public void skip(int seconds) {
        if (currentlyPlaying != null) {
            manualTimeAdjust(seconds); // uses the manualTimeAdjust method
            if (pastSeconds >= currentlyPlaying.getLength()) {
                next();
            }
        }
    }

    // rewinds 10 seconds of the song
    public void reSkip(int seconds) {
        if (currentlyPlaying != null) {
            manualTimeAdjust(-seconds); // uses the manualTimeAdjust method
            if (pastSeconds < 0) {
                pastSeconds = 0;
            }
        }
    }

    // Method to skip time without manipulating statistics
    private void manualTimeAdjust(int seconds) {
        pastSeconds += seconds;
        if (pastSeconds < 0) {
            pastSeconds = 0;
        } else if (pastSeconds > currentlyPlaying.getLength()) {
            pastSeconds = currentlyPlaying.getLength();
        }
    }

    // Simple "listening" for the passed seconds
    public void listen(int seconds) {
        if (currentlyPlaying != null) {
            pastSeconds += seconds;
            if (pastSeconds >= currentlyPlaying.getLength()) {
                next();
            }
        }
    }

    // increments the volume by the passed number (default set to 5)
    public int volumeUp(int increment) {
        int newVolume = level + increment;
        if (newVolume > 100) {      // checks if the incremented volume is within bounds
            newVolume = 100;
            System.out.println("Max volume = " + newVolume);
        } else {
            level = newVolume;
            System.out.println("Volume up = " + level);
        }
        level = newVolume;
        return level;
    }

    // decrements the volume by the passed number (default set to 5)
    public int volumeDown(int decrement) {
        int newVolume = level - decrement;
        if (newVolume < 0) { // checks if the decremented volume is within bounds
            newVolume = 0;
            System.out.println("Min volume = " + newVolume);
        } else {
            level = newVolume;
            System.out.println("Volume down = " + level);
        }
        level = newVolume;
        return level;
    }

    // sets Playlist
    public void setCurrentPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        System.out.println("Active playlist changed to " + playlist.getTitle());
    }
    //Method to play media instances by themselves
    public void playMediaById(int id) {
        Media foundMedia = MediaLibrary.searchMedia(id);
        if (foundMedia != null) { // checks if media exists
            play(foundMedia);
            JOptionPane.showMessageDialog(playerFrame, "Playing: " + foundMedia.getTitle(), "Media Found", JOptionPane.INFORMATION_MESSAGE);
        } else { // error message if media does not exist
            JOptionPane.showMessageDialog(playerFrame, "Media not found with ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter methods
    public int getVolume() { return level; }
    public Statistics getStatistics() { return statistics; }
    public Media getCurrentlyPlaying() { return currentlyPlaying; }
    public Playlist getCurrentPlaylist() { return currentPlaylist; }
    public boolean getIsPlaying() { return isPlaying; }
    public int getPastSeconds() { return pastSeconds; }

    // Returns information of the Player
    @Override
    public String toString() {
        if (currentlyPlaying != null) {
            return String.format( // a String visualisation of the currently playing media
                    "Currently playing: %s\nPast seconds: %d\nVolume: %d",
                    currentlyPlaying.toString(), pastSeconds, level
            );
        }
        return "No media is currently playing.";
    }

    // Creates the Player Window with the GUI Methods
    public void PlayerGUI() {
        playerFrame = new JFrame("Player");
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new FlowLayout());

        JLabel audioLabel = new JLabel("Currently playing: ");
        audioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(audioLabel);

        // creates the "currently playing" text field

        audioNameLabel = new JLabel();
        audioNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(audioNameLabel);

        // creates the timer

        timeLabel = new JLabel("0:00");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(timeLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        // creates all the Buttons and assigns the methods

        previousButton = new JButton("\u23EE");
        previousButton.addActionListener(e -> previous());

        playButton = new JButton("\u25B6");
        playButton.addActionListener(e -> play());

        pauseButton = new JButton("\u23F8");
        pauseButton.addActionListener(e -> pause());

        stopButton = new JButton("\u23F9");
        stopButton.addActionListener(e -> stop());

        nextButton = new JButton("\u23ED");
        nextButton.addActionListener(e -> next());

        volumeUpButton = new JButton("\u002B");
        volumeUpButton.addActionListener(e -> volumeUp(5));

        volumeDownButton = new JButton("\u2212");
        volumeDownButton.addActionListener(e -> volumeDown(5));

        JButton skipButton = new JButton("\u21BB");
        skipButton.addActionListener(e -> skip(10));

        JButton reSkipButton = new JButton("\u21BA");
        reSkipButton.addActionListener(e -> reSkip(10));

        JButton shuffleButton = new JButton("\u21C6");
        shuffleButton.addActionListener(e -> {
            if (currentPlaylist != null) { // checks if there is a playlist to shuffle
                currentPlaylist = MediaLibrary.shufflePlaylist(currentPlaylist);
                JOptionPane.showMessageDialog(playerFrame, "Shuffled: " + currentPlaylist.getTitle());
            } else {
                JOptionPane.showMessageDialog(playerFrame, "No playlist to shuffle.");
            }
        });

        // creates the button to generate a new Playlist
        JButton createPlaylistButton = new JButton("+ Create Playlist");
        createPlaylistButton.addActionListener(e -> {
            String playlistName = JOptionPane.showInputDialog(playerFrame, "Enter playlist name:");
            if (playlistName != null && !playlistName.trim().isEmpty()) {
                int option = JOptionPane.showConfirmDialog(  // asks if the playlist only should contain podcasts
                        playerFrame,
                        "Should the playlist only contain podcasts?",
                        "Playlist Type",
                        JOptionPane.YES_NO_OPTION
                );
                boolean isPodcastPlaylist = option == JOptionPane.YES_OPTION;
                Genre genreFilter = null;

                if (!isPodcastPlaylist) { // checks if the playlist is an "only podcast" playlist and then asks for the genre
                    String[] genres = {"Pop", "Rock", "Metal", "K-Pop"};
                    String selectedGenre = (String) JOptionPane.showInputDialog(
                            playerFrame,
                            "Choose your genre:",
                            "Genre",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            genres,
                            genres[0]
                    );
                    if (selectedGenre != null) {
                        genreFilter = Genre.valueOf(selectedGenre.toUpperCase().replace("-", "_"));
                    }
                }

                Playlist newPlaylist = MediaLibrary.createPlaylist(playlistName, isPodcastPlaylist, genreFilter); // creates the new Playlist
                if (newPlaylist.getMedia().length > 0) {
                    currentPlaylist = newPlaylist;
                    JOptionPane.showMessageDialog(playerFrame, "Playlist : " + playlistName+ " has been created.");
                } else {
                    JOptionPane.showMessageDialog(playerFrame, "No media added to playlist");
                }
            } else {
                JOptionPane.showMessageDialog(playerFrame, "Playlist name cannot be empty.");
            }
        });

        JButton statisticsButton = new JButton("Statistics"); // creates a visual representation of the statistics of the user
        statisticsButton.addActionListener(e -> {
            String statsMessage = String.format(
                    "Played seconds: %s\nSongs: %d\nPodcasts: %d",
                    statistics.getPlayedSeconds(),
                    statistics.getPlayedSongs(),
                    statistics.getPlayedPodcasts()
            );
            JOptionPane.showMessageDialog(playerFrame, statsMessage);
        });

        JButton searchMediaButton = new JButton("\uD83D\uDD0D");    // creates the button for searching media
        searchMediaButton.setToolTipText("Search Media");
        searchMediaButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(playerFrame, "Enter Media ID or Title:");
            if (input != null) {
                try {
                    int id = Integer.parseInt(input);
                    playMediaById(id); // Plays a medium based on the ID
                } catch (NumberFormatException ex) {
                    Media[] foundMedia = MediaLibrary.searchMedia(input); // Searches for title
                    if (foundMedia.length == 1) {
                        play(foundMedia[0]); // Plays the found media
                        JOptionPane.showMessageDialog(playerFrame, "Playing: " + foundMedia[0].getTitle(), "Media Found", JOptionPane.INFORMATION_MESSAGE);
                    } else if (foundMedia.length > 1) {
                        JOptionPane.showMessageDialog(playerFrame, "Multiple media found with the same title.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(playerFrame, "No media found with title: " + input, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // adds all the buttons to the panels
        bottomPanel.add(playButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(shuffleButton);
        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(volumeUpButton);
        bottomPanel.add(volumeDownButton);
        bottomPanel.add(skipButton);
        bottomPanel.add(reSkipButton);

        topPanel.add(createPlaylistButton, BorderLayout.NORTH);
        topPanel.add(statisticsButton, BorderLayout.WEST);
        topPanel.add(searchMediaButton, BorderLayout.EAST);


        playerFrame.add(topPanel, BorderLayout.NORTH);
        playerFrame.add(centerPanel, BorderLayout.CENTER);
        playerFrame.add(bottomPanel, BorderLayout.SOUTH);

        playerFrame.pack();
        playerFrame.setVisible(true);

        // Timer for automatic time update
        Timer timer = new Timer(1000, e -> {
            if (currentlyPlaying != null) {
                if (isPlaying) {
                    pastSeconds++;
                    statistics.incrementPlayedSeconds(1);
                    if (pastSeconds >= currentlyPlaying.getLength()) {
                        next();
                    }
                }
                updateLabels();
            } else {
                audioNameLabel.setText("-");
                timeLabel.setText("-");
            }
        });
        timer.start();
    }

    // updates the labels of the playback
    private void updateLabels() {
        if (currentlyPlaying != null) {
            String displayTitle = currentlyPlaying.getTitle();
            if (currentlyPlaying instanceof Podcast) {
                Podcast p = (Podcast) currentlyPlaying;
                displayTitle += " - " + p.getEpisodeTitle();
            }
            audioNameLabel.setText(displayTitle);
            int seconds = pastSeconds % 60;
            int minutes = pastSeconds / 60;
            timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
        } else {
            audioNameLabel.setText("-");
            timeLabel.setText("-");
        }
    }

    // The Login-GUI as inner class
    public class LoginGUI {
        private JFrame loginFrame;
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private User user;

        public LoginGUI() {
            createGUI();
        }

        // Login-Window
        private void createGUI() {
            loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setLayout(new FlowLayout());

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField(10);
            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField(10);

            loginButton = new JButton("Login");
            loginButton.addActionListener(e -> login());

            loginFrame.add(usernameLabel);
            loginFrame.add(usernameField);
            loginFrame.add(passwordLabel);
            loginFrame.add(passwordField);
            loginFrame.add(loginButton);

            loginFrame.pack();
            loginFrame.setVisible(true);
        }

        private void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // User for testing purpose
            user = new User("maxi1", "password", "Max", "Mustermann");

            if (user.login(password)) {
                user.isLoggedIn = true;
                loginFrame.dispose();
                Player player = new Player(user);
                player.PlayerGUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Wrong user or password! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}