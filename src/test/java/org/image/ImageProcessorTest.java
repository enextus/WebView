package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ImageProcessor class.
 */
public class ImageProcessorTest {

    private static final String INPUT_IMAGE_PATH = "src/main/resources/img/image.jpg";
    private BufferedImage colorImage;

    @BeforeEach
    public void setUp() throws IOException {
        colorImage = ImageIO.read(new File(INPUT_IMAGE_PATH));
        assertNotNull(colorImage, "Color image should not be null");
    }

    /**
     * Tests the conversion of a color image to black and white using the provided weights.
     *
     * @param weight1 the weight for the red channel
     * @param weight2 the weight for the green channel
     * @param weight3 the weight for the blue channel
     */
    @ParameterizedTest
    @CsvSource({"0.0, 0.0, 0.0", "0.0, 0.0, 0.0", "0.0, 0.0, 0.0",
            "0.3, 0.3, 0.3", "0.3, 0.3, 0.3", "0.3, 0.3, 0.3",
            "0.35, 0.35, 0.35", "0.1, 0.79, 0.11", "0.79, 0.11, 0.1"})
    public void testConvertToBlackAndWhite(double weight1, double weight2, double weight3) {
        double[] weights = {weight1, weight2, weight3};
        BufferedImage blackWhiteImage = ImageProcessor.convertToBlackAndWhite(colorImage, weights);
        assertNotNull(blackWhiteImage, "Black and white image should not be null");
        assertEquals(BufferedImage.TYPE_BYTE_BINARY, blackWhiteImage.getType(), "Image type should be BufferedImage.TYPE_BYTE_BINARY");
        assertEquals(colorImage.getWidth(), blackWhiteImage.getWidth(), "Width should be equal");
        assertEquals(colorImage.getHeight(), blackWhiteImage.getHeight(), "Height should be equal");
    }

    /**
     * Tests scaling of a color image to fit a maximum size of 500x500 pixels for preview purposes.
     */
    @Test
    public void testScaleImageForPreview() {
        BufferedImage scaledImage = ImageDisplay.scaleImageForPreview(colorImage);
        assertNotNull(scaledImage, "Scaled image should not be null");
        assertTrue(scaledImage.getWidth() <= 512 && scaledImage.getHeight() <= 512, "Scaled image dimensions should not exceed 500x500");
    }

}
