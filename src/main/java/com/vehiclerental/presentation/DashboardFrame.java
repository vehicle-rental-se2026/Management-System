package com.vehiclerental.presentation;

import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The DashboardFrame class provides the main user
 * interface for accessing the vehicle rental
 * management system features.
 */
public class DashboardFrame extends JFrame {

    // Constants
    private static final String FONT_NAME = "Segoe UI";
    private static final String TITLE_APP = "SB CAR RENTAL";

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color RED = new Color(214, 80, 80);

    private final transient VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private JButton vehiclesButton;
    private JButton rentButton;
    private JButton returnButton;
    private JButton billingButton;
    private JButton reminderButton;
    private JButton vehicleTypesButton;
    private JButton logoutButton;

    private JLabel availableVehiclesLabel;
    private JLabel rentedVehiclesLabel;
    private JLabel statusLabel;

    public DashboardFrame() {
        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PAGE_BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        initializeEvents();
        refreshDashboard();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("SB Car Rental Dashboard");
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(NAVY, NAVY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 38, 24, 38));

        JPanel titlePanel = createYBoxPanel();

        JLabel title = createStandardLabel(TITLE_APP, Font.BOLD, 34, Color.WHITE, Component.LEFT_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Vehicle Rental Management System", Font.PLAIN, 15, new Color(220, 235, 245), Component.LEFT_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        rightPanel.setOpaque(false);

        JLabel admin = createStandardLabel("Administrator", Font.BOLD, 17, Color.WHITE, Component.CENTER_ALIGNMENT);
        logoutButton = createHeaderButton("Logout");

        rightPanel.add(admin);
        rightPanel.add(logoutButton);

        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createCenter() {
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(PAGE_BG);
        center.setBorder(new EmptyBorder(30, 42, 28, 42));

        JPanel topPanel = createYBoxPanel();

        JLabel welcome = createStandardLabel("Welcome to SB Car Rental Dashboard", Font.BOLD, 30, TEXT_DARK, Component.CENTER_ALIGNMENT);
        JLabel description = createStandardLabel("Manage vehicles, rentals, billing, reminders, and vehicle types.", Font.PLAIN, 16, TEXT_GRAY, Component.CENTER_ALIGNMENT);

        topPanel.add(welcome);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(description);
        topPanel.add(Box.createVerticalStrut(28));

        center.add(topPanel, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 22, 22));
        cardsPanel.setOpaque(false);

        vehiclesButton = createCard("View Vehicles", "Browse all vehicles and availability", new Color(65, 130, 190));
        rentButton = createCard("Rent Vehicle", "Create a new rental transaction", new Color(78, 150, 120));
        returnButton = createCard("Return Vehicle", "Return rented vehicles safely", new Color(210, 145, 80));
        billingButton = createCard("Billing", "Calculate cost and late penalty", new Color(125, 105, 170));
        vehicleTypesButton = createCard("Vehicle Types", "View vehicles by category", new Color(60, 145, 145));
        reminderButton = createCard("Rental Reminder", "Send rental reminder messages", new Color(190, 90, 90));

        cardsPanel.add(vehiclesButton);
        cardsPanel.add(rentButton);
        cardsPanel.add(returnButton);
        cardsPanel.add(billingButton);
        cardsPanel.add(vehicleTypesButton);
        cardsPanel.add(reminderButton);

        center.add(cardsPanel, BorderLayout.CENTER);
        center.add(createInformationPanel(), BorderLayout.SOUTH);

        return center;
    }

    private JPanel createInformationPanel() {
        RoundedPanel panel = new RoundedPanel(24, Color.WHITE);
        panel.setLayout(new GridLayout(1, 3, 20, 0));
        panel.setBorder(new EmptyBorder(20, 24, 20, 24));
        panel.setPreferredSize(new Dimension(1000, 115));

        availableVehiclesLabel = createStandardLabel("0", Font.BOLD, 28, new Color(65, 130, 190), Component.CENTER_ALIGNMENT);
        rentedVehiclesLabel = createStandardLabel("--", Font.BOLD, 28, new Color(210, 145, 80), Component.CENTER_ALIGNMENT);
        statusLabel = createStandardLabel("Online", Font.BOLD, 28, new Color(78, 150, 120), Component.CENTER_ALIGNMENT);

        panel.add(createInfoCard("Available Vehicles", availableVehiclesLabel));
        panel.add(createInfoCard("Rented Vehicles", rentedVehiclesLabel));
        panel.add(createInfoCard("System Status", statusLabel));

        return panel;
    }

    private JPanel createInfoCard(String title, JLabel valueLabel) {
        JPanel panel = createYBoxPanel();

        JLabel titleLabel = createStandardLabel(title, Font.PLAIN, 15, TEXT_GRAY, Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(valueLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel footer = createStandardLabel("© 2026 SB Car Rental - Vehicle Rental Management System", Font.PLAIN, 13, TEXT_GRAY, Component.CENTER_ALIGNMENT);
        footerPanel.add(footer);

        return footerPanel;
    }

    // --- Helper UI Methods ---

    private JPanel createYBoxPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private JLabel createStandardLabel(String text, int fontStyle, int fontSize, Color color, float alignmentX) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, fontStyle, fontSize));
        label.setForeground(color);
        label.setAlignmentX(alignmentX);
        return label;
    }

    private JButton createCard(String title, String description, Color accentColor) {
        CardButton button = new CardButton(accentColor);
        button.setText(
                "<html><center>"
                        + "<span style='font-size:20px;'><b>" + title + "</b></span>"
                        + "<br><br>"
                        + "<span style='font-size:12px; color:#6c757d;'>" + description + "</span>"
                        + "</center></html>"
        );

        button.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        button.setForeground(TEXT_DARK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(20, 20, 20, 20));

        return button;
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(115, 42));
        button.setBackground(RED);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 15));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(RED.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(RED);
            }
        });

        return button;
    }

    // --- Logic & Events ---

    private void refreshDashboard() {
        int availableVehicles = vehicleService.getAvailableVehicles().size();
        availableVehiclesLabel.setText(String.valueOf(availableVehicles));
        rentedVehiclesLabel.setText("--");
        statusLabel.setText("Online");
    }

    private void initializeEvents() {
        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        vehiclesButton.addActionListener(e -> {
            dispose();
            new ViewVehiclesFrame();
        });

        rentButton.addActionListener(e -> {
            dispose();
            new RentVehicleFrame();
        });

        returnButton.addActionListener(e -> {
            dispose();
            new ReturnVehicleFrame();
        });

        billingButton.addActionListener(e -> {
            dispose();
            new BillingFrame();
        });

        vehicleTypesButton.addActionListener(e -> {
            dispose();
            new VehicleTypesFrame();
        });

        reminderButton.addActionListener(e -> {
            dispose();
            new RentalReminderFrame();
        });
    }

    // --- Custom UI Components ---

    private static class CardButton extends JButton {

        private final Color accentColor;
        private boolean hover;

        public CardButton(Color accentColor) {
            this.accentColor = accentColor;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int x = 4;
            int y = 4;
            int width = getWidth() - 10;
            int height = getHeight() - 10;

            g2.setColor(new Color(0, 0, 0, 18));
            g2.fillRoundRect(x + 4, y + 5, width, height, 24, 24);

            g2.setColor(hover ? new Color(250, 252, 255) : CARD_BG);
            g2.fillRoundRect(x, y, width, height, 24, 24);

            g2.setColor(new Color(220, 230, 240));
            g2.drawRoundRect(x, y, width, height, 24, 24);

            g2.setColor(accentColor);
            g2.fillRoundRect(x, y, width, 8, 24, 24);
            g2.fillRect(x, y + 5, width, 5);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private static class RoundedPanel extends JPanel {

        private final int radius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color backgroundColor) {
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(0, 0, 0, 15));
            g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, radius, radius);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, radius, radius);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private static class GradientPanel extends JPanel {

        private final Color startColor;
        private final Color endColor;

        public GradientPanel(Color startColor, Color endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    getWidth(), getHeight(), endColor
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}