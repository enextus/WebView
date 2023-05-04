package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

public class WindowTest {
    private Window window;

    @BeforeEach
    public void setUp() {
        window = new Window();
    }

    @Test
    public void testDecodeBase64ToImage_validBase64String() {
        String validBase64ImageString = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg=="; // 1x1 red pixel in PNG format
        BufferedImage result = Window.decodeBase64ToImage(validBase64ImageString);

        assertNotNull(result, "Image should not be null when decoding a valid Base64 string");
    }

    @Test
    public void testDecodeBase64ToImage_invalidBase64String() {
        String invalidBase64ImageString = "invalid_base64_string";
        assertThrows(IllegalArgumentException.class, () -> Window.decodeBase64ToImage(invalidBase64ImageString),
                "decodeBase64ToImage should throw IllegalArgumentException when decoding an invalid Base64 string");
    }

    @Test
    public void testDecodeBase64ToImage_nullBase64String() {
        String nullBase64ImageString = null;
        assertThrows(NullPointerException.class, () -> Window.decodeBase64ToImage(nullBase64ImageString),
                "decodeBase64ToImage should throw NullPointerException when decoding a null Base64 string");
    }

    @Test
    public void testDisplayImages() {
        // Prepare a test image
        String validBase64ImageString = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg=="; // 1x1 red pixel in PNG format
        BufferedImage testImage = Window.decodeBase64ToImage(validBase64ImageString);

        // Since displayImages is a GUI-related method and doesn't return any values,
        // it's difficult to test its functionality directly. You can visually test
        // the method by calling it and checking if the image is displayed correctly.
        Window.displayImages(testImage);

        // Alternatively, you could refactor the method to return a JFrame or JPanel object,
        // which could then be inspected for the presence of the image.
    }

}
