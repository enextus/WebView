package org.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageProcessor {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String inputImagePath = selectedFile.getCanonicalPath();
                String outputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";

                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));
                BufferedImage bwImage1 = convertToBlackAndWhite1(colorImage);
                BufferedImage bwImage2 = convertToBlackAndWhite2(colorImage);
                BufferedImage bwImage3 = convertToBlackAndWhite3(colorImage);
                ImageIO.write(bwImage1, "jpg", new File(outputImagePath));

                displayImages(colorImage, bwImage1, bwImage2, bwImage3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static BufferedImage convertToBlackAndWhite1(BufferedImage colorImage) {
        BufferedImage blackWhiteImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = colorImage.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int gray = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                int binary = (gray > 128) ? 255 : 0;

                int newPixel = (binary << 16) | (binary << 8) | binary;

                blackWhiteImage.setRGB(x, y, newPixel);
            }
        }

        return blackWhiteImage;
    }

    public static BufferedImage convertToBlackAndWhite2(BufferedImage colorImage) {
        BufferedImage blackWhiteImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = colorImage.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int gray = (int) (0.1 * red + 0.79 * green + 0.11 * blue);
                int binary = (gray > 128) ? 255 : 0;

                int newPixel = (binary << 16) | (binary << 8) | binary;

                blackWhiteImage.setRGB(x, y, newPixel);
            }
        }

        return blackWhiteImage;
    }
    public static BufferedImage convertToBlackAndWhite3(BufferedImage colorImage) {
        BufferedImage blackWhiteImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = colorImage.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int gray = (int) (0.1 * red + 0.79 * green + 0.2 * blue);
                int binary = (gray > 128) ? 255 : 0;

                int newPixel = (binary << 16) | (binary << 8) | binary;

                blackWhiteImage.setRGB(x, y, newPixel);
            }
        }

        return blackWhiteImage;
    }

    private static void displayImages(BufferedImage colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Processor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel originalImagePanel = new JPanel();
            originalImagePanel.add(new JLabel(new ImageIcon(colorImage)));
            frame.add(originalImagePanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3));
            buttonPanel.add(new JButton("Преобразование 1"));
            buttonPanel.add(new JButton("Преобразование 2"));
            buttonPanel.add(new JButton("Преобразование 3"));
            frame.add(buttonPanel, BorderLayout.CENTER);

            JPanel resultImagesPanel = new JPanel();
            resultImagesPanel.setLayout(new GridLayout(1, 3));
            resultImagesPanel.add(new JLabel(new ImageIcon(bwImage1)));
            resultImagesPanel.add(new JLabel(new ImageIcon(bwImage2)));
            resultImagesPanel.add(new JLabel(new ImageIcon(bwImage3)));
            frame.add(resultImagesPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
