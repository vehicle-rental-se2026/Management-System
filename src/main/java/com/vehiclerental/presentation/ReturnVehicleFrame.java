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
 * ReturnVehicleFrame handles the check-in and status restoration for rented vehicles.
 */
public class ReturnVehicleFrame extends JFrame {

    // Visual Palette & Constants
    private static final String FONT_FAMILY_NAME = "Segoe UI";
    private static final String APP_COPYRIGHT_TEXT = "SB Car Rental Management System";

    private static final Color HEADER_NAVY_START = new Color(18, 54, 82);
    private static final Color HEADER_NAVY_END = new Color(35, 92, 132);
    private static final Color VIEW_BG_COLOR = new Color(244, 248, 252);
    private static final Color CONTENT_DARK_TEXT = new Color(33, 37, 41);
    private static final Color MUTED_SUB_TEXT = new Color(108, 117, 125);
    private static final Color COLOR_CONFIRM_GREEN = new Color(78, 150, 120);
    private static final Color COLOR_INFO_BLUE = new Color(65, 130, 190);
    private static final Color COLOR_ALERT_RED = new Color(214, 80, 80);

    // Interactive UI Elements
    private JComboBox<Rental> activeRentalsDropdown;
    private JButton confirmReturnBtn;
    private JButton refreshDataBtn;
    private JButton navigateHomeBtn;

    private JLabel statusNotificationLbl;
    private JLabel currentRentalDetailsLbl;
    private JLabel totalActiveCountLbl;

    // Repositories & Services
    private final RentalRepository rentalDataRepository = new RentalRepository();
    private final RentalService rentalExecutionService = new RentalService(new EmailNotificationService());

    public ReturnVehicleFrame() {
        configureMainFrameSettings();
        buildApplicationLayout();
        attachEventListeners();
        loadActiveRentalsData();
        setVisible(true);
    }

    private void configureMainFrameSettings() {
        setTitle("Return Vehicle");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buildApplicationLayout() {
        JPanel outerCanvas = new JPanel(new BorderLayout());
        outerCanvas.setBackground(VIEW_BG_COLOR);

        outerCanvas.add(constructHeaderSection(), BorderLayout.NORTH);
        outerCanvas.add(constructBodySection(), BorderLayout.CENTER);
        outerCanvas.add(constructFooterSection(), BorderLayout.SOUTH);

        setContentPane(outerCanvas);
    }

    private JPanel constructHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, HEADER_NAVY_START, getWidth(), getHeight(), HEADER_NAVY_END));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel titleStack = new JPanel();
        titleStack.setOpaque(false);
        titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));

        JLabel pageHeading = createCustomText("Return Vehicle", Font.BOLD, 32, Color.WHITE);
        JLabel pageSubHeading = createCustomText("Complete a rental return and update the vehicle status", Font.PLAIN, 15, new Color(220, 235, 245));

        titleStack.add(pageHeading);
        titleStack.add(Box.createVerticalStrut(5));
        titleStack.add(pageSubHeading);

        navigateHomeBtn = buildColoredButton("Back", COLOR_ALERT_RED, 110, 42);

        headerPanel.add(titleStack, BorderLayout.WEST);
        headerPanel.add(navigateHomeBtn, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel constructBodySection() {
        JPanel centerGrid = new JPanel(new GridBagLayout());
        centerGrid.setBackground(VIEW_BG_COLOR);
        centerGrid.setBorder(new EmptyBorder(32, 42, 32, 42));

        JPanel elevatedCard = new JPanel(new BorderLayout()) {
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
        elevatedCard.setOpaque(false);
        elevatedCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        elevatedCard.setPreferredSize(new Dimension(820, 410));

        JPanel splitColumns = new JPanel(new GridLayout(1, 2, 28, 0));
        splitColumns.setOpaque(false);

        splitColumns.add(constructOverviewPanel());
        splitColumns.add(constructFormPanel());

        elevatedCard.add(splitColumns, BorderLayout.CENTER);
        centerGrid.add(elevatedCard);

        return centerGrid;
    }

    private JPanel constructOverviewPanel() {
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

        summaryBox.add(createCustomText("Return Summary", Font.BOLD, 24, HEADER_NAVY_START));
        summaryBox.add(Box.createVerticalStrut(8));
        summaryBox.add(createCustomText("Select an active rental and complete the return.", Font.PLAIN, 14, MUTED_SUB_TEXT));
        summaryBox.add(Box.createVerticalStrut(28));

        totalActiveCountLbl = createCustomText("Active Rentals: 0", Font.BOLD, 18, COLOR_CONFIRM_GREEN);
        summaryBox.add(totalActiveCountLbl);
        summaryBox.add(Box.createVerticalStrut(26));

        currentRentalDetailsLbl = createCustomText("<html><b>Selected Rental:</b><br>No rental selected yet</html>", Font.PLAIN, 15, CONTENT_DARK_TEXT);
        summaryBox.add(currentRentalDetailsLbl);
        summaryBox.add(Box.createVerticalStrut(28));

        summaryBox.add(createCustomText("• Only active rentals can be returned.", Font.PLAIN, 14, CONTENT_DARK_TEXT));
        summaryBox.add(Box.createVerticalStrut(12));
        summaryBox.add(createCustomText("• After return, the rental becomes inactive.", Font.PLAIN, 14, CONTENT_DARK_TEXT));
        summaryBox.add(Box.createVerticalStrut(12));
        summaryBox.add(createCustomText("• The vehicle becomes available again.", Font.PLAIN, 14, CONTENT_DARK_TEXT));

        summaryBox.add(Box.createVerticalGlue());
        summaryBox.add(createCustomText("SB Car Rental", Font.BOLD, 15, HEADER_NAVY_START));

        return summaryBox;
    }

    private JPanel constructFormPanel() {
        JPanel formStack = new JPanel();
        formStack.setOpaque(false);
        formStack.setLayout(new BoxLayout(formStack, BoxLayout.Y_AXIS));

        formStack.add(createCustomText("Return Details", Font.BOLD, 24, CONTENT_DARK_TEXT));
        formStack.add(Box.createVerticalStrut(8));
        formStack.add(createCustomText("Choose the rental you want to return.", Font.PLAIN, 14, MUTED_SUB_TEXT));
        formStack.add(Box.createVerticalStrut(32));

        formStack.add(createCustomText("Select Rental", Font.BOLD, 15, CONTENT_DARK_TEXT));
        formStack.add(Box.createVerticalStrut(8));

        activeRentalsDropdown = new JComboBox<>();
        activeRentalsDropdown.setMaximumSize(new Dimension(360, 44));
        activeRentalsDropdown.setFont(new Font(FONT_FAMILY_NAME, Font.PLAIN, 15));
        activeRentalsDropdown.setBackground(Color.WHITE);
        activeRentalsDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);

        activeRentalsDropdown.setRenderer((list, val, idx, isSel, cellHasFocus) -> {
            JLabel rowCell = new JLabel();
            rowCell.setOpaque(true);
            rowCell.setFont(new Font(FONT_FAMILY_NAME, Font.PLAIN, 15));
            rowCell.setBackground(isSel ? list.getSelectionBackground() : list.getBackground());
            rowCell.setForeground(isSel ? list.getSelectionForeground() : list.getForeground());

            if (val != null) {
                rowCell.setText(String.format("%s - %s %s | %d days",
                        val.getVehicle().getId(),
                        val.getVehicle().getBrand(),
                        val.getVehicle().getModel(),
                        val.getRentalDays()));
            }
            return rowCell;
        });

        formStack.add(activeRentalsDropdown);
        formStack.add(Box.createVerticalStrut(22));

        statusNotificationLbl = createCustomText(" ", Font.BOLD, 14, COLOR_ALERT_RED);
        formStack.add(statusNotificationLbl);
        formStack.add(Box.createVerticalStrut(18));

        JPanel buttonBar = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonBar.setOpaque(false);
        buttonBar.setMaximumSize(new Dimension(360, 44));
        buttonBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        confirmReturnBtn = buildColoredButton("Return Vehicle", COLOR_CONFIRM_GREEN, 160, 44);
        refreshDataBtn = buildColoredButton("Refresh", COLOR_INFO_BLUE, 160, 44);

        buttonBar.add(confirmReturnBtn);
        buttonBar.add(refreshDataBtn);

        formStack.add(buttonBar);

        return formStack;
    }

    private JPanel constructFooterSection() {
        JPanel footerBar = new JPanel();
        footerBar.setBackground(new Color(248, 249, 250));
        footerBar.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel brandNotice = createCustomText(APP_COPYRIGHT_TEXT, Font.PLAIN, 13, MUTED_SUB_TEXT);
        brandNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerBar.add(brandNotice);

        return footerBar;
    }

    // --- Control Generator Helpers ---

    private JLabel createCustomText(String labelContent, int fontStyle, int fontSize, Color textFx) {
        JLabel lbl = new JLabel(labelContent);
        lbl.setFont(new Font(FONT_FAMILY_NAME, fontStyle, fontSize));
        lbl.setForeground(textFx);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton buildColoredButton(String btnText, Color fillTone, int w, int h) {
        JButton btn = new JButton(btnText);
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(fillTone);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font(FONT_FAMILY_NAME, Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(fillTone.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(fillTone);
            }
        });

        return btn;
    }

    // --- Domain Logic & Event Handlers ---

    private void loadActiveRentalsData() {
        activeRentalsDropdown.removeAllItems();

        int openCount = 0;
        for (Rental item : rentalDataRepository.getAllRentals()) {
            if (item.isActive()) {
                activeRentalsDropdown.addItem(item);
                openCount++;
            }
        }

        totalActiveCountLbl.setText("Active Rentals: " + openCount);
        refreshRentalSelectionDetails();

        if (openCount == 0) {
            updateStatusAlert("No active rentals found.", false);
        } else {
            statusNotificationLbl.setText(" ");
        }
    }

    private void attachEventListeners() {
        confirmReturnBtn.addActionListener(e -> processVehicleReturn());
        refreshDataBtn.addActionListener(e -> loadActiveRentalsData());
        navigateHomeBtn.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        activeRentalsDropdown.addActionListener(e -> refreshRentalSelectionDetails());
    }

    private void refreshRentalSelectionDetails() {
        Rental chosenRental = (Rental) activeRentalsDropdown.getSelectedItem();

        if (chosenRental == null) {
            currentRentalDetailsLbl.setText("<html><b>Selected Rental:</b><br>No rental selected yet</html>");
        } else {
            currentRentalDetailsLbl.setText(String.format(
                    "<html><b>Selected Rental:</b><br>%s %s<br>Days: %d</html>",
                    chosenRental.getVehicle().getBrand(),
                    chosenRental.getVehicle().getModel(),
                    chosenRental.getRentalDays()
            ));
        }
    }

    private void processVehicleReturn() {
        Rental selectedTarget = (Rental) activeRentalsDropdown.getSelectedItem();

        if (selectedTarget == null) {
            updateStatusAlert("Please select a rental.", false);
            return;
        }

        rentalExecutionService.returnVehicle(selectedTarget);
        updateStatusAlert("Vehicle returned successfully.", true);
        loadActiveRentalsData();
    }

    private void updateStatusAlert(String message, boolean isSuccess) {
        statusNotificationLbl.setForeground(isSuccess ? COLOR_CONFIRM_GREEN : COLOR_ALERT_RED);
        statusNotificationLbl.setText(message);
    }
}