package org.image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.awt.Desktop;
import java.net.URI;

public class MagnetLinkParser {
    public static void main(String[] args) {
        String url = "https://xxxtor.com/";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements magnetLinks = doc.select("a[href^=magnet]");

            for (Element magnetLink : magnetLinks) {
                String link = magnetLink.attr("href");
                System.out.println("Найдена ссылка: " + link);
                openMagnetLinkInTorrentClient(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void openMagnetLinkInTorrentClient(String magnetLink) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    URI magnetURI = new URI(magnetLink);
                    desktop.browse(magnetURI);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
