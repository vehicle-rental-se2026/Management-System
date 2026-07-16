package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RentalReminderFrame extends JFrame {

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

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private final RentalService rentalService =
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
        setTitle("Rental Reminder");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(NAVY, NAVY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Rental Reminder");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Send reminder messages for vehicle rentals");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(220, 235, 245));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        backButton = createButton("Back", RED, 110, 42);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createCenter() {
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(PAGE_BG);
        background.setBorder(new EmptyBorder(32, 42, 32, 42));

        RoundedPanel mainCard = new RoundedPanel(28, CARD_BG);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainCard.setPreferredSize(new Dimension(820, 410));

        JPanel content = new JPanel(new GridLayout(1, 2, 28, 0));
        content.setOpaque(false);

        content.add(createInfoPanel());
        content.add(createFormPanel());

        mainCard.add(content, BorderLayout.CENTER);
        background.add(mainCard);

        return background;
    }

    private JPanel createInfoPanel() {
        RoundedPanel panel = new RoundedPanel(24, new Color(236, 245, 252));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 28, 30, 28));

        JLabel title = new JLabel("Reminder Summary");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(NAVY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Choose a vehicle and send a reminder message.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(28));

        availableVehiclesLabel = new JLabel("Available Vehicles: 0");
        availableVehiclesLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        availableVehiclesLabel.setForeground(GREEN);
        availableVehiclesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(availableVehiclesLabel);
        panel.add(Box.createVerticalStrut(26));

        selectedVehicleLabel = new JLabel("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>");
        selectedVehicleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        selectedVehicleLabel.setForeground(TEXT_DARK);
        selectedVehicleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(selectedVehicleLabel);
        panel.add(Box.createVerticalStrut(28));

        panel.add(createRuleLabel("Select a vehicle from the list."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("The reminder is sent using the notification service."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("Use Refresh to reload available vehicles."));

        panel.add(Box.createVerticalGlue());

        JLabel note = new JLabel("SB Car Rental");
        note.setFont(new Font("Segoe UI", Font.BOLD, 15));
        note.setForeground(NAVY);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(note);

        return panel;
    }

    private JLabel createRuleLabel(String text) {
        JLabel label = new JLabel("• " + text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel formTitle = new JLabel("Reminder Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(TEXT_DARK);
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formSubtitle = new JLabel("Select a vehicle and click Send Reminder.");
        formSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formSubtitle.setForeground(TEXT_GRAY);
        formSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(formTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(formSubtitle);
        panel.add(Box.createVerticalStrut(32));

        JLabel vehicleLabel = createInputLabel("Select Vehicle");
        panel.add(vehicleLabel);
        panel.add(Box.createVerticalStrut(8));

        vehicleComboBox = new JComboBox<>();
        vehicleComboBox.setMaximumSize(new Dimension(360, 44));
        vehicleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        vehicleComboBox.setBackground(Color.WHITE);
        vehicleComboBox.setRenderer(new VehicleRenderer());
        vehicleComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(vehicleComboBox);

        panel.add(Box.createVerticalStrut(22));

        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(RED);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(messageLabel);

        panel.add(Box.createVerticalStrut(18));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(360, 44));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        reminderButton = createButton("Send Reminder", GREEN, 160, 44);
        refreshButton = createButton("Refresh", BLUE, 160, 44);

        buttonsPanel.add(reminderButton);
        buttonsPanel.add(refreshButton);

        panel.add(buttonsPanel);

        return panel;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
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

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(248, 249, 250));
        footer.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel label = new JLabel("SB Car Rental Management System");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_GRAY);

        footer.add(label);
        return footer;
    }

    private void loadVehicles() {
        vehicleComboBox.removeAllItems();

        int count = 0;

        for (Vehicle vehicle : vehicleService.getAvailableVehicles()) {
            vehicleComboBox.addItem(vehicle);
            count++;
        }

        availableVehiclesLabel.setText("Available Vehicles: " + count);
        updateSelectedVehicleInfo();

        if (count == 0) {
            showMessage("No available vehicles found.", false);
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

            setFont(new Font("Segoe UI", Font.PLAIN, 15));
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