/**
 *
 */
package org.image;

import java.awt.image.BufferedImage;
import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.image.ImageDisplay.decodeBase64ToImage;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static final String IMAGE_PATH = "/img/myImage.txt";

    /**
     * Logs the provided URL.
     *
     * @param url The URL to be logged.
     */
    public static void logURL(String url) {
        LOGGER.log(Level.INFO, "Parsed URL: {0}", url);
    }

    /**
     * Reads a resource file located at the specified path and returns its content as a string.
     * The file is read using UTF-8 encoding.
     *
     * @return The content of the resource file as a string.
     * @throws IllegalArgumentException If the resource file is not found.
     * @throws RuntimeException If there's an error reading the resource file.
     */
    private static String readResourceFileToString() {
        InputStream inputStream = ImageDisplay.class.getResourceAsStream(IMAGE_PATH);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource file not found: " + IMAGE_PATH);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource file: " + IMAGE_PATH, e);
        }

    }

    public static void main(String[] args) {

        LOGGER.info("App \"BWEffectsImageProcessor\" running");

        String base64ImageString = readResourceFileToString();
        BufferedImage imageDecode = decodeBase64ToImage(base64ImageString);

        if (imageDecode != null) {
            logSelectedImage(IMAGE_PATH);
            ImageDisplay.displayImages(imageDecode);
        } else {
            System.err.println("Failed to decode the image.");
        }

    }

    /**
     * Logs the selected image's path.
     *
     * @param imagePath The path of the selected image.
     */
    public static void logSelectedImage(String imagePath) {
        LOGGER.log(Level.INFO, "Opened original file: {0}", imagePath);
    }

}
