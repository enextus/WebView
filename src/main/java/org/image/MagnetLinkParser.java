package org.image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

public class MagnetLinkParser {
    public static void main(String[] args) {
        String url = "https://xxxtor.com/kino/";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements magnetLinks = doc.select("a[href^=magnet]");

            for (Element magnetLink : magnetLinks) {
                String link = magnetLink.attr("href");
                System.out.println("Найдена ссылка: " + link);
                openMagnetLinkInTorrentClient(link, Desktop.getDesktop());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the given magnet link in the default torrent client installed on the user's system.
     * <p>
     * This method uses the java.awt.Desktop class to open the magnet link in the default torrent client.
     * It first checks if the Desktop class is supported on the current platform, and then checks if the
     * BROWSE action is supported. If both conditions are met, it proceeds to open the magnet link as a URI
     * using the browse() method. In most cases, this will trigger the default torrent client to open and
     * start downloading the linked content.
     * <p>
     * It is important to note that using this program to download illegal content may violate the laws
     * of your country. Ensure that you use this program in compliance with the law.
     *
     * @param magnetLink The magnet link to be opened in the default torrent client
     */
    private static void openMagnetLinkInTorrentClient(String magnetLink, Desktop desktop) {
        try {
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI magnetURI = new URI(magnetLink);
                desktop.browse(magnetURI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
