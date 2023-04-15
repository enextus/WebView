package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageSaver {

    private static final Logger LOGGER = Logger.getLogger(ImageSaver.class.getName());

    private static String originalFileName = "none";



    public static String getOriginalFileName() {
        return originalFileName;
    }

    public static void setOriginalFileName(String fileName) {
        originalFileName = fileName;
    }

    public static void saveProcessedImages(BufferedImage[] images, String[] fileNames) {
        for (int i = 0; i < images.length; i++) {
            File outputFile = new File(fileNames[i]);
            try {
                ImageIO.write(images[i], "png", outputFile);
                LOGGER.log(Level.INFO, "Saved processed file: {0}", outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void saveImage(BufferedImage image) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save image as");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

        String filenameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        fileChooser.setSelectedFile(new File(filenameWithoutExtension + "_bw.png"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                ImageIO.write(image, "png", fileToSave);
                LOGGER.log(Level.INFO, "Saved processed file: {0}", fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveBlackWhiteImage(BufferedImage bwImage, String inputImagePath) throws IOException {
        String outputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";
        ImageIO.write(bwImage, "jpg", new File(outputImagePath));
    }

}
