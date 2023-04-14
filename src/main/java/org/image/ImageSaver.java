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
        // Your existing code
    }

    public static void saveBlackWhiteImage(BufferedImage bwImage, String inputImagePath) throws IOException {
        // Your existing code
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
}



/*
package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaver {

    private static String originalFileName;

    public static String getOriginalFileName() {
        return originalFileName;
    }

    public static void setOriginalFileName(String fileName) {
        originalFileName = fileName;
    }

    static void saveImage(BufferedImage image) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".jpg")) {
                filePath += ".jpg";
            }

            try {
                ImageIO.write(image, "jpg", new File(filePath));
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
*/
