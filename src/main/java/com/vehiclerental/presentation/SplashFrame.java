package com.vehiclerental.presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SplashFrame extends JFrame {

    private JProgressBar progressBar;
    private JLabel loadingLabel;
    private Timer timer;
    private int progress = 0;

    public SplashFrame() {

        initializeFrame();

        BackgroundImagePanel mainPanel =
                new BackgroundImagePanel("/images/logo.png");

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        add(mainPanel);

        startLoading();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("SB Car Rental");
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

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(0, 16));
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        progressBar.setForeground(new Color(92, 155, 214));
        progressBar.setBackground(new Color(230, 235, 240));
        progressBar.setBorder(BorderFactory.createEmptyBorder());

        loadingLabel = new JLabel("Loading... 0%");
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(progressBar);
        bottomPanel.add(Box.createVerticalStrut(18));
        bottomPanel.add(loadingLabel);

        return bottomPanel;
    }

    private void startLoading() {

        timer = new Timer(35, e -> {

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

            // تعبئة كاملة للشاشة
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            // طبقة خفيفة فوق الصورة حتى يبين شريط التحميل أوضح
            g2.setColor(new Color(8, 28, 48, 55));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
        }
    }
}