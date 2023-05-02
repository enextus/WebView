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

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        LOGGER.info("App \"BWEffectsImageProcessor\" running");

        double[] weights1 = {0.35, 0.35, 0.35};
        double[] weights2 = {0.1, 0.79, 0.11};
        double[] weights3 = {0.79, 0.11, 0.1};

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {

                String inputImagePath = selectedFile.getCanonicalPath();
                ImageSaver.setOriginalFileName(selectedFile.getName());

                logSelectedImage(inputImagePath);

                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                BufferedImage bwImage1 = ImageProcessor.convertToBlackAndWhite(colorImage, weights1);
                BufferedImage bwImage2 = ImageProcessor.convertToBlackAndWhite(colorImage, weights2);
                BufferedImage bwImage3 = ImageProcessor.convertToBlackAndWhite(colorImage, weights3);

                ImageDisplay.displayImages(colorImage, bwImage1, bwImage2, bwImage3);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    public static void logSelectedImage(String imagePath) {
        LOGGER.log(Level.INFO, "Opened original file: {0}", imagePath);
    }

}
