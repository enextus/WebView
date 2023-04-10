package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay {

    private static JButton changeImageButton;

    public static void displayImages(BufferedImage colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
                String inputImagePath = ((File) selectedFile).getCanonicalPath();
                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                double[] weights1 = {0.35, 0.35, 0.35};
                double[] weights2 = {0.1, 0.79, 0.11};
                double[] weights3 = {0.79, 0.11, 0.1};

                BufferedImage bwImage1 = ImageProcessor.convertToBlackAndWhite(colorImage, weights1);
                BufferedImage bwImage2 = ImageProcessor.convertToBlackAndWhite(colorImage, weights2);
                BufferedImage bwImage3 = ImageProcessor.convertToBlackAndWhite(colorImage, weights3);

                displayImages(colorImage, bwImage1, bwImage2, bwImage3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static BufferedImage scaleImageForPreview(BufferedImage image) {
        int maxWidth = 500;
        int maxHeight = 500;
        int width = image.getWidth();
        int height = image.getHeight();

        if (width <= maxWidth && height <= maxHeight) {
            return image;
        }

        double scaleFactor = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = (int) (width * scaleFactor);
        int newHeight = (int) (height * scaleFactor);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }

}
