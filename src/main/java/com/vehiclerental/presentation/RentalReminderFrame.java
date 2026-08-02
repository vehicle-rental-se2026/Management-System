package com.vehiclerental.presentation;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The RentalReminderFrame class provides the user
 * interface for sending rental reminder
 * notifications.
 */
public class RentalReminderFrame extends JFrame {

    // Constants
    private static final String FONT_NAME = "Segoe UI";
    private static final String TITLE_RENTAL_REMINDER = "Rental Reminder";

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color GREEN = new Color(78, 150, 120);
    private static final Color RED = new Color(214, 80, 80);

    private JComboBox<Vehicle> vehicleComboBox;

    private JButton reminderButton;
    private JButton refreshButton;
    private JButton backButton;

    private JLabel messageLabel;
    private JLabel selectedVehicleLabel;
    private JLabel availableVehiclesLabel;

    private final transient RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public RentalReminderFrame() {
        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PAGE_BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        loadVehicles();
        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle(TITLE_RENTAL_REMINDER);
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(NAVY, NAVY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel titlePanel = createYBoxPanel();

        JLabel title = createStandardLabel(TITLE_RENTAL_REMINDER, Font.BOLD, 32, Color.WHITE, Component.LEFT_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Send reminder messages for vehicle rentals", Font.PLAIN, 15, new Color(220, 235, 245), Component.LEFT_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        backButton = createHeaderButton("Back to Dashboard");
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createCenter() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        centerPanel.setBackground(PAGE_BG);
        centerPanel.setBorder(new EmptyBorder(28, 36, 28, 36));

        centerPanel.add(createSummaryCard());
        centerPanel.add(createFormCard());

        return centerPanel;
    }

    private JPanel createSummaryCard() {
        RoundedPanel panel = new RoundedPanel(24, new Color(236, 245, 252));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 28, 30, 28));

        JLabel title = createStandardLabel("Reminder Summary", Font.BOLD, 24, NAVY, Component.LEFT_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Choose a vehicle and send a reminder message.", Font.PLAIN, 14, TEXT_GRAY, Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(28));

        availableVehiclesLabel = createStandardLabel("Available Vehicles: 0", Font.BOLD, 18, GREEN, Component.LEFT_ALIGNMENT);
        panel.add(availableVehiclesLabel);
        panel.add(Box.createVerticalStrut(26));

        selectedVehicleLabel = createStandardLabel("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>", Font.PLAIN, 15, TEXT_DARK, Component.LEFT_ALIGNMENT);
        panel.add(selectedVehicleLabel);
        panel.add(Box.createVerticalStrut(28));

        panel.add(createRuleLabel("Select a vehicle from the list."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("The reminder is sent using the notification service."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("Use Refresh to reload available vehicles."));

        panel.add(Box.createVerticalGlue());

        JLabel note = createStandardLabel("SB Car Rental", Font.BOLD, 15, NAVY, Component.LEFT_ALIGNMENT);
        panel.add(note);

        return panel;
    }

    private JPanel createFormCard() {
        RoundedPanel card = new RoundedPanel(24, CARD_BG);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        card.add(createFormPanel(), BorderLayout.CENTER);
        card.add(createButtonsPanel(), BorderLayout.SOUTH);

        return card;
    }

    private JPanel createFormPanel() {
        JPanel panel = createYBoxPanel();

        JLabel formTitle = createStandardLabel("Reminder Details", Font.BOLD, 24, TEXT_DARK, Component.LEFT_ALIGNMENT);
        JLabel formSubtitle = createStandardLabel("Select a vehicle and click Send Reminder.", Font.PLAIN, 14, TEXT_GRAY, Component.LEFT_ALIGNMENT);

        panel.add(formTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(formSubtitle);
        panel.add(Box.createVerticalStrut(32));

        JLabel vehicleLabel = createInputLabel("Select Vehicle");
        panel.add(vehicleLabel);
        panel.add(Box.createVerticalStrut(8));

        vehicleComboBox = new JComboBox<>();
        vehicleComboBox.setMaximumSize(new Dimension(360, 44));
        vehicleComboBox.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        vehicleComboBox.setBackground(Color.WHITE);
        vehicleComboBox.setRenderer(new VehicleRenderer());
        vehicleComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(vehicleComboBox);

        panel.add(Box.createVerticalStrut(22));

        messageLabel = createStandardLabel(" ", Font.BOLD, 14, RED, Component.LEFT_ALIGNMENT);
        panel.add(messageLabel);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        reminderButton = createButton("Send Reminder", BLUE, 170, 44);
        refreshButton = createButton("Refresh", GREEN, 120, 44);

        panel.add(reminderButton);
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(248, 249, 250));
        footer.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel label = createStandardLabel("SB Car Rental Management System", Font.PLAIN, 13, TEXT_GRAY, Component.CENTER_ALIGNMENT);
        footer.add(label);

        return footer;
    }

    // --- Helper Methods ---

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

    private JLabel createRuleLabel(String text) {
        return createStandardLabel("• " + text, Font.PLAIN, 14, TEXT_DARK, Component.LEFT_ALIGNMENT);
    }

    private JLabel createInputLabel(String text) {
        return createStandardLabel(text, Font.BOLD, 15, TEXT_DARK, Component.LEFT_ALIGNMENT);
    }

    private JButton createButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(170, 40));
        button.setBackground(NAVY_LIGHT);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(NAVY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(NAVY_LIGHT);
            }
        });

        return button;
    }

    // --- Logic & Events ---

    private void loadVehicles() {
        vehicleComboBox.removeAllItems();

        int count = 0;

        for (Rental rental : rentalService.getActiveRentals()) {
            vehicleComboBox.addItem(rental.getVehicle());
            count++;
        }

        availableVehiclesLabel.setText("Active Rentals: " + count);

        updateSelectedVehicleInfo();

        if (count == 0) {
            showMessage("No active rentals found.", false);
        } else {
            messageLabel.setText(" ");
        }
    }

    private void initializeEvents() {
        reminderButton.addActionListener(e -> sendReminder());

        refreshButton.addActionListener(e -> loadVehicles());

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });

        vehicleComboBox.addActionListener(e -> updateSelectedVehicleInfo());
    }

    private void updateSelectedVehicleInfo() {
        Vehicle vehicle = (Vehicle) vehicleComboBox.getSelectedItem();

        if (vehicle == null) {
            selectedVehicleLabel.setText("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>");
            return;
        }

        selectedVehicleLabel.setText(
                "<html><b>Selected Vehicle:</b><br>"
                        + vehicle.getId()
                        + " - "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + "</html>"
        );
    }

    private void sendReminder() {
        Vehicle vehicle = (Vehicle) vehicleComboBox.getSelectedItem();

        if (vehicle == null) {
            showMessage("Please select a vehicle.", false);
            return;
        }

        rentalService.sendRentalReminder(vehicle);

        showMessage("Reminder sent successfully.", true);
    }

    private void showMessage(String message, boolean success) {
        messageLabel.setForeground(success ? GREEN : RED);
        messageLabel.setText(message);
    }

    // --- Custom Renderers & UI Panels ---

    private static class VehicleRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Vehicle vehicle) {
                setText(
                        vehicle.getId()
                                + " - "
                                + vehicle.getBrand()
                                + " "
                                + vehicle.getModel()
                                + " ("
                                + vehicle.getClass().getSimpleName()
                                + ")"
                );
            }

            setFont(new Font(FONT_NAME, Font.PLAIN, 15));

            return this;
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

            g2.setColor(new Color(0, 0, 0, 14));
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