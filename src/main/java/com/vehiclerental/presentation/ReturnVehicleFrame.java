package com.vehiclerental.presentation;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.RentalRepository;
import com.vehiclerental.service.RentalService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The ReturnVehicleFrame class provides the user
 * interface for returning rented vehicles.
 */
public class ReturnVehicleFrame extends JFrame {

    private static final String FONT_NAME = "Segoe UI";
    private static final String TITLE_RETURN_VEHICLE = "Return Vehicle";

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color GREEN = new Color(78, 150, 120);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color RED = new Color(214, 80, 80);

    private JComboBox<Rental> rentalComboBox;

    private JButton returnButton;
    private JButton refreshButton;
    private JButton backButton;

    private JLabel messageLabel;
    private JLabel selectedRentalLabel;
    private JLabel activeRentalsCountLabel;

    private final RentalRepository rentalRepository =
            new RentalRepository();

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public ReturnVehicleFrame() {
        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PAGE_BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        loadRentals();
        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle(TITLE_RETURN_VEHICLE);
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

        JLabel title = createStandardLabel(TITLE_RETURN_VEHICLE, Font.BOLD, 32, Color.WHITE, Component.LEFT_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Complete a rental return and update the vehicle status", Font.PLAIN, 15, new Color(220, 235, 245), Component.LEFT_ALIGNMENT);

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

        JLabel title = createStandardLabel("Return Summary", Font.BOLD, 24, NAVY, Component.LEFT_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Select an active rental and complete the return.", Font.PLAIN, 14, TEXT_GRAY, Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(28));

        activeRentalsCountLabel = createStandardLabel("Active Rentals: 0", Font.BOLD, 18, GREEN, Component.LEFT_ALIGNMENT);
        panel.add(activeRentalsCountLabel);
        panel.add(Box.createVerticalStrut(26));

        selectedRentalLabel = createStandardLabel("<html><b>Selected Rental:</b><br>No rental selected yet</html>", Font.PLAIN, 15, TEXT_DARK, Component.LEFT_ALIGNMENT);
        panel.add(selectedRentalLabel);
        panel.add(Box.createVerticalStrut(28));

        panel.add(createRuleLabel("Only active rentals can be returned."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("After return, the rental becomes inactive."));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createRuleLabel("The vehicle becomes available again."));

        panel.add(Box.createVerticalGlue());

        JLabel note = createStandardLabel("SB Car Rental", Font.BOLD, 15, NAVY, Component.LEFT_ALIGNMENT);
        panel.add(note);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = createYBoxPanel();

        JLabel formTitle = createStandardLabel("Return Details", Font.BOLD, 24, TEXT_DARK, Component.LEFT_ALIGNMENT);
        JLabel formSubtitle = createStandardLabel("Choose the rental you want to return.", Font.PLAIN, 14, TEXT_GRAY, Component.LEFT_ALIGNMENT);

        panel.add(formTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(formSubtitle);
        panel.add(Box.createVerticalStrut(32));

        JLabel rentalLabel = createInputLabel("Select Rental");
        panel.add(rentalLabel);
        panel.add(Box.createVerticalStrut(8));

        rentalComboBox = new JComboBox<>();
        rentalComboBox.setMaximumSize(new Dimension(360, 44));
        rentalComboBox.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        rentalComboBox.setBackground(Color.WHITE);
        rentalComboBox.setRenderer(new RentalRenderer());
        rentalComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(rentalComboBox);

        panel.add(Box.createVerticalStrut(22));

        messageLabel = createStandardLabel(" ", Font.BOLD, 14, RED, Component.LEFT_ALIGNMENT);
        panel.add(messageLabel);

        panel.add(Box.createVerticalStrut(18));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(360, 44));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        returnButton = createButton("Return Vehicle", GREEN, 160, 44);
        refreshButton = createButton("Refresh", BLUE, 160, 44);

        buttonsPanel.add(returnButton);
        buttonsPanel.add(refreshButton);

        panel.add(buttonsPanel);

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

    // --- Logic & Events ---

    private void loadRentals() {
        rentalComboBox.removeAllItems();

        int activeCount = 0;

        for (Rental rental : rentalRepository.getAllRentals()) {
            if (rental.isActive()) {
                rentalComboBox.addItem(rental);
                activeCount++;
            }
        }

        activeRentalsCountLabel.setText("Active Rentals: " + activeCount);
        updateSelectedRentalInfo();

        if (activeCount == 0) {
            showMessage("No active rentals found.", false);
        } else {
            messageLabel.setText(" ");
        }
    }

    private void initializeEvents() {
        returnButton.addActionListener(e -> returnVehicle());

        refreshButton.addActionListener(e -> loadRentals());

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });

        rentalComboBox.addActionListener(e -> updateSelectedRentalInfo());
    }

    private void updateSelectedRentalInfo() {
        Rental rental = (Rental) rentalComboBox.getSelectedItem();

        if (rental == null) {
            selectedRentalLabel.setText("<html><b>Selected Rental:</b><br>No rental selected yet</html>");
            return;
        }

        selectedRentalLabel.setText(
                "<html><b>Selected Rental:</b><br>"
                        + rental.getVehicle().getBrand()
                        + " "
                        + rental.getVehicle().getModel()
                        + "<br>Days: "
                        + rental.getRentalDays()
                        + "</html>"
        );
    }

    private void returnVehicle() {
        Rental rental = (Rental) rentalComboBox.getSelectedItem();

        if (rental == null) {
            showMessage("Please select a rental.", false);
            return;
        }

        rentalService.returnVehicle(rental);

        showMessage("Vehicle returned successfully.", true);

        loadRentals();
    }

    private void showMessage(String message, boolean success) {
        messageLabel.setForeground(success ? GREEN : RED);
        messageLabel.setText(message);
    }

    // --- Custom Renderers & UI Panels ---

    private static class RentalRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Rental rental) {
                setText(
                        rental.getVehicle().getId()
                                + " - "
                                + rental.getVehicle().getBrand()
                                + " "
                                + rental.getVehicle().getModel()
                                + " | "
                                + rental.getRentalDays()
                                + " days"
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