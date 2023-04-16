package org.image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class MagnetLinkParserTest {


    @Test
    void parseMagnetLinksFromWebsite() throws IOException {
        String testUrl = "https://xxxtor.com/";
        Document doc = Jsoup.connect(testUrl).get();
        List<String> magnetLinks = doc.select("a[href^=magnet]").stream().map(e -> e.attr("href")).collect(Collectors.toList());

        // Ensure that the list of magnet links is not empty
        assertFalse(magnetLinks.isEmpty());
    }

    @Test
    void openMagnetLinkInTorrentClient() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, URISyntaxException, IOException {
        String testMagnetLink = "magnet:?xt=urn:btih:TEST&dn=test.torrent";

        Desktop mockDesktop = mock(Desktop.class);
        when(mockDesktop.isSupported(Desktop.Action.BROWSE)).thenReturn(true);

        // Access the private method using reflection
        Method openMagnetLinkInTorrentClient = MagnetLinkParser.class.getDeclaredMethod("openMagnetLinkInTorrentClient", String.class, Desktop.class);
        openMagnetLinkInTorrentClient.setAccessible(true);

        // Invoke the method with the test magnet link and the mocked Desktop instance
        openMagnetLinkInTorrentClient.invoke(null, testMagnetLink, mockDesktop);

        // Verify that the browse() method was called with the correct URI
        verify(mockDesktop).browse(new URI(testMagnetLink));
    }
}