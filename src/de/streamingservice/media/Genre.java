package de.streamingservice.media;

public enum Genre {

    //TODO Team 3 - Felix Bosl (Matrikelnr.: 2197603) – Dominik Mátyás (Matrikelnr.: 2427625) - letzte Änderung 18.01.2024 (todo for visibility)

    // the genres
    POP, ROCK, METAL, K_POP;

    //this Method returns the Visual representation of the genres in String form
    @Override
    public String toString(){
        return switch (this) {
            case POP -> "Pop";
            case ROCK -> "Rock";
            case METAL -> "Metal";
            case K_POP -> "K-Pop";
            default -> "no Genre available and you are dumb";
        };

    }

    // is the reverse method to toString, it turns the String representation into genres
    public Genre fromString(String genre) {
        return switch (genre) {
            case "Pop" -> POP;
            case "Rock" -> ROCK;
            case "Metal" -> METAL;
            case "K-Pop" -> K_POP;
            default -> null;
        };
    }

}
