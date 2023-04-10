package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay {

    private static JButton changeImageButton;

    /**
     * Displays the given images in a JFrame, allowing the user to choose different image effects.
     *
     * @param colorImage The original color image.
     * @param bwImage1   The first black and white image effect.
     * @param bwImage2   The second black and white image effect.
     * @param bwImage3   The third black and white image effect.
     * @param image3
     */
    public static void displayImages(JFrame colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3, BufferedImage image3) {
        if (colorImage == null || bwImage1 == null || bwImage2 == null || bwImage3 == null) {
            throw new IllegalArgumentException("Input images cannot be null.");
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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

            changeImageButton = new JButton("Выбрать другое изображение");
            changeImageButton.addActionListener(e -> changeImage());
            panel.add(changeImageButton);

            JPanel originalImagePanel = new JPanel();
            originalImagePanel.add(new JLabel(new ImageIcon(scaleImageForPreview(colorImage))));
            frame.add(originalImagePanel, BorderLayout.WEST);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3));
            JButton button1 = new JButton("Save Image with effect 1");
            button1.addActionListener(e -> ImageSaver.saveImage(bwImage1));

            JButton button2 = new JButton("Save Image with effect 2");
            button2.addActionListener(e -> ImageSaver.saveImage(bwImage2));

            JButton button3 = new JButton("Save Image with effect 3");
            button3.addActionListener(e -> ImageSaver.saveImage(bwImage3));

            Dimension buttonSize = new Dimension(200, 30);
            button1.setPreferredSize(buttonSize);
            button2.setPreferredSize(buttonSize);
            button3.setPreferredSize(buttonSize);

            buttonPanel.add(button1);
            buttonPanel.add(button2);
            buttonPanel.add(button3);
            frame.add(buttonPanel, BorderLayout.CENTER);

            JPanel resultImagesPanel = new JPanel();
            resultImagesPanel.setLayout(new GridLayout(1, 3));
            resultImagesPanel.add(new JLabel(new ImageIcon(scaleImageForPreview(bwImage1))));
            resultImagesPanel.add(new JLabel(new ImageIcon(scaleImageForPreview(bwImage2))));
            resultImagesPanel.add(new JLabel(new ImageIcon(scaleImageForPreview(bwImage3))));
            frame.add(resultImagesPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void changeImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String inputImagePath = selectedFile.getCanonicalPath();

                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                double[] weights1 = {0.35, 0.35, 0.35};
                double[] weights2 = {0.1, 0.79, 0.11};
                double[] weights3 = {0.79, 0.11, 0.1};

                BufferedImage bwImage1 = ImageProcessor.convertToBlackAndWhite(colorImage, weights1);
                BufferedImage bwImage2 = ImageProcessor.convertToBlackAndWhite(colorImage, weights2);
                BufferedImage bwImage3 = ImageProcessor.convertToBlackAndWhite(colorImage, weights3);

                SwingUtilities.invokeLater(() -> displayImages(colorImage, bwImage1, bwImage2, bwImage3, bwImage3));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static BufferedImage scaleImageForPreview(BufferedImage image) {
        int maxWidth = 200;
        int maxHeight = 200;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        if (newWidth > maxWidth || newHeight > maxHeight) {
            double scaleFactor = Math.min((double) maxWidth / newWidth, (double) maxHeight / newHeight);
            newWidth = (int) (newWidth * scaleFactor);
            newHeight = (int) (newHeight * scaleFactor);
        }

        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

}