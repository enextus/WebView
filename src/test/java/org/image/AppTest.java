package org.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

    @BeforeAll
    static void setUp() {
        String testImagePath = "/img/1.img";
    }

    @Test
    public void testGetRandomImagePath_validDirectory() {
        // Create an instance of App
        App app = new App();

        // Call the getRandomImagePath method and check if it returns a valid image path
        assertDoesNotThrow(() -> ImageProvider.getRandomImagePath(),
                "getRandomImagePath should not throw an exception when called with a valid image directory");
    }

    @Test
    void testReadResourceFileToString_withInvalidPath() {
        String invalidPath = "/img/invalid.txt";

        Assertions.assertThrows(IllegalArgumentException.class, () -> ImageProvider.readResourceFileToString(invalidPath));
    }

    @Test
    void testReadResourceFileToString_withIOException() {
        // Create a mock InputStream that throws an IOException when read
        InputStream mockInputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };

        // Try to read a resource file with the mock InputStream
        String imagePath = "/img/test-image.txt";
        Assertions.assertThrows(RuntimeException.class, () -> {
            try (InputStream inputStream = mockInputStream) {
                ImageProvider.readResourceFileToString(imagePath);
            }
        });
    }

    @Test
    void testReadResourceFileToString() {
        String imagePath = "/img/test.txt";
        String content = ImageProvider.readResourceFileToString(imagePath);
        Assertions.assertEquals("Hello, world!", content);
    }

}
