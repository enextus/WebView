package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageProcessorTest {

    @Test
    public void testConvertToBlackAndWhite() throws IOException {
        String inputImagePath = "src/main/resources/img/test_image.jpg";
        BufferedImage colorImage = ImageIO.read(new File(inputImagePath));
        assertNotNull(colorImage, "Color image should not be null");

        BufferedImage blackWhiteImage = ImageProcessor.convertToBlackAndWhite(colorImage);
        assertNotNull(blackWhiteImage, "Black and white image should not be null");

        assertEquals(BufferedImage.TYPE_BYTE_BINARY, blackWhiteImage.getType(), "Image type should be BufferedImage.TYPE_BYTE_BINARY");
    }
}
