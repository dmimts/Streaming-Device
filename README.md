# 🎵 Streaming Device

> Ein Java-Swing-basierter Audio-Streaming-Simulator, der die Funktionalität eines einfachen Streaming-Geräts nachbildet.

**Team 3** – Felix Bosl & Dominik Mátyás  
_Letzte Änderung: 18.01.2024_

---

## 📋 Inhaltsverzeichnis

- [Überblick](#-überblick)
- [Features](#-features)
- [Projektstruktur](#-projektstruktur)
- [Klassenübersicht](#-klassenübersicht)
- [Datenquellen](#-datenquellen)
- [Installation & Ausführung](#-installation--ausführung)
- [Bedienung](#-bedienung)
- [Architektur](#-architektur)

---

## 🎯 Überblick

Das Projekt simuliert ein einfaches Gerät zum Streamen von Audiodateien. Es greift auf Werte zurück, die aus Textdateien im CSV-Format gelesen werden, und stellt diese über eine grafische Benutzeroberfläche (Java Swing) dar. Der Benutzer kann sich einloggen, Songs und Podcasts abspielen, Playlists erstellen und seine Hörstatistiken einsehen.

---

## ✨ Features

| Feature                 | Beschreibung                                                       |
| ----------------------- | ------------------------------------------------------------------ |
| **Login-System**        | Benutzeranmeldung mit Benutzername und Passwort                    |
| **Medienwiedergabe**    | Abspielen von Songs und Podcasts mit Echtzeit-Timer                |
| **Playlist-Erstellung** | Erstellen von Playlists nach Genre oder Medientyp (Songs/Podcasts) |
| **Shuffle**             | Zufällige Wiedergabereihenfolge innerhalb einer Playlist           |
| **Mediensuche**         | Suche nach Medien per ID oder Titel                                |
| **Lautstärkeregelung**  | Lautstärke erhöhen/verringern in 5er-Schritten (0–100)             |
| **Vor-/Zurückspulen**   | 10 Sekunden vor- oder zurückspringen                               |
| **Statistiken**         | Anzeige von Hörzeit, Anzahl gehörter Songs und Podcasts            |
| **Genre-Filter**        | Playlists nach Pop, Rock, Metal oder K-Pop filtern                 |

---

## 📁 Projektstruktur

```
Streaming-Device/
├── README.md
├── Streaming Device - Anleitung.pdf
└── src/
    ├── de/
    │   └── streamingservice/
    │       ├── Main.java                  # Einstiegspunkt der Anwendung
    │       ├── Player.java                # Player-Logik & GUI (Login + Player)
    │       ├── User.java                  # Benutzermodell (Login/Logout)
    │       ├── media/
    │       │   ├── Genre.java             # Enum: Pop, Rock, Metal, K-Pop
    │       │   ├── Media.java             # Abstrakte Basisklasse für Medien
    │       │   ├── MediaLibrary.java      # Medienverwaltung & CSV-Import
    │       │   ├── Playlist.java          # Playlist-Verwaltung
    │       │   ├── Podcast.java           # Podcast-Modell (Episode, Episodentitel)
    │       │   └── Song.java              # Song-Modell (Künstler, Genre)
    │       └── statistics/
    │           └── Statistics.java        # Hörstatistiken
    └── resources/
        ├── songs_with_ids.csv             # 83 Songs (Pop, Rock, Metal, K-Pop)
        └── podcasts_with_ids.csv          # 59 Podcast-Episoden
```

---

## 🏗 Klassenübersicht

### `Main`

Einstiegspunkt der Anwendung. Erstellt einen `Player` und startet die Login-GUI.

### `Player`

Zentrale Klasse des Projekts (~520 Zeilen). Enthält:

- **Wiedergabelogik**: `play()`, `pause()`, `stop()`, `next()`, `previous()`, `skip()`, `reSkip()`
- **Lautstärke**: `volumeUp()`, `volumeDown()` (Bereich 0–100)
- **Player-GUI** (`PlayerGUI()`): Swing-basiertes Interface mit Buttons für alle Steuerelemente
- **Login-GUI** (innere Klasse `LoginGUI`): Anmeldefenster mit Benutzername/Passwort
- **Echtzeit-Timer**: Automatische Aktualisierung der Wiedergabezeit jede Sekunde

### `User`

Benutzermodell mit:

- Login/Logout-Funktionalität
- Benutzername und Passwort ändern
- Felder: `username`, `password`, `firstName`, `lastName`, `isLoggedIn`

### `Media` _(abstrakt)_

Basisklasse für alle Medientypen mit `mediaId`, `title` und `length` (in Sekunden). Implementiert `equals()` und `hashCode()` basierend auf der Media-ID.

### `Song` _(extends Media)_

Erweiterung um `artist` (Künstler) und `genre` (Genre-Enum). Validiert, dass Künstler und Genre nicht leer/null sind.

### `Podcast` _(extends Media)_

Erweiterung um `episode` (Episodennummer) und `episodeTitle` (Episodentitel).

### `Playlist`

Verwaltung von Medien-Sammlungen mit automatischer ID-Vergabe. Unterstützt Hinzufügen und Entfernen von Medien (Duplikate werden verhindert).

### `MediaLibrary`

Zentrale Medienverwaltung:

- **CSV-Import**: Liest Songs und Podcasts aus CSV-Dateien mit eigenem CSV-Parser (unterstützt Anführungszeichen)
- **Playlist-Erstellung**: Filtert nach Medientyp und optional nach Genre
- **Shuffle**: Mischt die Reihenfolge einer Playlist
- **Suche**: Nach ID (exakt) oder Titel (Teilstring, case-insensitive)

### `Genre` _(Enum)_

Definiert die verfügbaren Genres: `POP`, `ROCK`, `METAL`, `K_POP`. Bietet Konvertierung zwischen Enum und String-Darstellung.

### `Statistics`

Trackt Hörstatistiken des Benutzers:

- Gesamte Hörzeit (in HH:MM:SS formatiert)
- Anzahl abgespielter Songs
- Anzahl abgespielter Podcasts

---

## 📊 Datenquellen

### Songs (`songs_with_ids.csv`)

83 Songs in 4 Genres mit folgendem CSV-Format:

```csv
ID, Künstler, Titel, Länge (Sekunden), Genre
```

| Genre | Anzahl | Beispiele                                   |
| ----- | ------ | ------------------------------------------- |
| Pop   | 25     | Lady Gaga, Billie Eilish, Sabrina Carpenter |
| Rock  | 23     | AC/DC, Guns N' Roses, Led Zeppelin          |
| K-Pop | 19     | ROSÉ, aespa, ITZY, NMIXX                    |
| Metal | 16     | Metallica, Slayer, Rammstein                |

### Podcasts (`podcasts_with_ids.csv`)

59 Episoden aus 3 Podcasts mit folgendem CSV-Format:

```csv
ID, Podcast-Name, Episodennummer, Episodentitel, Länge (Sekunden)
```

| Podcast         | Episoden    |
| --------------- | ----------- |
| Hobbylos        | 20 Episoden |
| Crime Junkies   | 20 Episoden |
| Gemischtes Hack | 19 Episoden |

---

## 🚀 Installation & Ausführung

### Voraussetzungen

- **Java JDK 17** oder höher (für Switch-Expressions und moderne Sprachfeatures)

### Kompilieren und Starten

```bash
# Ins Projektverzeichnis wechseln
cd Streaming-Device

# Kompilieren
javac -d out src/de/streamingservice/*.java src/de/streamingservice/media/*.java src/de/streamingservice/statistics/*.java

# Ausführen (aus dem Projektverzeichnis, damit die CSV-Dateien gefunden werden)
java -cp out de.streamingservice.Main
```

> **Hinweis:** Die Anwendung muss aus dem Projektverzeichnis `Streaming-Device/` gestartet werden, da die CSV-Dateien über relative Pfade (`src/resources/...`) geladen werden.

---

## 🎮 Bedienung

### 1. Login

Beim Start erscheint das Login-Fenster. Verwende folgende Testdaten:

- **Benutzername:** `maxi1`
- **Passwort:** `password`

### 2. Player-GUI

Nach dem Login öffnet sich der Player mit folgenden Bedienelementen:

| Button            | Funktion                    |
| ----------------- | --------------------------- |
| ▶                 | Wiedergabe starten          |
| ⏸                 | Wiedergabe pausieren        |
| ⏹                 | Wiedergabe komplett stoppen |
| ⏮                | Vorheriges Medium           |
| ⏭                | Nächstes Medium             |
| ↻                 | 10 Sekunden vorspulen       |
| ↺                 | 10 Sekunden zurückspulen    |
| +                 | Lautstärke erhöhen (+5)     |
| −                 | Lautstärke verringern (−5)  |
| ⇆                 | Playlist mischen (Shuffle)  |
| 🔍                | Mediensuche (ID oder Titel) |
| + Create Playlist | Neue Playlist erstellen     |
| Statistics        | Hörstatistiken anzeigen     |

### 3. Playlist erstellen

1. Klick auf **"+ Create Playlist"**
2. Namen für die Playlist eingeben
3. Wählen, ob nur Podcasts enthalten sein sollen
4. Bei Songs: Genre auswählen (Pop, Rock, Metal, K-Pop)
5. Die Playlist wird automatisch als aktuelle Playlist gesetzt

### 4. Mediensuche

1. Klick auf **🔍**
2. Eine **Zahl** eingeben → Suche nach Media-ID
3. Einen **Text** eingeben → Suche nach Titel (Teilstring)

---

## 🏛 Architektur

```
┌─────────────┐
│    Main      │  Einstiegspunkt
└──────┬──────┘
       │ erstellt
       ▼
┌─────────────┐     ┌───────────┐
│   Player     │────▶│   User    │  Authentifizierung
├─────────────┤     └───────────┘
│  LoginGUI   │  (Innere Klasse)
│  PlayerGUI  │
├─────────────┤     ┌───────────────┐
│  Wiedergabe  │────▶│ MediaLibrary   │  CSV-Import & Verwaltung
│  Steuerung   │     ├───────────────┤
│  Statistiken │     │ Media (abstr.) │
└─────────────┘     │  ├── Song      │
                    │  └── Podcast   │
                    │ Playlist       │
                    │ Genre (Enum)   │
                    └───────────────┘
                           │
                    ┌──────┴──────┐
                    │ Statistics   │
                    └─────────────┘
```

**Design-Entscheidungen:**

- **Innere Klasse `LoginGUI`**: Direkt im `Player` eingebettet für einfachen Zugriff auf den Player-Zustand
- **Abstrakte Klasse `Media`**: Polymorphe Behandlung von Songs und Podcasts
- **CSV-Parser**: Eigene Implementierung zur Unterstützung von Feldern mit Anführungszeichen und Kommas
- **Automatische ID-Vergabe**: Playlists erhalten automatisch inkrementelle IDs

---

## 📄 Weitere Dokumentation

Eine detaillierte Anleitung zur Nutzung befindet sich in der Datei [`Streaming Device - Anleitung.pdf`](Streaming%20Device%20-%20Anleitung.pdf).
