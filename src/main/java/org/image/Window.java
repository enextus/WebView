package org.image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Timer;

import static org.image.App.logURL;

public class Window {
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
        logURL(urlString);

        try {
            URL url = new URL(urlString);
            Parser.parseUrl(url.toString());
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid URL. Please enter a valid URL.", "Issue!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void displayImages(BufferedImage colorImage) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Magnet Links Opener 2023");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(BACKGROUND_COLOR);

            originalImageLabel = new JLabel(new ImageIcon(Tools.scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            JButton jButton = new JButton("OK");
            jButton.addActionListener(e -> enterUrl());

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(e -> {
                Parser.resetNumberOfFoundLinks();
                urlField.setText("");
                urlField.requestFocusInWindow();
            });

            urlField.setColumns(55);

            JPanel urlPanel = new JPanel();
            urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));

            urlPanel.add(new JLabel(""));
            urlPanel.add(urlField);

            urlPanel.setMaximumSize(new Dimension(300, urlField.getPreferredSize().height));
            urlPanel.setPreferredSize(new Dimension(300, urlField.getPreferredSize().height));

            JLabel numberLabel = new JLabel(Integer.toString(Parser.getNumberOfFoundLinks()));
            numberLabel.setFont(new Font("Arial", Font.PLAIN, 53));
            numberLabel.setForeground(TEXT_COLOR);

            JPanel numberPanel = new JPanel();
            numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.X_AXIS));
            numberPanel.setOpaque(false);
            numberPanel.add(new JLabel(""));
            numberPanel.add(numberLabel);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
            inputPanel.setOpaque(false);

            inputPanel.add(urlPanel);
            inputPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add a horizontal spacing
            inputPanel.add(jButton);
            inputPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add a horizontal spacing
            inputPanel.add(clearButton);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);

            buttonPanel.add(numberPanel);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a vertical spacing
            buttonPanel.add(inputPanel);

            magnetLinksTextArea = new JTextArea(10, 50);
            magnetLinksTextArea.setEditable(false);

            magnetLinksTextArea.setForeground(TEXT_COLOR);
            magnetLinksTextArea.setBackground(BACKGROUND_COLOR);

            JScrollPane scrollPane = new JScrollPane(magnetLinksTextArea);

            JPanel textAreaPanel = new JPanel();
            textAreaPanel.setLayout(new BorderLayout());
            textAreaPanel.add(scrollPane, BorderLayout.NORTH);

            originalImageLabel.setLayout(new BorderLayout());
            originalImageLabel.add(buttonPanel, BorderLayout.CENTER);

            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(textAreaPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            int delay = 150;
            Timer timer = new Timer(delay, e -> {
                numberLabel.setText(Integer.toString(Parser.getNumberOfFoundLinks()));
            });
            timer.start();

            urlField.requestFocusInWindow();
        });
    }

}
