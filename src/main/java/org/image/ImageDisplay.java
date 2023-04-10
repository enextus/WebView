package org.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplay {

    public static void displayImages(BufferedImage colorImage, BufferedImage bwImage1, BufferedImage bwImage2, BufferedImage bwImage3) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Image Processor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel originalImagePanel = new JPanel();
            originalImagePanel.add(new JLabel(new ImageIcon(scaleImageForPreview(colorImage))));
            frame.add(originalImagePanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3));
            JButton button1 = new JButton("Преобразование 1");
            button1.addActionListener(e -> SaveImages.saveImage(bwImage1));

            JButton button2 = new JButton("Преобразование 2");
            button2.addActionListener(e -> SaveImages.saveImage(bwImage2));

            JButton button3 = new JButton("Преобразование 3");
            button3.addActionListener(e -> SaveImages.saveImage(bwImage3));

            Dimension buttonSize = new Dimension(200, 30);
            button1.setPreferredSize(buttonSize);
            button1.setMaximumSize(buttonSize);
            button1.setMinimumSize(buttonSize);
            button2.setPreferredSize(buttonSize);
            button2.setMaximumSize(buttonSize);
            button2.setMinimumSize(buttonSize);
            button3.setPreferredSize(buttonSize);
            button3.setMaximumSize(buttonSize);
            button3.setMinimumSize(buttonSize);

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
