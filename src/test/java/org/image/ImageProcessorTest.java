package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

public class ImageProcessorTest {

    private String inputImagePath;
    private BufferedImage colorImage;

    @BeforeEach
    public void setUp() throws IOException {
        inputImagePath = "src/main/resources/img/image.jpg";
        colorImage = ImageIO.read(new File(inputImagePath));
        assertNotNull(colorImage, "Color image should not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"convertToBlackAndWhite1", "convertToBlackAndWhite2", "convertToBlackAndWhite3"})
    public void testConvertToBlackAndWhite(String methodName) throws IOException {
        Function<BufferedImage, BufferedImage> imageProcessorMethod = getImageProcessorMethod(methodName);

        BufferedImage blackWhiteImage = imageProcessorMethod.apply(colorImage);
        assertNotNull(blackWhiteImage, "Black and white image should not be null");
        assertEquals(BufferedImage.TYPE_BYTE_BINARY, blackWhiteImage.getType(), "Image type should be BufferedImage.TYPE_BYTE_BINARY");
        assertEquals(colorImage.getWidth(), blackWhiteImage.getWidth(), "Width should be equal");
        assertEquals(colorImage.getHeight(), blackWhiteImage.getHeight(), "Height should be equal");
    }

    @Test
    public void testScaleImageForPreview() {
        BufferedImage scaledImage = ImageProcessor.scaleImageForPreview(colorImage);
        assertNotNull(scaledImage, "Scaled image should not be null");
        assertTrue(scaledImage.getWidth() <= 500 && scaledImage.getHeight() <= 500, "Scaled image dimensions should not exceed 500x500");
    }

    private Function<BufferedImage, BufferedImage> getImageProcessorMethod(String methodName) {
        switch (methodName) {
            case "convertToBlackAndWhite1":
                return ImageProcessor::convertToBlackAndWhite1;
            case "convertToBlackAndWhite2":
                return ImageProcessor::convertToBlackAndWhite2;
            case "convertToBlackAndWhite3":
                return ImageProcessor::convertToBlackAndWhite3;
            default:
                throw new IllegalArgumentException("Invalid method name: " + methodName);
        }
    }

    @Test
    public void testSaveBlackWhiteImage(@TempDir Path tempDir) throws IOException {
        BufferedImage bwImage = ImageProcessor.convertToBlackAndWhite1(colorImage);

        String outputImagePath = tempDir.resolve("image.jpg").toString();
        File outputFileBeforeSave = new File(outputImagePath);
        assertFalse(outputFileBeforeSave.exists(), "Output file should not exist before saving");

        ImageProcessor.saveBlackWhiteImage(bwImage, inputImagePath);

        String expectedOutputImagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf('.')) + "_bw.jpg";
        File outputFile = new File(expectedOutputImagePath);
        assertTrue(outputFile.exists(), "Output file should exist");
        assertTrue(outputFile.getName().endsWith("_bw.jpg"), "Output file name should end with '_bw.jpg'");

        BufferedImage savedImage = ImageIO.read(outputFile);
        assertNotNull(savedImage, "Saved image should not be null");
        assertEquals(bwImage.getWidth(), savedImage.getWidth(), "Width should be equal");
        assertEquals(bwImage.getHeight(), savedImage.getHeight(), "Height should be equal");
    }

}
