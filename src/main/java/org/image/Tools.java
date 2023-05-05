package org.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Tools {

    static BufferedImage decodeBase64ToImage(String base64ImageString) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage scaleImageForPreview(BufferedImage source) {

        final int maxSize = 512;
        double scaleFactor = Math.min((double) maxSize / source.getWidth(), (double) maxSize / source.getHeight());

        int newWidth = (int) (source.getWidth() * scaleFactor);
        int newHeight = (int) (source.getHeight() * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, source.getType());

        Graphics2D g = scaledImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return scaledImage;
    }
}
