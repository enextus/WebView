package org.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Import javax.swing.Timer at the beginning of the file
import javax.swing.Timer;

import static org.image.App.logURL;

public class Window {

    /**
     * The background color used for the components in the program's GUI.
     * Currently set to black.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /**
     * The text color used for the components in the program's GUI.
     * Currently set to gold (RGB: 255, 215, 0).
     */
    private static final Color TEXT_COLOR = new Color(255, 215, 0); // Gold

    /**
     * The label used to display the original image in the program's GUI.
     */
    private static JLabel originalImageLabel;

    /**
     * The text area used to display magnet links in the program's GUI.
     */
    static JTextArea magnetLinksTextArea;
    private static final JTextField urlField = new JTextField();

    private static void enterUrl() {
        String urlString = urlField.getText();
        logURL(urlString);

        try {
            URL url = new URL(urlString);
            Parser.parseUrl(url.toString());
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid URL. Please enter a valid URL.", "Issue!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void displayImages(BufferedImage colorImage) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Magnet Links Parser.");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(BACKGROUND_COLOR);

            originalImageLabel = new JLabel(new ImageIcon(Tools.scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            JButton jButton = new JButton("OK");
            jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            jButton.addActionListener(e -> enterUrl());

            JButton clearButton = new JButton("Clear");
            clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            clearButton.addActionListener(e -> {
                Parser.resetNumberOfFoundLinks();
                urlField.setText("");
                urlField.requestFocusInWindow();
            });

            JPanel urlPanel = new JPanel();
            urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));
            urlPanel.add(new JLabel("URL: "));
            urlField.setColumns(40);
            urlField.setPreferredSize(new Dimension(300, 30)); // установить размеры поля ввода 300 пикселей по ширине и 30 пикселей по высоте
            urlPanel.add(urlField);

            JLabel numberLabel = new JLabel(Integer.toString(Parser.getNumberOfFoundLinks()));
            numberLabel.setFont(new Font("Arial", Font.PLAIN, 64));
            numberLabel.setForeground(TEXT_COLOR);

            JPanel numberPanel = new JPanel();
            numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.X_AXIS));
            numberPanel.add(new JLabel("Number of links found: "));
            numberPanel.add(numberLabel);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);

            buttonPanel.add(numberPanel);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
            buttonPanel.add(urlPanel);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
            buttonPanel.add(jButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
            buttonPanel.add(clearButton);

            magnetLinksTextArea = new JTextArea(10, 50);
            magnetLinksTextArea.setEditable(false);

            magnetLinksTextArea.setForeground(TEXT_COLOR);
            magnetLinksTextArea.setBackground(BACKGROUND_COLOR);

            JScrollPane scrollPane = new JScrollPane(magnetLinksTextArea);

            JPanel textAreaPanel = new JPanel();
            textAreaPanel.setLayout(new BorderLayout());
            textAreaPanel.add(scrollPane, BorderLayout.NORTH);

            originalImageLabel.setLayout(new BorderLayout());
            originalImageLabel.add(buttonPanel, BorderLayout.CENTER);

            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(textAreaPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            int delay = 150;
            Timer timer = new Timer(delay, e -> {
                numberLabel.setText(Integer.toString(Parser.getNumberOfFoundLinks()));
            });
            timer.start();

            urlField.requestFocusInWindow();
        });
    }


}