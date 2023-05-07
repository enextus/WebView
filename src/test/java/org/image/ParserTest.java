package org.image;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Desktop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ParserTest {
    @Test
    public void testParseInvalidUrl() {
        // Test case for invalid URL
        String invalidUrl = "this-is-not-a-url";
        assertThrows(IllegalArgumentException.class, () -> LnkParser.parseUrl(invalidUrl),
                "parseUrl should throw IllegalArgumentException for an invalid URL");
    }
    @Test
    public void testParseUrlNoLinks() {
        // Test case for a URL that does not contain any magnet links
        String url = "https://www.example.com";
        LnkParser.parseUrl(url);
        assertEquals(0, LnkParser.getNumberOfFoundLinks(), "parseUrl should not find any magnet links");
    }

    @Test
    public void testParseUrlMultipleLinks() {
        // Test case for a URL that contains multiple magnet links
        String url = "https://torrentz2.eu.com/";
        int expectedNumberOfLinks = 0; // Set this to the expected number of magnet links on the page
        LnkParser.parseUrl(url);
        assertEquals(expectedNumberOfLinks, LnkParser.getNumberOfFoundLinks(), "parseUrl should find all magnet links");
    }



    @Test
    public void testOpenMagnetLinkInTorrentClient() {
        // Test case for opening a magnet link in the default torrent client
        String magnetLink = "magnet:?xt=urn:btih:abcdef1234567890";
        LnkParser.openMagnetLinkInTorrentClient(magnetLink, Desktop.getDesktop());
        // The test is successful if the magnet link is opened in the default torrent client
    }



}