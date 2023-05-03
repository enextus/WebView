package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import static org.image.App.logURL;

/**
 * The Window class provides functionality to display the original image and its three
 * black and white versions with different effects. It also provides the option to change the
 * input image and save the black and white images with the applied effects.
 * The main components of the class include:
 * 1. A method to display the original and processed images in a JFrame.
 * 2. A method to change the displayed image based on user input.
 * 3. A method to scale the images for preview purposes.
 */
public class Window {

    private static JLabel originalImageLabel;

    private static JButton enterUrlButton;

    /**
     * Displays the original and black and white versions of an image with three different effects.
     *
     * @param colorImage the original color image
     */
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

            frame.setResizable(false); // Prevent window resizing

            // add NORTH panel
            JPanel panel = new JPanel();
            frame.add(panel, BorderLayout.NORTH);

            // Add the new URL-button
            enterUrlButton = new JButton("Enter the URL of the page to be parsed");
            enterUrlButton.addActionListener(e -> enterUrl());
            panel.add(enterUrlButton);

            // add center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(Color.GRAY); // Change background color to gray

            originalImageLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            frame.add(centerPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * This method creates and displays a dialog to input a URL.
     * Once a URL is entered and the OK button is clicked, it validates the URL and calls the parseUrl method
     * from the MagnetLinkParser class to parse the provided URL.
     * If the URL is invalid, it shows an error message dialog.
     */
    private static void enterUrl() {

        JTextField urlField = new JTextField(99);
        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);

        int result = JOptionPane.showConfirmDialog(null, urlPanel, "Enter the URL of the page to be parsed", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String urlString = urlField.getText();
            logURL(urlString); // Log the entered URL or any other characters

            try {
                URL url = new URL(urlString);
                // Call the parseUrl method with the entered URL
                MagnetLinkParser.parseUrl(url.toString());
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Invalid URL. Please enter a valid URL.", "Issue!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Scales a given image to fit within a maximum size of 512x512 pixels for preview purposes.
     *
     * @param source the image to be scaled
     * @return the scaled image
     */
    public static BufferedImage scaleImageForPreview(BufferedImage source) {

        final int maxSize = 512;
        double scaleFactor = Math.min((double) maxSize / source.getWidth(), (double) maxSize / source.getHeight());

        int newWidth = (int) (source.getWidth() * scaleFactor);
        int newHeight = (int) (source.getHeight() * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, source.getType());

        Graphics2D g = scaledImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return scaledImage;
    }

    /**
     * Decodes a Base64 encoded image string and returns a BufferedImage object.
     *
     * @param base64ImageString The Base64 encoded image string.
     * @return A BufferedImage object representing the decoded image, or null if an error occurs during decoding.
     */
    static BufferedImage decodeBase64ToImage(String base64ImageString) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
