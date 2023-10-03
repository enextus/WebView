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
        WebViewApp app = new WebViewApp();


    }


}
