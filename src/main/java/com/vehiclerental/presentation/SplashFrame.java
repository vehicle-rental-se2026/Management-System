package com.vehiclerental.presentation;

import javax.swing.*;
import java.awt.*;

public class SplashFrame extends JFrame {

    private final JProgressBar progressBar;
    private final JLabel loadingLabel;

    public SplashFrame() {

        setTitle("SB Car Rental");
        setSize(900,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(16,76,129));

        // ================= Logo =================

        ImageIcon icon = new ImageIcon(
                getClass().getResource("/images/logo.png"));

        Image image = icon.getImage().getScaledInstance(
                280,
                280,
                Image.SCALE_SMOOTH);

        JLabel logo = new JLabel(new ImageIcon(image));
        logo.setBounds(310,60,280,280);

        panel.add(logo);

        // ================= Title =================

        JLabel title = new JLabel("SB CAR RENTAL");
        title.setFont(new Font("Segoe UI",Font.BOLD,42));
        title.setForeground(Color.WHITE);
        title.setBounds(245,350,500,50);

        panel.add(title);

        JLabel subTitle = new JLabel(
                "Vehicle Rental Management System");

        subTitle.setFont(new Font("Segoe UI",Font.PLAIN,22));
        subTitle.setForeground(Color.WHITE);
        subTitle.setBounds(245,405,420,30);

        panel.add(subTitle);

        // ================= Progress =================

        progressBar = new JProgressBar();

        progressBar.setBounds(225,520,450,25);

        progressBar.setStringPainted(false);

        panel.add(progressBar);

        loadingLabel = new JLabel(
                "Loading... 0%",
                SwingConstants.CENTER);

        loadingLabel.setFont(
                new Font("Segoe UI",Font.PLAIN,18));

        loadingLabel.setForeground(Color.WHITE);

        loadingLabel.setBounds(320,555,250,30);

        panel.add(loadingLabel);

        add(panel);

        startLoading();

        setVisible(true);

    }

    private void startLoading() {

        Timer timer = new Timer(35,null);

        timer.addActionListener(e -> {

            int value = progressBar.getValue() + 1;

            progressBar.setValue(value);

            loadingLabel.setText(
                    "Loading... " + value + "%");

            if (value >= 100) {

                timer.stop();

                dispose();

                new LoginFrame();

            }

        });

        timer.start();

    }

}