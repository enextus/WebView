package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDisplay {

    // GUI components
    private static JButton changeImageButton;
    private static JLabel originalImageLabel;
    private static JLabel bwImage1Label;
    private static JLabel bwImage2Label;
    private static JLabel bwImage3Label;

    /**
     * Displays the original and black and white versions of an image with three different effects.
     *
     * @param colorImage the original color image
     * @param bwImage1   the black and white image with effect 1
     * @param bwImage2   the black and white image with effect 2
     * @param bwImage3   the black and white image with effect 3
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

            changeImageButton = new JButton("Choose another image");
            changeImageButton.addActionListener(e -> changeImage());
            panel.add(changeImageButton);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(Color.GRAY); // Change background color to gray

            originalImageLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            JPanel grayPanel = new JPanel();
            grayPanel.setPreferredSize(new Dimension(originalImageLabel.getWidth(), 10));
            grayPanel.setBackground(Color.GRAY); // Change the color of the horizontal stripe to red
            centerPanel.add(grayPanel, BorderLayout.SOUTH);

            frame.add(centerPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3));
            JButton button1 = new JButton("Сохранить с эффектом 1");
            button1.addActionListener(e -> ImageSaver.saveImage(bwImage1));

            JButton button2 = new JButton("Сохранить с эффектом 2");
            button2.addActionListener(e -> ImageSaver.saveImage(bwImage2));

            JButton button3 = new JButton("Сохранить с эффектом 3");
            button3.addActionListener(e -> ImageSaver.saveImage(bwImage3));

            Dimension buttonSize = new Dimension(200, 30);
            button1.setPreferredSize(buttonSize);
            button2.setPreferredSize(buttonSize);
            button3.setPreferredSize(buttonSize);

            buttonPanel.add(button1);
            buttonPanel.add(button2);
            buttonPanel.add(button3);

            JPanel resultImagesPanel = new JPanel();
            resultImagesPanel.setLayout(new GridLayout(1, 3));
            bwImage1Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage1)));
            bwImage2Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage2)));
            bwImage3Label = new JLabel(new ImageIcon(scaleImageForPreview(bwImage3)));

            resultImagesPanel.add(bwImage1Label);
            resultImagesPanel.add(bwImage2Label);
            resultImagesPanel.add(bwImage3Label);

            // Creating a new panel to place buttonPanel and resultImagesPanel panels vertically
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
            southPanel.add(buttonPanel);
            southPanel.add(resultImagesPanel);
            frame.add(southPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Changes the displayed image to a new one selected by the user.
     */
    private static void changeImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String inputImagePath = ((File) selectedFile).getCanonicalPath();
                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                // Set filename
                ImageSaver.setOriginalFileName(selectedFile.getName());

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
