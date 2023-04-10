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

public class ImageProcessorTest {

    private static final String INPUT_IMAGE_PATH = "src/main/resources/img/image.jpg";
    private BufferedImage colorImage;

    @BeforeEach
    public void setUp() throws IOException {
        colorImage = ImageIO.read(new File(INPUT_IMAGE_PATH));
        assertNotNull(colorImage, "Color image should not be null");
    }

    @ParameterizedTest
    @CsvSource({"0.35, 0.35, 0.35", "0.1, 0.79, 0.11", "0.79, 0.11, 0.1"})
    public void testConvertToBlackAndWhite(double weight1, double weight2, double weight3) {
        double[] weights = {weight1, weight2, weight3};
        BufferedImage blackWhiteImage = ImageProcessor.convertToBlackAndWhite(colorImage, weights);
        assertNotNull(blackWhiteImage, "Black and white image should not be null");
        assertEquals(BufferedImage.TYPE_BYTE_BINARY, blackWhiteImage.getType(), "Image type should be BufferedImage.TYPE_BYTE_BINARY");
        assertEquals(colorImage.getWidth(), blackWhiteImage.getWidth(), "Width should be equal");
        assertEquals(colorImage.getHeight(), blackWhiteImage.getHeight(), "Height should be equal");
    }

    @Test
    public void testScaleImageForPreview() {
        BufferedImage scaledImage = ImageDisplay.scaleImageForPreview(colorImage);
        assertNotNull(scaledImage, "Scaled image should not be null");
        assertTrue(scaledImage.getWidth() <= 500 && scaledImage.getHeight() <= 500, "Scaled image dimensions should not exceed 500x500");
    }

    @Test
    public void testSaveBlackWhiteImage(@TempDir Path tempDir) throws IOException {
        double[] weights = {0.79, 0.11, 0.1};
        BufferedImage bwImage = ImageProcessor.convertToBlackAndWhite(colorImage, weights);

        String outputImagePath = tempDir.resolve("image.jpg").toString();
        File outputFileBeforeSave = new File(outputImagePath);
        assertFalse(outputFileBeforeSave.exists(), "Output file should not exist before saving");

        ImageSave.saveBlackWhiteImage(bwImage, INPUT_IMAGE_PATH);

        String expectedOutputImagePath = INPUT_IMAGE_PATH.substring(0, INPUT_IMAGE_PATH.lastIndexOf('.')) + "_bw.jpg";
        File outputFile = new File(expectedOutputImagePath);
        assertTrue(outputFile.exists(), "Output file should exist");
        assertTrue(outputFile.getName().endsWith("_bw.jpg"), "Output file name should end with '_bw.jpg'");

        BufferedImage savedImage = ImageIO.read(outputFile);
        assertNotNull(savedImage, "Saved image should not be null");
        assertEquals(bwImage.getWidth(), savedImage.getWidth(), "Width should be equal");
        assertEquals(bwImage.getHeight(), savedImage.getHeight(), "Height should be equal");
    }

}
