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

        LOGGER.info("This is an info message");

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

                BufferedImage[] processedImages = {bwImage1, bwImage2, bwImage3};

                String[] fileNames = {"bwImage1.png", "bwImage2.png", "bwImage3.png"};
                // ImageSaver.saveProcessedImages(processedImages, fileNames);

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
