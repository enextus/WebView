package org.image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

import java.io.FileInputStream;

public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load the logger configuration file", e);
        }
    }

    /**
     * Parses the given URL for magnet links and opens them in the default torrent client installed on the user's system.
     * This method connects to the specified URL and retrieves the page content using Jsoup. It then selects all magnet
     * links on the page using a CSS selector. For each found magnet link, the method extracts the "href" attribute
     * and opens the link in the default torrent client using the openMagnetLinkInTorrentClient() method.
     * It is important to note that using this program to download illegal content may violate the laws
     * of your country. Ensure that you use this program in compliance with the law.
     *
     * @param url The URL to be parsed for magnet links
     */
    public static void parseUrl(String url) {
        try {
            // Connecting to the URL and obtaining the document using Jsoup
            Document doc = Jsoup.connect(url).get();
            //  Selecting magnet links on the page using a CSS selector
            Elements magnetLinks = doc.select("a[href^=magnet]");

            // Processing each found magnet link
            for (Element magnetLink : magnetLinks) {

                // Extracting the "href" attribute from the link element
                String link = magnetLink.attr("href");
                System.out.println("Link found: " + link);

                // log file
                logger.log(Level.INFO, "Link found: " + link);

                // Opening the magnet link in the default torrent client
                openMagnetLinkInTorrentClient(link, Desktop.getDesktop());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Произошла ошибка при подключении к URL", e);
            e.printStackTrace();
        }
    }

    /**
     * The main method of the MagnetLinkParser program.
     * This method serves as the entry point for the program. It connects to the specified URL and retrieves the page
     * content using Jsoup. It then selects all magnet links on the page using a CSS selector. For each found magnet link,
     * the method extracts the "href" attribute and opens the link in the default torrent client using the
     * openMagnetLinkInTorrentClient() method.
     * It is important to note that using this program to download illegal content may violate the laws
     * of your country. Ensure that you use this program in compliance with the law.
     *
     * @param args Command-line arguments (not used in this program)
     */
    public static void main(String[] args) {

        String url = "https://xxxtor.com/kino/";

        try {
            // Подключение к URL и получение документа с использованием Jsoup
            Document doc = Jsoup.connect(url).get();
            // Выбор магнитных ссылок на странице с использованием CSS-селектора
            Elements magnetLinks = doc.select("a[href^=magnet]");

            for (Element magnetLink : magnetLinks) {
                // Извлечение атрибута "href" из элемента ссылки
                String link = magnetLink.attr("href");
                System.out.println("Link found: " + link);
                // Открытие магнитной ссылки в торрент-клиенте по умолчанию
                openMagnetLinkInTorrentClient(link, Desktop.getDesktop());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the given magnet link in the default torrent client installed on the user's system.
     *
     * This method uses the java.awt.Desktop class to open the magnet link in the default torrent client.
     * It first checks if the Desktop class is supported on the current platform, and then checks if the
     * BROWSE action is supported. If both conditions are met, it proceeds to open the magnet link as a URI
     * using the browse() method. In most cases, this will trigger the default torrent client to open and
     * start downloading the linked content.
     *
     * It is important to note that using this program to download illegal content may violate the laws
     * of your country. Ensure that you use this program in compliance with the law.
     *
     * @param magnetLink The magnet link to be opened in the default torrent client
     */
    private static void openMagnetLinkInTorrentClient(String magnetLink, Desktop desktop) {
        try {
            // Проверка поддержки действия BROWSE классом Desktop на текущей платформе
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                // Создание URI из магнитной ссылки
                URI magnetURI = new URI(magnetLink);
                // Открытие магнитной ссылки в торрент-клиенте по умолчанию
                desktop.browse(magnetURI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
