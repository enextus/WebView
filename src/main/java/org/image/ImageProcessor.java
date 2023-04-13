package org.image;

import java.awt.image.BufferedImage;

public class ImageProcessor {

    /**
     * Converts the given color image to a black and white image using the specified weights.
     *
     * @param colorImage A BufferedImage representing the input color image.
     * @param weights    An array of 3 doubles representing the weights for the red, green, and blue channels.
     * @return A BufferedImage representing the black and white image.
     */
    public static BufferedImage convertToBlackAndWhite(BufferedImage colorImage, double[] weights) {
        if (colorImage == null) {
            throw new IllegalArgumentException("Check the input data. The input image cannot be null.");
        }

        if (weights == null || weights.length != 3) {
            throw new IllegalArgumentException("Check the input data. Weights must be an array of 3 elements.");
        }

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
