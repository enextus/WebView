package org.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

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

                logSelectedImage(inputImagePath);

                logger.info("Selected image: {}", inputImagePath);

                BufferedImage colorImage = ImageIO.read(new File(inputImagePath));

                BufferedImage bwImage1 = ImageProcessor.convertToBlackAndWhite(colorImage, weights1);
                BufferedImage bwImage2 = ImageProcessor.convertToBlackAndWhite(colorImage, weights2);
                BufferedImage bwImage3 = ImageProcessor.convertToBlackAndWhite(colorImage, weights3);

                ImageDisplay.displayImages(colorImage, bwImage1, bwImage2, bwImage3);
            } catch (IOException e) {
                logger.error("Error processing image", e);
                e.printStackTrace();
            }
        }

    }

    public static void logSelectedImage(String imagePath) {
        logger.info("Selected image: {}", imagePath);
    }

}
