package org.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import static org.image.App.logURL;

public class Window {

    private static JLabel originalImageLabel;
    private static JTextArea magnetLinksTextArea;


    public static void displayImages(BufferedImage colorImage) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Magnet Links Parser.");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(Color.GRAY);

            originalImageLabel = new JLabel(new ImageIcon(scaleImageForPreview(colorImage)));
            centerPanel.add(originalImageLabel, BorderLayout.CENTER);

            JButton jButton = new JButton("Enter the URL of the page to be parsed");
            jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            jButton.addActionListener(e -> enterUrl());

            JPanel buttonPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(0, 0, 0, 64));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };

            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.gridx = 0;
            gbc.gridy = 0;

            double verticalPercent = -0.1; // Change this value to adjust the vertical position (0.0 to 1.0)
            double horizontalPercent = 0.05; // Change this value to adjust the horizontal position (0.0 to 1.0)

            int topInset = (int) (colorImage.getHeight() * verticalPercent);
            int leftInset = (int) (colorImage.getWidth() * horizontalPercent - jButton.getPreferredSize().getWidth() / 2);

            gbc.insets = new Insets(topInset, leftInset, 0, 0);

            buttonPanel.add(jButton, gbc);

            magnetLinksTextArea = new JTextArea(10, 50);
            magnetLinksTextArea.setEditable(false);
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
        });
    }

    public static void addMagnetLinkToTextArea(String magnetLink) {
        magnetLinksTextArea.append(magnetLink + "\n");
        magnetLinksTextArea.append(magnetLink + "\n");
        magnetLinksTextArea.setCaretPosition(magnetLinksTextArea.getDocument().getLength());

    }


    private static void enterUrl() {

        JTextField urlField = new JTextField(66);
        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);

        int result = JOptionPane.showConfirmDialog(null, urlPanel, "Enter the URL of the page to be parsed", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String urlString = urlField.getText();
            logURL(urlString);

            try {
                URL url = new URL(urlString);
                Parser.parseUrl(url.toString());
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Invalid URL. Please enter a valid URL.", "Issue!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static BufferedImage scaleImageForPreview(BufferedImage source) {

        final int maxSize = 512;
        double scaleFactor = Math.min((double) maxSize / source.getWidth(), (double) maxSize / source.getHeight());

        int newWidth = (int) (source.getWidth() * scaleFactor);
        int newHeight = (int) (source.getHeight() * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, source.getType());

        Graphics2D g = scaledImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return scaledImage;
    }

}
