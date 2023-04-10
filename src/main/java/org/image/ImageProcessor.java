package org.image;

import java.awt.image.BufferedImage;

public class ImageProcessor {

    public static BufferedImage convertToBlackAndWhite(BufferedImage colorImage, double[] weights) {
        BufferedImage blackWhiteImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();


