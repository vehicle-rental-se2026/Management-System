package com.vehiclerental.presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The SplashFrame class displays the splash screen
 * when the application starts.
 */
public class SplashFrame extends JFrame {

    private static final String FONT_NAME = "Segoe UI";
    private static final String TITLE_APP = "SB Car Rental";
    private static final String LOGO_PATH = "/images/logo.png";
    private static final int TIMER_DELAY_MS = 35;

    private static final Color PROGRESS_BAR_FG = new Color(92, 155, 214);
    private static final Color PROGRESS_BAR_BG = new Color(230, 235, 240);
    private static final Color OVERLAY_COLOR = new Color(8, 28, 48, 55);

    private JProgressBar progressBar;
    private JLabel loadingLabel;
    private Timer timer;
    private int progress = 0;

    public SplashFrame() {
        initializeFrame();

        BackgroundImagePanel mainPanel = new BackgroundImagePanel(LOGO_PATH);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        add(mainPanel);

        startLoading();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle(TITLE_APP);
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(new EmptyBorder(0, 120, 35, 120));

        progressBar = createProgressBar();
        loadingLabel = createStandardLabel("Loading... 0%", Font.PLAIN, 18, Color.WHITE, Component.CENTER_ALIGNMENT);

        bottomPanel.add(progressBar);
        bottomPanel.add(Box.createVerticalStrut(18));
        bottomPanel.add(loadingLabel);

        return bottomPanel;
    }

    // --- Helper UI Methods ---

    private JProgressBar createProgressBar() {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(0);
        bar.setStringPainted(false);
        bar.setPreferredSize(new Dimension(0, 16));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        bar.setForeground(PROGRESS_BAR_FG);
        bar.setBackground(PROGRESS_BAR_BG);
        bar.setBorder(BorderFactory.createEmptyBorder());
        return bar;
    }

    private JLabel createStandardLabel(String text, int fontStyle, int fontSize, Color color, float alignmentX) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, fontStyle, fontSize));
        label.setForeground(color);
        label.setAlignmentX(alignmentX);
        return label;
    }

    // --- Logic & Timers ---

    private void startLoading() {
        timer = new Timer(TIMER_DELAY_MS, e -> {
            progress++;
            progressBar.setValue(progress);
            loadingLabel.setText("Loading... " + progress + "%");

            if (progress >= 100) {
                timer.stop();
                dispose();
                new LoginFrame();
            }
        });

        timer.start();
    }

    // --- Custom UI Component ---

    private static class BackgroundImagePanel extends JPanel {

        private final Image backgroundImage;

        public BackgroundImagePanel(String imagePath) {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            backgroundImage = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            g2.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            g2.setColor(OVERLAY_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
        }
    }
}