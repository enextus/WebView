package org.image;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageProcessor {
    public static void main(String[] args) {
        String inputImagePath = "src/main/resources/img/image.jpg";
        String outputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";

        try {
            BufferedImage colorImage = ImageIO.read(new File(inputImagePath));
            BufferedImage blackWhiteImage = convertToBlackAndWhite(colorImage);
            ImageIO.write(blackWhiteImage, "jpg", new File(outputImagePath));

            displayImages(colorImage, blackWhiteImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage convertToBlackAndWhite(BufferedImage colorImage) {
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

    private static void displayImages(BufferedImage colorImage, BufferedImage blackWhiteImage) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Processor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 2));

            JPanel originalImagePanel = new JPanel();
            originalImagePanel.add(new JLabel(new ImageIcon(colorImage)));
            frame.add(originalImagePanel);

            JPanel processedImagePanel = new JPanel();
            processedImagePanel.add(new JLabel(new ImageIcon(blackWhiteImage)));
            frame.add(processedImagePanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
