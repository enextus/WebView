package org.image;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParserTest {

    private Parser parser;

    @Mock
    private Connection connection;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }


    @Test
    public void testParseInvalidUrl() {
        // Test case for invalid URL
        String invalidUrl = "this-is-not-a-url";
        assertThrows(IllegalArgumentException.class, () -> Parser.parseUrl(invalidUrl),
                "parseUrl should throw IllegalArgumentException for an invalid URL");
    }

}