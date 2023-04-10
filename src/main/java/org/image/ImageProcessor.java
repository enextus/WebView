package org.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

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
//                String outputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";

                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                double[] weights1 = {0.35, 0.35, 0.35};
                double[] weights2 = {0.1, 0.79, 0.11};
                double[] weights3 = {0.79, 0.11, 0.1};

                BufferedImage bwImage1 = convertToBlackAndWhite(colorImage, weights1);
                BufferedImage bwImage2 = convertToBlackAndWhite(colorImage, weights2);
                BufferedImage bwImage3 = convertToBlackAndWhite(colorImage, weights3);

                ImageDisplay.displayImages(colorImage, bwImage1, bwImage2, bwImage3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static BufferedImage convertToBlackAndWhite(BufferedImage colorImage, double[] weights) {
        BufferedImage blackWhiteImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = colorImage.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int gray = (int) (weights[0] * red + weights[1] * green + weights[2] * blue);
                int binary = (gray > 128) ? 255 : 0;

                int newPixel = (binary << 16) | (binary << 8) | binary;

                blackWhiteImage.setRGB(x, y, newPixel);
            }
        }

        return blackWhiteImage;
    }


}
