/**
 * The ImageDisplay class provides functionality to display the original image and its three
 * black and white versions with different effects. It also provides the option to change the
 * input image and save the black and white images with the applied effects.
 * <p>
 * The main components of the class include:
 * 1. A method to display the original and processed images in a JFrame.
 * 2. A method to change the displayed image based on user input.
 * 3. A method to scale the images for preview purposes.
 */
package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDisplay {

    // GUI components

    private static JLabel originalImageLabel;

    // Add a new JButton instance variable
    private static JButton enterUrlButton;

    /**
     * Displays the original and black and white versions of an image with three different effects.
     *
     * @param colorImage the original color image
     */
    public static void displayImages(BufferedImage colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Image Processor | Color to B/W");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false); // Prevent window resizing

            JPanel panel = new JPanel();
            frame.add(panel, BorderLayout.NORTH);

            // Add the new button
            enterUrlButton = new JButton("Enter the URL of the page to be parsed");
            enterUrlButton.addActionListener(e -> enterUrl());
            panel.add(enterUrlButton);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(Color.GRAY); // Change background color to gray

            originalImageLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            JPanel grayPanel = new JPanel();
            grayPanel.setPreferredSize(new Dimension(originalImageLabel.getWidth(), 1));
            grayPanel.setBackground(Color.GRAY); // Change the color of the horizontal stripe to some color
            centerPanel.add(grayPanel, BorderLayout.SOUTH);

            frame.add(centerPanel, BorderLayout.CENTER);

            // Creating a new panel to place buttonPanel and resultImagesPanel panels vertically
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));


            frame.add(southPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Add a new method to handle the new button's action event
    private static void enterUrl() {
        JTextField urlField = new JTextField(99);
        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);

        int result = JOptionPane.showConfirmDialog(null, urlPanel, "Enter the URL of the page to be parsed", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                URL url = new URL(urlField.getText());
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

}
