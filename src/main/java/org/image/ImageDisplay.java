package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay {

    private static JButton changeImageButton;
    private static JLabel originalImageLabel;
    private static JLabel bwImage1Label;
    private static JLabel bwImage2Label;
    private static JLabel bwImage3Label;

    public static void displayImages(BufferedImage colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3) {
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
            originalImageLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
            originalImagePanel.add(originalImageLabel);
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
            bwImage1Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage1)));
            bwImage2Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage2)));
            bwImage3Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage3)));

            resultImagesPanel.add(bwImage1Label);
            resultImagesPanel.add(bwImage2Label);
            resultImagesPanel.add(bwImage3Label);
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

                originalImageLabel.setIcon(new ImageIcon(scaleImageForPreview(colorImage)));
                bwImage1Label.setIcon(new ImageIcon(scaleImageForPreview(bwImage1)));
                bwImage2Label.setIcon(new ImageIcon(scaleImageForPreview(bwImage2)));
                bwImage3Label.setIcon(new ImageIcon(scaleImageForPreview(bwImage3)));

                originalImageLabel.repaint();
                bwImage1Label.repaint();
                bwImage2Label.repaint();
                bwImage3Label.repaint();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static BufferedImage scaleImageForPreview(BufferedImage source) {
        final int maxSize = 256;
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
