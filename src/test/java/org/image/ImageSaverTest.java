package org.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

class ImageSaverTest {
    private ImageSaver imageSaver;
    private BufferedImage testImage;
    private String testFileName;

    @BeforeEach
    void setUp() {
        imageSaver = new ImageSaver();
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        testFileName = "test_image.png";
    }

    @Test
    void getOriginalFileName() {
        // Your existing test implementation
    }

    @Test
    void setOriginalFileName() {
        imageSaver.setOriginalFileName(testFileName);
        String originalFileName = imageSaver.getOriginalFileName();
        assertNotNull(originalFileName, "Original file name should not be null");
        assertEquals(testFileName, originalFileName, "Original file name should match the set value");
    }

/*    @Test
    void saveImage() {
        imageSaver.setOriginalFileName(testFileName);

        // If your saveImage() method throws any exceptions, make sure to catch them here
        try {
            imageSaver.saveImage(testImage);
        } catch (Exception e) {
            fail("Image saving failed with an exception: " + e.getMessage());
        }

        File savedFile = new File(testFileName);
        assertTrue(savedFile.exists(), "Saved file should exist");

        try {
            BufferedImage loadedImage = ImageIO.read(savedFile);
            assertNotNull(loadedImage, "Loaded image should not be null");
            assertEquals(testImage.getWidth(), loadedImage.getWidth(), "Loaded image width should match the original");
            assertEquals(testImage.getHeight(), loadedImage.getHeight(), "Loaded image height should match the original");
        } catch (IOException e) {
            fail("Failed to load saved image");
        } finally {
            // Clean up the saved file
            if (savedFile.exists()) {
                savedFile.delete();
            }
        }
    }*/


}
