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
    void setOriginalFileName() {
        imageSaver.setOriginalFileName(testFileName);
        String originalFileName = imageSaver.getOriginalFileName();
        assertNotNull(originalFileName, "Original file name should not be null");
        assertEquals(testFileName, originalFileName, "Original file name should match the set value");
    }


}
