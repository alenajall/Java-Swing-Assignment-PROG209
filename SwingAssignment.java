import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

// Inheritance: SwingAssignment is a JFrame with its own constructor and controls.
public class SwingAssignment extends JFrame {
    private final JFrame frame;
    private final JPanel panel;
    private final JLabel label;
    private final JLabel subtitleLabel;

    // Ten solid colors used by the Change Background button.
    private final Color[] backgroundColors = {
        new Color(0x000000), // Black
        new Color(0x172B4D), // Dark navy
        new Color(0x2563EB), // Royal blue
        new Color(0xDC2626), // Red
        new Color(0x15803D), // Green
        new Color(0x7C3AED), // Purple
        new Color(0xEA580C), // Orange
        new Color(0x0F766E), // Teal
        new Color(0xFFFFFF), // White
        new Color(0x374151)  // Dark gray
    };

    // Messages used by the Change Text button.
    private final String[] textMessages = {
        "Welcome to Java Swing – Individual Assignment by Alhaji Abubakarr Jalloh",
        "Hello! Welcome to My Interactive Java Application!",
        "Java Swing Makes Desktop Applications Interactive!",
        "Learning Event Driven Programming with Java!",
        "Thank You for Exploring My Java Swing Application!"
    };

    private int colorIndex = 3;
    private int textIndex = 0;

    // Constructor: creates the window, components and event handlers.
    public SwingAssignment() {
        frame = this;
        frame.setTitle("Java Swing Assignment");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColors[2]);
        panel.setBorder(BorderFactory.createEmptyBorder(35, 30, 35, 30));
        frame.setContentPane(panel);

        // Load icon.png for the frame and display a scaled copy above the text.
        ImageIcon appIcon = loadIcon();
        if (appIcon != null) {
            frame.setIconImage(appIcon.getImage());
        }
        JLabel logoLabel = createLogoLabel(appIcon);
        addComponent(logoLabel, 0, 0);

        label = new JLabel(formatMessage(textMessages[textIndex]), SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        label.setPreferredSize(new Dimension(620, 112));
        addComponent(label, 1, 18);

        subtitleLabel = new JLabel(
            "Individual Assignment by Alhaji Abubakarr Jalloh",
            SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        addComponent(subtitleLabel, 2, 4);

        // A normal white JPanel acts as the control area; no custom painting is used.
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 214, 220)),
            BorderFactory.createEmptyBorder(24, 34, 24, 34)));

        JLabel controlsTitle = new JLabel("Application Controls", SwingConstants.CENTER);
        controlsTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
        controlsTitle.setForeground(new Color(30, 58, 95));
        addControl(controlPanel, controlsTitle, 0, 0);

        JButton changeTextButton = new JButton("Change Text");
        styleButton(changeTextButton, new Color(0x2563EB));
        changeTextButton.addActionListener(e -> {
            textIndex = (textIndex + 1) % textMessages.length;
            label.setText(formatMessage(textMessages[textIndex]));
        });
        addControl(controlPanel, changeTextButton, 0, 1);

        JButton changeBackgroundButton = new JButton("Change Background");
        styleButton(changeBackgroundButton, new Color(0x0F766E));
        changeBackgroundButton.addActionListener(e -> {
            colorIndex = (colorIndex + 1) % backgroundColors.length;
            panel.setBackground(backgroundColors[colorIndex]);
            updateTextContrast();
        });
        addControl(controlPanel, changeBackgroundButton, 0, 2);

        addComponent(controlPanel, 3, 30);
        updateTextContrast();
    }

    // Adds a component to the centred main panel.
    private void addComponent(java.awt.Component component, int row, int topGap) {
        GridBagConstraints position = new GridBagConstraints();
        position.gridx = 0;
        position.gridy = row;
        position.insets = new Insets(topGap, 0, 0, 0);
        panel.add(component, position);
    }

    // Adds a component to the white control panel.
    private static void addControl(JPanel controlPanel, java.awt.Component component,
                                   int column, int row) {
        GridBagConstraints position = new GridBagConstraints();
        position.gridx = column;
        position.gridy = row;
        position.fill = GridBagConstraints.HORIZONTAL;
        position.weightx = 1.0;
        position.insets = new Insets(row == 0 ? 0 : 10, 0, 0, 0);
        controlPanel.add(component, position);
    }

    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(330, 48));
    }

    // Word wrapping keeps the longer messages centred inside the label area.
    private static String formatMessage(String message) {
        StringBuilder html = new StringBuilder("<html><center>");
        int lineLength = 0;
        for (String word : message.split(" ")) {
            int wordLength = word.length();
            if (lineLength > 0 && lineLength + wordLength + 1 > 34) {
                html.append("<br>");
                lineLength = 0;
            } else if (lineLength > 0) {
                html.append(' ');
                lineLength++;
            }
            html.append(word);
            lineLength += wordLength;
        }
        return html.append("</center></html>").toString();
    }

    // Choose a readable black or white text color from the panel luminance.
    private void updateTextContrast() {
        Color background = panel.getBackground();
        double red = linearize(background.getRed() / 255.0);
        double green = linearize(background.getGreen() / 255.0);
        double blue = linearize(background.getBlue() / 255.0);
        double luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue;
        double whiteContrast = 1.05 / (luminance + 0.05);
        double blackContrast = (luminance + 0.05) / 0.05;
        Color textColor = whiteContrast >= blackContrast
            ? Color.WHITE : new Color(30, 58, 95);
        label.setForeground(textColor);
        subtitleLabel.setForeground(textColor);
    }

    private static double linearize(double channel) {
        return channel <= 0.03928
            ? channel / 12.92
            : Math.pow((channel + 0.055) / 1.055, 2.4);
    }

    // Look for icon.png on the classpath, then in the program folder.
    private static ImageIcon loadIcon() {
        URL resource = SwingAssignment.class.getResource("/icon.png");
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                return icon;
            }
        }
        File file = new File("icon.png");
        if (file.isFile()) {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                return icon;
            }
        }
        System.err.println("Could not load icon.png; continuing without an image.");
        return null;
    }

    // Scale the logo with equal width and height ratios so it is not distorted.
    private static JLabel createLogoLabel(ImageIcon source) {
        if (source == null) {
            JLabel missing = new JLabel("Logo unavailable", SwingConstants.CENTER);
            missing.setPreferredSize(new Dimension(100, 100));
            return missing;
        }
        int width = source.getIconWidth();
        int height = source.getIconHeight();
        double scale = Math.min(100.0 / width, 100.0 / height);
        java.awt.Image scaled = source.getImage().getScaledInstance(
            (int) Math.round(width * scale),
            (int) Math.round(height * scale),
            java.awt.Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(100, 100));
        return logo;
    }

    public static void main(String[] args) {
        // Construct and display Swing components on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new SwingAssignment().setVisible(true));
    }
}
