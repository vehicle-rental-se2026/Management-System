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
import java.util.List;

/**
 * ReturnVehicleFrame handles the check-in and status restoration for rented vehicles.
 */
public class ReturnVehicleFrame extends JFrame {

    // Palette & Visual Configuration
    private static final String APP_FONT_NAME = "Segoe UI";
    private static final String FOOTER_NOTE_TEXT = "SB Car Rental Management System";

    private static final Color PRIMARY_NAVY_DARK = new Color(18, 54, 82);
    private static final Color PRIMARY_NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color BACKGROUND_BASE = new Color(244, 248, 252);
    private static final Color TEXT_DARK_BODY = new Color(33, 37, 41);
    private static final Color TEXT_MUTED_LABEL = new Color(108, 117, 125);
    private static final Color STATE_SUCCESS_GREEN = new Color(78, 150, 120);
    private static final Color STATE_INFO_BLUE = new Color(65, 130, 190);
    private static final Color STATE_DANGER_RED = new Color(214, 80, 80);

    // Form Controls & Labels
    private JComboBox<Rental> rentalSelectorCombo;
    private JButton executeReturnButton;
    private JButton syncDataButton;
    private JButton backNavigationButton;
    private JLabel feedbackNoticeLabel;
    private JLabel selectedSummaryLabel;
    private JLabel activeRentalsCountLabel;

    // Domain Services
    private final RentalRepository rentalDataStore = new RentalRepository();
    private final RentalService vehicleRentalManager = new RentalService(new EmailNotificationService());

    public ReturnVehicleFrame() {
        initWindowFrameProperties();
        assembleFrameComponents();
        bindViewListeners();
        fetchActiveRentalsList();
        setVisible(true);
    }

    private void initWindowFrameProperties() {
        setTitle("Return Vehicle");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void assembleFrameComponents() {
        JPanel rootContainer = new JPanel(new BorderLayout());
        rootContainer.setBackground(BACKGROUND_BASE);

        rootContainer.add(buildTopHeaderBar(), BorderLayout.NORTH);
        rootContainer.add(buildMainWorkspace(), BorderLayout.CENTER);
        rootContainer.add(buildBottomFooterBar(), BorderLayout.SOUTH);

        setContentPane(rootContainer);
    }

    private JPanel buildTopHeaderBar() {
        JPanel headerHolder = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, PRIMARY_NAVY_DARK, getWidth(), getHeight(), PRIMARY_NAVY_LIGHT));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerHolder.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel headlineGroup = new JPanel();
        headlineGroup.setOpaque(false);
        headlineGroup.setLayout(new BoxLayout(headlineGroup, BoxLayout.Y_AXIS));

        JLabel mainHeading = generateTextComponent("Return Vehicle", Font.BOLD, 32, Color.WHITE);
        JLabel subHeading = generateTextComponent("Complete a rental return and update the vehicle status", Font.PLAIN, 15, new Color(220, 235, 245));

        headlineGroup.add(mainHeading);
        headlineGroup.add(Box.createVerticalStrut(5));
        headlineGroup.add(subHeading);

        backNavigationButton = generateActionButton("Back", STATE_DANGER_RED, 110, 42);

        headerHolder.add(headlineGroup, BorderLayout.WEST);
        headerHolder.add(backNavigationButton, BorderLayout.EAST);

        return headerHolder;
    }

    private JPanel buildMainWorkspace() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BACKGROUND_BASE);
        wrapper.setBorder(new EmptyBorder(32, 42, 32, 42));

        JPanel innerCard = new JPanel(new BorderLayout()) {
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
        innerCard.setOpaque(false);
        innerCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        innerCard.setPreferredSize(new Dimension(820, 410));

        JPanel dualColumns = new JPanel(new GridLayout(1, 2, 28, 0));
        dualColumns.setOpaque(false);

        dualColumns.add(buildLeftOverviewSection());
        dualColumns.add(buildRightFormSection());

        innerCard.add(dualColumns, BorderLayout.CENTER);
        wrapper.add(innerCard);

        return wrapper;
    }

    private JPanel buildLeftOverviewSection() {
        JPanel panel = new JPanel() {
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
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 28, 30, 28));

        panel.add(generateTextComponent("Return Summary", Font.BOLD, 24, PRIMARY_NAVY_DARK));
        panel.add(Box.createVerticalStrut(8));
        panel.add(generateTextComponent("Select an active rental and complete the return.", Font.PLAIN, 14, TEXT_MUTED_LABEL));
        panel.add(Box.createVerticalStrut(28));

        activeRentalsCountLabel = generateTextComponent("Active Rentals: 0", Font.BOLD, 18, STATE_SUCCESS_GREEN);
        panel.add(activeRentalsCountLabel);
        panel.add(Box.createVerticalStrut(26));

        selectedSummaryLabel = generateTextComponent("<html><b>Selected Rental:</b><br>No rental selected yet</html>", Font.PLAIN, 15, TEXT_DARK_BODY);
        panel.add(selectedSummaryLabel);
        panel.add(Box.createVerticalStrut(28));

        panel.add(generateTextComponent("• Only active rentals can be returned.", Font.PLAIN, 14, TEXT_DARK_BODY));
        panel.add(Box.createVerticalStrut(12));
        panel.add(generateTextComponent("• After return, the rental becomes inactive.", Font.PLAIN, 14, TEXT_DARK_BODY));
        panel.add(Box.createVerticalStrut(12));
        panel.add(generateTextComponent("• The vehicle becomes available again.", Font.PLAIN, 14, TEXT_DARK_BODY));
        panel.add(Box.createVerticalGlue());

        panel.add(generateTextComponent("SB Car Rental", Font.BOLD, 15, PRIMARY_NAVY_DARK));

        return panel;
    }

    private JPanel buildRightFormSection() {
        JPanel formLayout = new JPanel();
        formLayout.setOpaque(false);
        formLayout.setLayout(new BoxLayout(formLayout, BoxLayout.Y_AXIS));

        formLayout.add(generateTextComponent("Return Details", Font.BOLD, 24, TEXT_DARK_BODY));
        formLayout.add(Box.createVerticalStrut(8));
        formLayout.add(generateTextComponent("Choose the rental you want to return.", Font.PLAIN, 14, TEXT_MUTED_LABEL));
        formLayout.add(Box.createVerticalStrut(32));

        formLayout.add(generateTextComponent("Select Rental", Font.BOLD, 15, TEXT_DARK_BODY));
        formLayout.add(Box.createVerticalStrut(8));

        rentalSelectorCombo = new JComboBox<>();
        rentalSelectorCombo.setMaximumSize(new Dimension(360, 44));
        rentalSelectorCombo.setFont(new Font(APP_FONT_NAME, Font.PLAIN, 15));
        rentalSelectorCombo.setBackground(Color.WHITE);
        rentalSelectorCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        rentalSelectorCombo.setRenderer((list, item, index, isSelected, cellHasFocus) -> {
            DefaultListCellRenderer renderer = new DefaultListCellRenderer();
            Component comp = renderer.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
            if (item instanceof Rental) {
                Rental r = (Rental) item;
                String display = String.format("%s - %s %s | %d days",
                        r.getVehicle().getId(),
                        r.getVehicle().getBrand(),
                        r.getVehicle().getModel(),
                        r.getRentalDays());
                ((JLabel) comp).setText(display);
            }
            comp.setFont(new Font(APP_FONT_NAME, Font.PLAIN, 15));
            return comp;
        });

        formLayout.add(rentalSelectorCombo);
        formLayout.add(Box.createVerticalStrut(22));

        feedbackNoticeLabel = generateTextComponent(" ", Font.BOLD, 14, STATE_DANGER_RED);
        formLayout.add(feedbackNoticeLabel);
        formLayout.add(Box.createVerticalStrut(18));

        JPanel actionsRow = new JPanel(new GridLayout(1, 2, 14, 0));
        actionsRow.setOpaque(false);
        actionsRow.setMaximumSize(new Dimension(360, 44));
        actionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        executeReturnButton = generateActionButton("Return Vehicle", STATE_SUCCESS_GREEN, 160, 44);
        syncDataButton = generateActionButton("Refresh", STATE_INFO_BLUE, 160, 44);

        actionsRow.add(executeReturnButton);
        actionsRow.add(syncDataButton);

        formLayout.add(actionsRow);

        return formLayout;
    }

    private JPanel buildBottomFooterBar() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(248, 249, 250));
        footer.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel footerCaption = generateTextComponent(FOOTER_NOTE_TEXT, Font.PLAIN, 13, TEXT_MUTED_LABEL);
        footerCaption.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.add(footerCaption);

        return footer;
    }

    // --- UI Helpers ---

    private JLabel generateTextComponent(String content, int style, int size, Color color) {
        JLabel label = new JLabel(content);
        label.setFont(new Font(APP_FONT_NAME, style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton generateActionButton(String title, Color bgColor, int width, int height) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(APP_FONT_NAME, Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // --- Data & Event Actions ---

    private void fetchActiveRentalsList() {
        rentalSelectorCombo.removeAllItems();
        List<Rental> allRentals = rentalDataStore.getAllRentals();
        long openCount = allRentals.stream().filter(Rental::isActive).peek(rentalSelectorCombo::addItem).count();

        activeRentalsCountLabel.setText("Active Rentals: " + openCount);
        updateSelectedRentalSummary();

        if (openCount == 0) {
            displayStatusMessage("No active rentals found.", false);
        } else {
            feedbackNoticeLabel.setText(" ");
        }
    }

    private void bindViewListeners() {
        executeReturnButton.addActionListener(e -> performReturnAction());
        syncDataButton.addActionListener(e -> fetchActiveRentalsList());
        backNavigationButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        rentalSelectorCombo.addActionListener(e -> updateSelectedRentalSummary());
    }

    private void updateSelectedRentalSummary() {
        Rental selected = (Rental) rentalSelectorCombo.getSelectedItem();
        if (selected == null) {
            selectedSummaryLabel.setText("<html><b>Selected Rental:</b><br>No rental selected yet</html>");
        } else {
            selectedSummaryLabel.setText(String.format(
                    "<html><b>Selected Rental:</b><br>%s %s<br>Days: %d</html>",
                    selected.getVehicle().getBrand(),
                    selected.getVehicle().getModel(),
                    selected.getRentalDays()
            ));
        }
    }

    private void performReturnAction() {
        Rental selected = (Rental) rentalSelectorCombo.getSelectedItem();
        if (selected == null) {
            displayStatusMessage("Please select a rental.", false);
            return;
        }

        vehicleRentalManager.returnVehicle(selected);
        displayStatusMessage("Vehicle returned successfully.", true);
        fetchActiveRentalsList();
    }

    private void displayStatusMessage(String message, boolean isSuccess) {
        feedbackNoticeLabel.setForeground(isSuccess ? STATE_SUCCESS_GREEN : STATE_DANGER_RED);
        feedbackNoticeLabel.setText(message);
    }
}