package org.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

public class ImageProcessor {

    /**
     * Scales a BufferedImage to fit within a preview area of maximum size 512x512 pixels while maintaining its aspect ratio.
     *
     * @param source the BufferedImage to scale
     * @return the scaled BufferedImage
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

    /**
     * Decodes a Base64-encoded string back to a BufferedImage.
     *
     * @param base64ImageString The Base64-encoded string of the image.
     * @return The BufferedImage decoded from the provided Base64-encoded string.
     */
    public static BufferedImage decodeBase64ToImage(String base64ImageString) {
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            System.err.println("Failed to decode the image.");
            return null;
        }

    }

    /**
     * Reads a resource file located at the specified path and returns its content as a string.
     * The file is read using UTF-8 encoding.
     *
     * @param imagePath The path of the resource file.
     * @return The content of the resource file as a string.
     * @throws IllegalArgumentException If the resource file is not found.
     * @throws RuntimeException         If there's an error reading the resource file.
     */
    public static String readResourceFileToString(String imagePath) {

        InputStream inputStream = ImageProcessor.class.getResourceAsStream(imagePath);

        // ReadingAndPrintingBytes(inputStream);

        if (inputStream == null) {
            throw new IllegalArgumentException("Resource file not found: " + imagePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource file: " + imagePath, e);
        }
    }

    public static void ReadingAndPrintingBytes(InputStream inputStream) {

        // Reading and printing bytes to the console
        try {
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                System.out.print(byteRead + " : ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Закрываем InputStream
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
