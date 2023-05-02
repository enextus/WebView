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

    private static String originalFileName;

    public static String getOriginalFileName() {
        return originalFileName;
    }

    public static void setOriginalFileName(String fileName) {
        originalFileName = fileName;
    }

    static void saveImage(BufferedImage image) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save image as");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG Images", "jpg"));

        String imgSuffix = String.valueOf((int) (Math.random() * (99999 - 11111 + 1) + 11111));
        String filenameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        fileChooser.setSelectedFile(new File(filenameWithoutExtension + imgSuffix + "_bw.jpg"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                ImageIO.write(image, "jpg", fileToSave);
                LOGGER.log(Level.INFO, "Saved processed file: {0}", fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
