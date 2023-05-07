/**
 * The Parser class is responsible for parsing a given URL for magnet links and opening them in the default torrent client
 * installed on the user's system. The class connects to the specified URL and retrieves the page content using Jsoup, selects
 * all magnet links on the page using a CSS selector, and processes each found magnet link. For each magnet link, the class
 * extracts the "href" attribute and opens the link in the default torrent client using the openMagnetLinkInTorrentClient() method.
 * Note that using this program to download illegal content may violate the laws of your country. Ensure that you use this program
 * in compliance with the law.
 */
package org.image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Desktop;
import java.io.InputStream;
import java.net.URI;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Parser {
    /**
     * The logger used to log information about the parsing process.
     */
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * The number of magnet links that have been found so far.
     */
    private static Integer numberOfFoundLinks = 0;

    /*
      Initializes the logger configuration for the program by reading the "logging.properties" file
      located in the root of the classpath.
     */
    static {
        try {
            InputStream configFile = Parser.class.getResourceAsStream("/logging.properties");
            if (configFile == null) {
                throw new FileNotFoundException("File \"logging.properties\" not found!");
            }
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load the logger configuration file", e);
        }
    }

    /**
     * Increments the counter for the number of found magnet links.
     */
    synchronized static void incrementNumberOfFoundLinks() {
        numberOfFoundLinks++;
    }

    /**
     * Returns the number of magnet links that have been found so far.
     *
     * @return the number of found magnet links
     */
    public static Integer getNumberOfFoundLinks() {
        return numberOfFoundLinks;
    }

    private static volatile boolean isSearching = true;

    public static void stopSearching() {
        isSearching = false;
    }

    /**
     * Processes a magnet link by incrementing the counter for the number of found magnet links,
     * extracting the "href" attribute from the link element, printing the link and number of found
     * links to the console, logging the link, adding the magnet link to a text area, and opening
     * the magnet link in the default torrent client.
     *
     * @param magnetLink the element representing the magnet link to be processed
     */
    static void processMagnetLink(Element magnetLink) {
        // Increment the counter for the number of found magnet links
        incrementNumberOfFoundLinks();

        // Extracting the "href" attribute from the link element
        String link = magnetLink.attr("href");
        System.out.println(getNumberOfFoundLinks() + ": " + "Link found: " + link);

        // For the logfile
        logger.log(Level.INFO, getNumberOfFoundLinks() + ": " + "Link found: " + link);

        // Add magnet link to text area
        Window.addMagnetLinkToTextArea(getNumberOfFoundLinks() + ": " + link);

        // Opening the magnet link in the default torrent client
        openMagnetLinkInTorrentClient(link, Desktop.getDesktop());
    }

    public static void resetNumberOfFoundLinks() {
        numberOfFoundLinks = 0;
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
        // Обязательно установите isSearching обратно в true перед началом поиска
        isSearching = true;
        try {
            // Connecting to the URL and obtaining the document using Jsoup
            Document doc = Jsoup.connect(url).get();
            // Selecting magnet links on the page using a CSS selector
            Elements magnetLinks = doc.select("a[href^=magnet]");

            // Create a thread pool with a fixed number of worker threads
            int numberOfThreads = 8; // Задайте количество потоков в пуле
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

            // Processing each found magnet link
            for (Element magnetLink : magnetLinks) {
                if (!isSearching) { // Проверьте, не была ли нажата кнопка STOP
                    executorService.shutdownNow(); // Прекратить выполнение всех задач в пуле потоков
                    break;
                }

                executorService.submit(() -> processMagnetLink(magnetLink));
            }

            // Complete the thread pool work after processing all tasks.
            if (isSearching) {
                executorService.shutdown();
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the URL", e);
            e.printStackTrace();
        }
    }

    /**
     * Opens the given magnet link in the default torrent client installed on the user's system.
     * This method uses the java.awt.Desktop class to open the magnet link in the default torrent client.
     * It first checks if the Desktop class is supported on the current platform, and then checks if the
     * BROWSE action is supported. If both conditions are met, it proceeds to open the magnet link as a URI
     * using the browse() method. In most cases, this will trigger the default torrent client to open and
     * start downloading the linked content.
     * It is important to note that using this program to download illegal content may violate the laws
     * of your country. Ensure that you use this program in compliance with the law.
     *
     * @param magnetLink The magnet link to be opened in the default torrent client
     */
    static void openMagnetLinkInTorrentClient(String magnetLink, Desktop desktop) {
        try {
            // Checking if the BROWSE action is supported by the Desktop class on the current platform
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                // Creating a URI from the magnet link
                URI magnetURI = new URI(magnetLink);
                // Opening the magnet link in the default torrent client
                desktop.browse(magnetURI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
