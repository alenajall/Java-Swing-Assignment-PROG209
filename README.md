# PROG209 Java Swing Assignment

`SwingAssignment.java` is a basic interactive Java Swing application. It uses
standard `JFrame`, `JPanel`, `JLabel`, `JButton`, `ImageIcon`, inheritance, a
constructor, and `ActionListener` event handling.

## Features

- The window is 800 x 800, titled **Java Swing Assignment**, and closes with
  `JFrame.EXIT_ON_CLOSE`.
- `SwingAssignment` extends `JFrame`. The constructor creates the frame, main
  panel, logo, labels, control panel and buttons.
- The application starts with the first welcome message. `Change Text` cycles
  through all five messages using a counter and modulo arithmetic, so it
  continues from the first message after the fifth.
- The `Change Text` button uses blue (`#2563EB`) and `Change Background` uses
  teal (`#0F766E`).
- `Change Background` cycles through these ten solid colors:
  black (`#000000`), dark navy (`#172B4D`), royal blue (`#2563EB`), red
  (`#DC2626`), green (`#15803D`), purple (`#7C3AED`), orange (`#EA580C`),
  teal (`#0F766E`), white (`#FFFFFF`) and dark gray (`#374151`).
- The welcome and subtitle text automatically use a readable dark or white
  color based on the panel's luminance.
- The white control panel uses ordinary Swing components. There is no custom
  painting, gradient, `Graphics2D` code, or custom component subclass.

## Logo

Place `icon.png` in the same folder as `SwingAssignment.java`. The image is
loaded with `ImageIcon`, used as the JFrame application icon, and displayed as
a proportionally scaled logo near the top of the window. If the file is missing
or unreadable, the program prints a message and displays `Logo unavailable`
instead of stopping.

The supplied logo is available as `icon.png`. The original `icon.png.png` file
is also preserved in the project folder.

## Compile and run in VS Code

Open the project folder in VS Code and run these commands in the integrated
terminal:

```powershell
javac SwingAssignment.java
java -cp . SwingAssignment
```

The Java extension's Run command above `main` can also be used.

The program does not open any additional windows when either button is clicked.
Both actions update the existing interface on Swing's event dispatch thread.

No GitHub push has been performed.
