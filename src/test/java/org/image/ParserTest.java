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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    public void testParseUrl_validUrl() throws IOException {
        // Prepare a mocked Jsoup Document with a magnet link
        String html = "<html><body><a href='magnet:?xt=urn:btih:12345'>Magnet Link</a></body></html>";
        Document mockedDocument = Jsoup.parse(html);

        // Stub the Jsoup.connect().get() method to return the mocked Document
        try (MockedStatic<Jsoup> jsoupMock = Mockito.mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(anyString())).thenReturn(connection);
            when(connection.get()).thenReturn(mockedDocument);

            // Call the parseUrl method with a valid URL and check if it processes the magnet link
            String validUrl = "http://example.com";
            assertDoesNotThrow(() -> parser.parseUrl(validUrl),
                    "parseUrl should not throw an exception when parsing a valid URL with a magnet link");
        }
    }
}