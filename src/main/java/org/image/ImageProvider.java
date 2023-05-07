package org.image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageProvider {
    private static final String IMAGE_DIRECTORY = "/img";
    private static final Random RANDOM = new Random();

    /**
     * This method, getRandomImagePath(), returns a randomly-selected image path from the specified image directory.
     * It reads the image paths from a properties file (img.properties) within the directory, adding them to a list.
     * After ensuring the list isn't empty, the method selects a random image path from the list and returns it.
     *
     * @return A randomly-selected image path from the specified image directory.
     * @throws RuntimeException if there is an error reading the image directory or if no images are found in the directory.
     */
    public static String getRandomImagePath() {

        List<String> imagePaths = new ArrayList<>();

        try (InputStream inputStream = App.class.getResourceAsStream(IMAGE_DIRECTORY + "/img.properties")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    imagePaths.add(IMAGE_DIRECTORY + "/" + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image directory: " + IMAGE_DIRECTORY, e);
        }

        if (imagePaths.isEmpty()) {
            throw new RuntimeException("No images found in the directory: " + IMAGE_DIRECTORY);
        }

        return imagePaths.get(RANDOM.nextInt(imagePaths.size()));
    }

}
