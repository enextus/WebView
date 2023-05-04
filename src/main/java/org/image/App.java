package org.image;

import java.awt.image.BufferedImage;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.image.Window.decodeBase64ToImage;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final String IMAGE_DIRECTORY = "/img";

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        LOGGER.info("App \"Magnet Opener\" running");

        String randomImagePath = getRandomImagePath();
        String imagePath = getRandomImagePath();
        String base64ImageString = readResourceFileToString(imagePath);
        BufferedImage imageDecode = decodeBase64ToImage(base64ImageString);

        if (imageDecode != null) {
            logSelectedImage(randomImagePath);
            Window.displayImages(imageDecode);
        } else {
            System.err.println("Failed to decode the image.");
        }

    }

    public static String getRandomImagePath() {
        List<String> imagePaths = new ArrayList<>();

        try (InputStream inputStream = App.class.getResourceAsStream(IMAGE_DIRECTORY + "/img.config");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                imagePaths.add(IMAGE_DIRECTORY + "/" + line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image directory: " + IMAGE_DIRECTORY, e);
        }

        if (imagePaths.isEmpty()) {
            throw new RuntimeException("No images found in the directory: " + IMAGE_DIRECTORY);
        }

        String randomImagePath = imagePaths.get(RANDOM.nextInt(imagePaths.size()));
        return randomImagePath;
    }


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
    private static String readResourceFileToString(String imagePath) {

        InputStream inputStream = App.class.getResourceAsStream(imagePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("Resource file not found: " + imagePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource file: " + imagePath, e);
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
