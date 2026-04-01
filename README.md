# Blink Timer App

A desktop application built with Java Swing that allows users to start a blinking window either at a specific time or after a countdown.

This project demonstrates GUI development, event-driven programming, and timer-based functionality in Java.

---

## Features

*  Start blinking at a specific time (`HH:mm:ss`)
*  Start blinking after a countdown (in seconds)
*  Choose the blinking window color using a color picker
*  Select blinking speed (interval in milliseconds)
*  Live clock displayed in the main window
*  Start and stop timer functionality
*  Stop and restart the clock
*  Separate blinking window with dynamic color switching

---

## Technologies Used

* Java
* Swing (GUI)
* AWT (layouts and colors)

---

## Project Structure

```
app/
 ├── Main.java
 ├── MainFrame.java
 └── BlinkWindow.java
```

---

## How to Run

1. Make sure you have Java installed (JDK 8 or higher)

2. Compile the project:

```bash
javac app/*.java
```

3. Run the application:

```bash
java app.Main
```

---

## How It Works

* The user selects either:

  * a specific time to trigger the event, or
  * a countdown in seconds
* A delay is calculated based on the input
* After the delay expires, a new window opens
* The window alternates between the selected color and white using a Swing `Timer`

---

## Possible Improvements

* Add input validation messages for each field
* Improve UI styling (fonts, spacing, alignment)
* Allow custom blink intervals (not only predefined ones)
* Add sound notification when blinking starts
* Save user preferences

---

## Notes

This project was created for learning purposes and focuses on:

* Java Swing GUI design
* Timer-based events
* User interaction and input handling

---

Made for practice and learning 🚀
