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

            JButton jButton = new JButton("Enter the URL of the page to be parsed");
            jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            jButton.addActionListener(e -> enterUrl());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.gridx = 0;
            gbc.gridy = 0;

            JLabel numberLabel = new JLabel(Integer.toString(Parser.getNumberOfFoundLinks()));
            numberLabel.setFont(new Font("Arial", Font.PLAIN, 64));
            numberLabel.setForeground(TEXT_COLOR);

            gbc.insets = new Insets(0, 0, 0, 0);
            buttonPanel.add(numberLabel, gbc);

            gbc.gridy = 1;
            buttonPanel.add(jButton, gbc);

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

            // Create a javax.swing.Timer to update the numberLabel every 1000 milliseconds (1 second)
            int delay = 150;
            Timer timer = new Timer(delay, e -> {
                numberLabel.setText(Integer.toString(Parser.getNumberOfFoundLinks()));
            });
            timer.start();
        });
    }
    private static void enterUrl() {
        JTextField urlField = new JTextField(50);
        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        urlPanel.add(okButton);
        urlPanel.add(cancelButton);

        JDialog urlDialog = new JDialog((Frame) null, "Enter the URL of the page to be parsed", true);
        urlDialog.setContentPane(urlPanel);
        urlDialog.pack();

        // Create a variable to store the result of the dialog
        final int[] result = new int[1];

        okButton.addActionListener(e -> {
            result[0] = JOptionPane.OK_OPTION;
            urlDialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            result[0] = JOptionPane.CANCEL_OPTION;
            urlDialog.dispose();
        });

        urlDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                urlField.requestFocus();
            }
        });

        urlDialog.setLocationRelativeTo(null);
        urlDialog.setVisible(true);

        if (result[0] == JOptionPane.OK_OPTION) {
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
    }
}
