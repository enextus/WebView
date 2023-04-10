package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSave {

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
