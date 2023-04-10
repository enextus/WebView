package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaver {

    /**
     * Saves the given BufferedImage as a JPG file using a file chooser dialog.
     *
     * @param image The BufferedImage to be saved.
     */
    static void saveImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Input image cannot be null.");
        }

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

    /**
     * Saves the given black and white BufferedImage as a JPG file using the input image path as a base.
     *
     * @param bwImage       The black and white BufferedImage to be saved.
     * @param inputImagePath The path of the original input image.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void saveBlackWhiteImage(BufferedImage bwImage, String inputImagePath) throws IOException {
        if (bwImage == null) {
            throw new IllegalArgumentException("Input black and white image cannot be null.");
        }

        if (inputImagePath == null || inputImagePath.isEmpty()) {
            throw new IllegalArgumentException("Input image path cannot be null or empty.");
        }

        String outputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";
        ImageIO.write(bwImage, "jpg", new File(outputImagePath));
    }

}
