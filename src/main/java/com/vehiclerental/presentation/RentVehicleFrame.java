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

public class RentVehicleFrame extends JFrame {

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color GREEN = new Color(78, 150, 120);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color RED = new Color(214, 80, 80);

    private JComboBox<Vehicle> vehicleComboBox;
    private JTextField daysField;

    private JButton rentButton;
    private JButton clearButton;
    private JButton backButton;

    private JLabel messageLabel;
    private JLabel selectedVehicleLabel;

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public RentVehicleFrame() {
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
        setTitle("Rent Vehicle");
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

        JLabel title = new JLabel("Rent Vehicle");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Create a new rental transaction in a simple and organized way");
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

        JLabel title = new JLabel("Rental Summary");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(NAVY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Select a vehicle and enter valid rental days.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(28));

        selectedVehicleLabel = new JLabel("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>");
        selectedVehicleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        selectedVehicleLabel.setForeground(TEXT_DARK);
        selectedVehicleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(selectedVehicleLabel);
        panel.add(Box.createVerticalStrut(28));

        panel.add(createRuleLabel("Rental days must be greater than zero."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("Only available vehicles can be rented."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("After renting, the vehicle is removed from the list."));

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

        JLabel formTitle = new JLabel("Rental Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(TEXT_DARK);
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formSubtitle = new JLabel("Fill the information below to rent a vehicle.");
        formSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formSubtitle.setForeground(TEXT_GRAY);
        formSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(formTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(formSubtitle);
        panel.add(Box.createVerticalStrut(28));

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

        JLabel daysLabel = createInputLabel("Rental Days");
        panel.add(daysLabel);
        panel.add(Box.createVerticalStrut(8));

        daysField = new JTextField();
        daysField.setMaximumSize(new Dimension(360, 44));
        daysField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        daysField.setForeground(TEXT_DARK);
        daysField.setCaretColor(BLUE);
        daysField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 210, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        daysField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(daysField);

        panel.add(Box.createVerticalStrut(18));

        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(RED);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(messageLabel);

        panel.add(Box.createVerticalStrut(16));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(360, 44));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rentButton = createButton("Rent Vehicle", GREEN, 160, 44);
        clearButton = createButton("Clear", BLUE, 160, 44);

        buttonsPanel.add(rentButton);
        buttonsPanel.add(clearButton);

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

        for (Vehicle vehicle : vehicleService.getAvailableVehicles()) {
            vehicleComboBox.addItem(vehicle);
        }

        updateSelectedVehicleInfo();

        if (vehicleComboBox.getItemCount() == 0) {
            showMessage("No available vehicles found.", false);
        }
    }

    private void initializeEvents() {
        rentButton.addActionListener(e -> rentVehicle());

        clearButton.addActionListener(e -> clearFields());

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

    private void rentVehicle() {
        Vehicle vehicle = (Vehicle) vehicleComboBox.getSelectedItem();

        if (vehicle == null) {
            showMessage("Please select a vehicle.", false);
            return;
        }

        String daysText = daysField.getText().trim();

        if (daysText.isEmpty()) {
            showMessage("Please enter rental days.", false);
            return;
        }

        int days;

        try {
            days = Integer.parseInt(daysText);
        } catch (NumberFormatException ex) {
            showMessage("Rental days must be a valid number.", false);
            return;
        }

        if (days <= 0) {
            showMessage("Rental days must be greater than zero.", false);
            return;
        }

        boolean success = rentalService.rentVehicle(vehicle, days);

        if (success) {
            showMessage("Vehicle rented successfully.", true);
            daysField.setText("");
            loadVehicles();
        } else {
            showMessage("Vehicle cannot be rented.", false);
        }
    }

    private void clearFields() {
        if (vehicleComboBox.getItemCount() > 0) {
            vehicleComboBox.setSelectedIndex(0);
        }

        daysField.setText("");
        messageLabel.setText(" ");
        updateSelectedVehicleInfo();
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