package org.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ImageDisplay {

    public static void displayImages(JFrame frame, BufferedImage colorImage, BufferedImage... bwImages) {
        if (colorImage == null || bwImages == null || bwImages.length == 0) {
            throw new IllegalArgumentException("Input images cannot be null.");
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, bwImages.length + 1));

        JLabel colorLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
        panel.add(colorLabel);

        for (BufferedImage bwImage : bwImages) {
            JLabel bwLabel = new JLabel(new ImageIcon(scaleImageForPreview(bwImage)));
            panel.add(bwLabel);
        }

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage scaleImageForPreview(BufferedImage image) {
        int width = 300;
        int height = (int) (((double) image.getHeight()) / image.getWidth() * width);

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage bufferedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
}
