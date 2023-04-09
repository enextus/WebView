package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
