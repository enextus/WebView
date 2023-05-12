package org.image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class AppWindow {
    static JLabel numberLabel = new JLabel(Integer.toString(LinkParser.getNumberOfFoundLinks()));

    /**
     * The background color used for the components in the program's GUI.
     * Currently set to black.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /**
     * The text color used for the components in the program's GUI.
     * Currently set to gold (RGB: 255, 215, 0).
     */
    private static final Color TEXT_COLOR = new Color(255, 215, 0); // Gold

    /**
     * The label used to display the original image in the program's GUI.
     */
    private static JLabel originalImageLabel;

    /**
     * The text area used to display magnet links in the program's GUI.
     */
    static JTextArea magnetLinksTextArea;
    private static final JTextField urlField = new JTextField();

    // Add a mouse event handler for the urlField input field
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
                JMenuItem pasteMenuItem = new JMenuItem("Paste");
                pasteMenuItem.addActionListener(e1 -> {
                    urlField.paste();
                });
                contextMenu.add(pasteMenuItem);
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    private static void enterUrl() {
        String urlString = urlField.getText();
        LoggerUtil.logURL(urlString);

        try {
            URL url = new URL(urlString);
            LinkParser.parseUrl(url.toString());
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid URL. Please enter a valid URL.", "Issue!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void addMagnetLinkToTextArea(String magnetLink) {
        if (magnetLink != null && !magnetLink.isEmpty()) {
            AppWindow.magnetLinksTextArea.append(magnetLink + "\n");
        }
    }

    public static void displayImages(BufferedImage colorImage) {
        SwingUtilities.invokeLater(() -> {
            configureLookAndFeel();
            JFrame frame = createMainFrame();
            JPanel centerPanel = createCenterPanel(colorImage);
            JPanel textAreaPanel = createTextAreaPanel();
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(textAreaPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            startTimer();
            urlField.requestFocusInWindow();
        });
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
        JFrame frame = new JFrame("MaLO © Magnet Links Opener 2023");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        return frame;
    }

    private static JPanel createCenterPanel(BufferedImage colorImage) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);

        originalImageLabel = new JLabel(new ImageIcon(ImgProcessor.scaleImageForPreview(colorImage)));
        centerPanel.add(originalImageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        originalImageLabel.setLayout(new BorderLayout());
        originalImageLabel.add(buttonPanel, BorderLayout.CENTER);

        return centerPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        buttonPanel.add(createNumberPanel());
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
        buttonPanel.add(createInputPanel());

        return buttonPanel;
    }

    private static JPanel createNumberPanel() {
        numberLabel = new JLabel(Integer.toString(LinkParser.getNumberOfFoundLinks()));
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 53));
        numberLabel.setForeground(TEXT_COLOR);

        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.X_AXIS));
        numberPanel.setOpaque(false);
        numberPanel.add(new JLabel(""));
        numberPanel.add(numberLabel);
        return numberPanel;
    }

    private static JPanel createUrlPanel() {
        urlField.setColumns(33);

        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));

        urlPanel.add(new JLabel(""));
        urlPanel.add(urlField);

        urlPanel.setMaximumSize(new Dimension(300, urlField.getPreferredSize().height));
        urlPanel.setPreferredSize(new Dimension(300, urlField.getPreferredSize().height));

        return urlPanel;
    }

    private static JButton createOkButton() {
        JButton jButton = new JButton("OK");
        jButton.addActionListener(e -> enterUrl());
        return jButton;
    }

    private static JButton createSearchTopButton() {
        JButton searchButton = new JButton("Top");
        searchButton.addActionListener(e -> {
            urlField.setText("https://xxxtor.com/top/");
            enterUrl();
        });
        return searchButton;
    }

    private static JButton createSearchNewButton() {
        JButton searchButton = new JButton("New");
        searchButton.addActionListener(e -> {
            urlField.setText("https://xxxtor.com/");
            enterUrl();
        });
        return searchButton;
    }

    private static JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Changed layout to Y_AXIS for vertical alignment
        inputPanel.setOpaque(false);

        // This panel contains the url field and existing buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setOpaque(false);

        topPanel.add(createUrlPanel());
        topPanel.add(Box.createRigidArea(new Dimension(3, 0))); // Add a horizontal spacing
        topPanel.add(createOkButton());

        // This panel contains the new search button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setOpaque(false);

        bottomPanel.add(createSearchNewButton());
        bottomPanel.add(Box.createRigidArea(new Dimension(3, 0))); // Add a horizontal spacing
        bottomPanel.add(createSearchTopButton());
        bottomPanel.add(Box.createRigidArea(new Dimension(3, 0))); // Add a horizontal spacing
        bottomPanel.add(createClearButton());
        bottomPanel.add(Box.createRigidArea(new Dimension(3, 0))); // Add a horizontal spacing


        // Add the panels to the main inputPanel
        inputPanel.add(topPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
        inputPanel.add(bottomPanel);

        return inputPanel;
    }

    private static JButton createClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            LinkParser.resetNumberOfFoundLinks();
            urlField.setText("");
            magnetLinksTextArea.setText("");
            urlField.requestFocusInWindow();
        });
        return clearButton;
    }

    private static JPanel createTextAreaPanel() {
        magnetLinksTextArea = new JTextArea(10, 33);
        magnetLinksTextArea.setEditable(false);
        magnetLinksTextArea.setForeground(TEXT_COLOR);
        magnetLinksTextArea.setBackground(BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(magnetLinksTextArea);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(scrollPane, BorderLayout.NORTH);

        return textAreaPanel;
    }

    private static void startTimer() {
        int delay = 150;
        Timer timer = new Timer(delay, e -> {
            // JLabel numberLabel уже объявлен как поле класса AppWindow

            numberLabel.setText(Integer.toString(LinkParser.getNumberOfFoundLinks()));
        });
        timer.start();
    }

}
