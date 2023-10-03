package org.image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AppWindow {
    private static final String FRAME = "MaLO © Magnet Links Opener 2023";
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = new Color(255, 215, 0); // Gold
    private static final int NULLIS = 0;
    private static final int HORIZONTAL_SPACING = 2;
    private static final int VERTICAL_SPACING = 10;
    private static final int URL_FIELD_COLUMNS = 5;
    private static final int URL_PANEL_WIDTH = 300;
    private static final String FONT_NAME = "Arial";
    private static final int FONT_STYLE = Font.PLAIN;
    private static final int FONT_SIZE = 53;
    private static final int TEXT_AREA_ROWS = 10;
    private static final int TEXT_AREA_COLUMNS = 33;
    private static final int TIMER_DELAY = 150;
    private static final String DEFAULT_URL = "https://xxxtor.com/";
    private static final String TOP_URL = "https://xxxtor.com/top/";
    private static final String NEW_BUTTON_LABEL = "New";
    private static final String TOP_BUTTON_LABEL = "TOP";
    private static final String OK_BUTTON_TEXT = "OK";
    private static final String ERROR_DIALOG_TITLE = "Issue!";
    private static final String INVALID_URL_MESSAGE = "Invalid URL. Please enter a valid URL.";
    private static final String PASTE_MENU_ITEM_TEXT = "Paste";
    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String ERROR_MESSAGE_URL_SYNTAX = "URL could not be parsed. Please check the URL.";
    private static final String ERROR_MESSAGE_URL_MALFORMED = "URL is malformed. Please check the URL.";
    private static final String CONTEXT_MENU_PASTE = "Paste";
    private static final JTextField urlField = new JTextField();
    private static final int URL_PANEL_HEIGHT = urlField.getPreferredSize().height;
    private static final Dimension URL_PANEL_DIMENSION = new Dimension(URL_PANEL_WIDTH, URL_PANEL_HEIGHT);
    private static final Dimension RIGID_AREA_DIMENSION_HORIZONTAL = new Dimension(HORIZONTAL_SPACING, NULLIS);
    private static final Dimension RIGID_AREA_DIMENSION_VERTICAL = new Dimension(NULLIS, VERTICAL_SPACING);
    private static JTextArea magnetLinksTextArea;




    static {
        urlField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e);
                }
            }

            private void showContextMenu(MouseEvent e) {
                JPopupMenu contextMenu = new JPopupMenu();
                JMenuItem pasteMenuItem = new JMenuItem(CONTEXT_MENU_PASTE);
                pasteMenuItem.addActionListener(e1 -> {
                    urlField.paste();
                });
                contextMenu.add(pasteMenuItem);
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }



    public static void addMagnetLinkToTextArea(String magnetLink) {
        if (magnetLink != null && !magnetLink.isEmpty()) {
            AppWindow.magnetLinksTextArea.append(magnetLink + "\n");
        }
    }



    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    private static JFrame createMainFrame() {
        JFrame frame = new JFrame(FRAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        return frame;
    }


    private static JPanel createUrlPanel() {
        urlField.setColumns(URL_FIELD_COLUMNS);
        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));
        urlPanel.add(new JLabel(""));
        urlPanel.add(urlField);
        urlPanel.setMaximumSize(URL_PANEL_DIMENSION);
        urlPanel.setPreferredSize(URL_PANEL_DIMENSION);

        return urlPanel;
    }





    private static JPanel createTextAreaPanel() {
        magnetLinksTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);
        magnetLinksTextArea.setEditable(false);
        magnetLinksTextArea.setForeground(TEXT_COLOR);
        magnetLinksTextArea.setBackground(BACKGROUND_COLOR);
        JScrollPane scrollPane = new JScrollPane(magnetLinksTextArea);
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(scrollPane, BorderLayout.NORTH);

        return textAreaPanel;
    }



}
