/**
 * The BWEffectsImageProcessor application is a Java program that reads an input image, applies
 * three different black and white effects using predefined weights, and displays the original
 * and processed images. The application uses the ImageIO library for reading and writing images,
 * and Swing for file selection and image display.
 * <p>
 * The main steps of the application include:
 * 1. Defining three sets of weights for the black and white conversion.
 * 2. Using a JFileChooser object to open a file selection dialog for the user to choose an image.
 * 3. Reading the selected image into a BufferedImage object.
 * 4. Applying the black and white conversion to the input image using each set of weights.
 * 5. Displaying the original and processed images.
 */
package org.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.image.ImageDisplay.decodeBase64ToImage;
import static org.image.ImageDisplay.displayImageInGUI;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final String IMAGE_PATH = "src/main/resources/img/myImage.txt";


    public static void main(String[] args) throws IOException {

        LOGGER.info("App \"BWEffectsImageProcessor\" running");

        String base64ImageString = new String(Files.readAllBytes(Paths.get(IMAGE_PATH)));
        BufferedImage imageDecode = decodeBase64ToImage(base64ImageString);

        if (imageDecode != null) {
            // displayImageInGUI(imageDecode);
            BufferedImage colorImage = imageDecode;

            BufferedImage bwImage1 =null;
            BufferedImage bwImage2 = null;
            BufferedImage bwImage3 = null;
            ImageDisplay.displayImages(colorImage, bwImage1, bwImage2, bwImage3);

        } else {
            System.err.println("Не удалось декодировать изображение.");
        }

    }

    public static void logSelectedImage(String imagePath) {
        LOGGER.log(Level.INFO, "Opened original file: {0}", imagePath);
    }

}
