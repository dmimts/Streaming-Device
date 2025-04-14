package de.streamingservice;

public class Main {

    // The main method creates a new Player and starts the Login GUI
    public static void main(String[] args) {
        Player player = new Player(null);
        player.new LoginGUI();
    }
}
