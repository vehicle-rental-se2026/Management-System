package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * RentVehicleFrame manages the GUI and interaction logic for renting vehicles.
 */
public class RentVehicleFrame extends JFrame {

    // Style Constants
    private static final String FONT_FAMILY = "Segoe UI";
    private static final Color PRIMARY_NAVY = new Color(18, 54, 82);
    private static final Color SECONDARY_NAVY = new Color(35, 92, 132);
    private static final Color BACKGROUND_COLOR = new Color(244, 248, 252);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_MUTED = new Color(108, 117, 125);
    private static final Color SUCCESS_GREEN = new Color(78, 150, 120);
    private static final Color ACCENT_BLUE = new Color(65, 130, 190);
    private static final Color DANGER_RED = new Color(214, 80, 80);

    // Form Controls
    private JComboBox<Vehicle> vehicleSelector;
    private JTextField rentalDaysInput;
    private JButton submitButton;
    private JButton resetButton;
    private JButton backNavigationButton;
    private JLabel feedbackLabel;
    private JLabel vehicleSummaryLabel;

    // Services
    private final VehicleService vehicleService = new VehicleService(new VehicleRepository());
    private final RentalService rentalService = new RentalService(new EmailNotificationService());

    public RentVehicleFrame() {
        configureFrame();
        buildUI();
        bindEvents();
        refreshVehicleList();
        setVisible(true);
    }

    private void configureFrame() {
        setTitle("Rent Vehicle");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buildUI() {
        JPanel rootContainer = new JPanel(new BorderLayout());
        rootContainer.setBackground(BACKGROUND_COLOR);

        rootContainer.add(buildHeaderSection(), BorderLayout.NORTH);
        rootContainer.add(buildMainSection(), BorderLayout.CENTER);
        rootContainer.add(buildFooterSection(), BorderLayout.SOUTH);

        setContentPane(rootContainer);
    }

    private JPanel buildHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, PRIMARY_NAVY, getWidth(), getHeight(), SECONDARY_NAVY));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel titleGroup = new JPanel();
        titleGroup.setOpaque(false);
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));

        JLabel titleText = createCustomLabel("Rent Vehicle", Font.BOLD, 32, Color.WHITE);
        JLabel subtitleText = createCustomLabel("Create a new rental transaction in a simple and organized way", Font.PLAIN, 15, new Color(220, 235, 245));

        titleGroup.add(titleText);
        titleGroup.add(Box.createVerticalStrut(5));
        titleGroup.add(subtitleText);

        backNavigationButton = createActionButton("Back", DANGER_RED, 110, 42);

        headerPanel.add(titleGroup, BorderLayout.WEST);
        headerPanel.add(backNavigationButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel buildMainSection() {
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(BACKGROUND_COLOR);
        centerWrapper.setBorder(new EmptyBorder(32, 42, 32, 42));

        JPanel cardContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 28, 28);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        cardContainer.setOpaque(false);
        cardContainer.setBorder(new EmptyBorder(30, 30, 30, 30));
        cardContainer.setPreferredSize(new Dimension(820, 410));

        JPanel splitLayout = new JPanel(new GridLayout(1, 2, 28, 0));
        splitLayout.setOpaque(false);

        splitLayout.add(buildSummarySubPanel());
        splitLayout.add(buildInputSubPanel());

        cardContainer.add(splitLayout, BorderLayout.CENTER);
        centerWrapper.add(cardContainer);

        return centerWrapper;
    }

    private JPanel buildSummarySubPanel() {
        JPanel summaryBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(236, 245, 252));
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        summaryBox.setOpaque(false);
        summaryBox.setLayout(new BoxLayout(summaryBox, BoxLayout.Y_AXIS));
        summaryBox.setBorder(new EmptyBorder(30, 28, 30, 28));

        summaryBox.add(createCustomLabel("Rental Summary", Font.BOLD, 24, PRIMARY_NAVY));
        summaryBox.add(Box.createVerticalStrut(8));
        summaryBox.add(createCustomLabel("Select a vehicle and enter valid rental days.", Font.PLAIN, 14, TEXT_MUTED));
        summaryBox.add(Box.createVerticalStrut(28));

        vehicleSummaryLabel = createCustomLabel("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>", Font.PLAIN, 15, TEXT_PRIMARY);
        summaryBox.add(vehicleSummaryLabel);
        summaryBox.add(Box.createVerticalStrut(28));

        summaryBox.add(createCustomLabel("• Rental days must be greater than zero.", Font.PLAIN, 14, TEXT_PRIMARY));
        summaryBox.add(Box.createVerticalStrut(12));
        summaryBox.add(createCustomLabel("• Only available vehicles can be rented.", Font.PLAIN, 14, TEXT_PRIMARY));
        summaryBox.add(Box.createVerticalStrut(12));
        summaryBox.add(createCustomLabel("• After renting, the vehicle is removed from the list.", Font.PLAIN, 14, TEXT_PRIMARY));

        summaryBox.add(Box.createVerticalGlue());
        summaryBox.add(createCustomLabel("SB Car Rental", Font.BOLD, 15, PRIMARY_NAVY));

        return summaryBox;
    }

    private JPanel buildInputSubPanel() {
        JPanel inputForm = new JPanel();
        inputForm.setOpaque(false);
        inputForm.setLayout(new BoxLayout(inputForm, BoxLayout.Y_AXIS));

        inputForm.add(createCustomLabel("Rental Details", Font.BOLD, 24, TEXT_PRIMARY));
        inputForm.add(Box.createVerticalStrut(8));
        inputForm.add(createCustomLabel("Fill the information below to rent a vehicle.", Font.PLAIN, 14, TEXT_MUTED));
        inputForm.add(Box.createVerticalStrut(28));

        inputForm.add(createCustomLabel("Select Vehicle", Font.BOLD, 15, TEXT_PRIMARY));
        inputForm.add(Box.createVerticalStrut(8));

        vehicleSelector = new JComboBox<>();
        vehicleSelector.setMaximumSize(new Dimension(360, 44));
        vehicleSelector.setFont(new Font(FONT_FAMILY, Font.PLAIN, 15));
        vehicleSelector.setBackground(Color.WHITE);
        vehicleSelector.setAlignmentX(Component.LEFT_ALIGNMENT);

        vehicleSelector.setRenderer((list, val, idx, isSel, cellHasFocus) -> {
            JLabel cell = new JLabel();
            cell.setOpaque(true);
            cell.setFont(new Font(FONT_FAMILY, Font.PLAIN, 15));
            cell.setBackground(isSel ? list.getSelectionBackground() : list.getBackground());
            cell.setForeground(isSel ? list.getSelectionForeground() : list.getForeground());

            if (val != null) {
                cell.setText(String.format("%s - %s %s (%s)",
                        val.getId(),
                        val.getBrand(),
                        val.getModel(),
                        val.getClass().getSimpleName()));
            }

            return cell;
        });

        inputForm.add(vehicleSelector);
        inputForm.add(Box.createVerticalStrut(22));

        inputForm.add(createCustomLabel("Rental Days", Font.BOLD, 15, TEXT_PRIMARY));
        inputForm.add(Box.createVerticalStrut(8));

        rentalDaysInput = new JTextField();
        rentalDaysInput.setMaximumSize(new Dimension(360, 44));
        rentalDaysInput.setFont(new Font(FONT_FAMILY, Font.PLAIN, 15));
        rentalDaysInput.setForeground(TEXT_PRIMARY);
        rentalDaysInput.setCaretColor(ACCENT_BLUE);
        rentalDaysInput.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 210, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        rentalDaysInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputForm.add(rentalDaysInput);
        inputForm.add(Box.createVerticalStrut(18));

        feedbackLabel = createCustomLabel(" ", Font.BOLD, 14, DANGER_RED);
        inputForm.add(feedbackLabel);
        inputForm.add(Box.createVerticalStrut(16));

        JPanel actionGroup = new JPanel(new GridLayout(1, 2, 14, 0));
        actionGroup.setOpaque(false);
        actionGroup.setMaximumSize(new Dimension(360, 44));
        actionGroup.setAlignmentX(Component.LEFT_ALIGNMENT);

        submitButton = createActionButton("Rent Vehicle", SUCCESS_GREEN, 160, 44);
        resetButton = createActionButton("Clear", ACCENT_BLUE, 160, 44);

        actionGroup.add(submitButton);
        actionGroup.add(resetButton);

        inputForm.add(actionGroup);

        return inputForm;
    }

    private JPanel buildFooterSection() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel copyrightText = createCustomLabel("SB Car Rental Management System", Font.PLAIN, 13, TEXT_MUTED);
        copyrightText.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(copyrightText);

        return footerPanel;
    }

    // --- Helper UI Builders ---

    private JLabel createCustomLabel(String text, int style, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_FAMILY, style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createActionButton(String text, Color baseColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font(FONT_FAMILY, Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        return btn;
    }

    // --- Business Logic & Handlers ---

    private void refreshVehicleList() {
        vehicleSelector.removeAllItems();
        for (Vehicle v : vehicleService.getAvailableVehicles()) {
            vehicleSelector.addItem(v);
        }

        syncSelectedVehicleDisplay();

        if (vehicleSelector.getItemCount() == 0) {
            displayStatus("No available vehicles found.", false);
        }
    }

    private void bindEvents() {
        submitButton.addActionListener(e -> processRental());
        resetButton.addActionListener(e -> resetFormFields());
        backNavigationButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        vehicleSelector.addActionListener(e -> syncSelectedVehicleDisplay());
    }

    private void syncSelectedVehicleDisplay() {
        Vehicle target = (Vehicle) vehicleSelector.getSelectedItem();
        if (target == null) {
            vehicleSummaryLabel.setText("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>");
        } else {
            vehicleSummaryLabel.setText(String.format("<html><b>Selected Vehicle:</b><br>%s - %s %s</html>",
                    target.getId(), target.getBrand(), target.getModel()));
        }
    }

    private void processRental() {
        Vehicle targetVehicle = (Vehicle) vehicleSelector.getSelectedItem();
        if (targetVehicle == null) {
            displayStatus("Please select a vehicle.", false);
            return;
        }

        String rawDays = rentalDaysInput.getText().trim();
        if (rawDays.isEmpty()) {
            displayStatus("Please enter rental days.", false);
            return;
        }

        try {
            int duration = Integer.parseInt(rawDays);
            if (duration <= 0) {
                displayStatus("Rental days must be greater than zero.", false);
                return;
            }

            if (rentalService.rentVehicle(targetVehicle, duration)) {
                displayStatus("Vehicle rented successfully.", true);
                rentalDaysInput.setText("");
                refreshVehicleList();
            } else {
                displayStatus("Vehicle cannot be rented.", false);
            }
        } catch (NumberFormatException ex) {
            displayStatus("Rental days must be a valid number.", false);
        }
    }

    private void resetFormFields() {
        if (vehicleSelector.getItemCount() > 0) {
            vehicleSelector.setSelectedIndex(0);
        }
        rentalDaysInput.setText("");
        feedbackLabel.setText(" ");
        syncSelectedVehicleDisplay();
    }

    private void displayStatus(String message, boolean isSuccess) {
        feedbackLabel.setForeground(isSuccess ? SUCCESS_GREEN : DANGER_RED);
        feedbackLabel.setText(message);
    }
}