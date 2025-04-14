package de.streamingservice;

public class User {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024

    // Fields for User
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    public boolean isLoggedIn;

    // creates a new user with a username, password, first Name and last name
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLoggedIn = false; // false as standard
    }

    // logs in the user with the passed password
    public boolean login(String password) {
        if (this.password.equals(password)) { // checks if the password is correct
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    // logs the user out
    public void logout() {
        isLoggedIn = false;
    }


    // changes the username
    public void changeUsername(String newUsername) {
        this.username = newUsername;
        System.out.println("Your new username is: " + newUsername);
    }

    // changes the password
    public void changePassword(String currentPassword, String newPassword) {
        if (this.password.equals(currentPassword)) { // checks first is the user knows the current password
            this.password = newPassword;
            System.out.println("Your new password is: " + newPassword);
        } else {
            System.out.println("Current password is incorrect.");
        }
    }

    // getter Methods
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password; // Vorsicht: Sicherheitsproblem!
    }
    public String getName() {
        return firstName + " " + lastName;
    }
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
}