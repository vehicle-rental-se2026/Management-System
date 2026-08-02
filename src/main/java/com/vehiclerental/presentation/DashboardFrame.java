package com.vehiclerental.presentation;

import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * DashboardFrame is the primary navigation hub for the rental system.
 */
public class DashboardFrame extends JFrame {

    // Global Style Constants
    private static final String MAIN_FONT_FAMILY = "Segoe UI";
    private static final String BRAND_TITLE = "SB CAR RENTAL";

    private static final Color DEEP_PRIMARY_NAVY = new Color(18, 54, 82);
    private static final Color LIGHT_PRIMARY_NAVY = new Color(35, 92, 132);
    private static final Color BACKGROUND_CANVAS = new Color(244, 248, 252);
    private static final Color BASE_DARK_TEXT = new Color(33, 37, 41);
    private static final Color BASE_MUTED_TEXT = new Color(108, 117, 125);
    private static final Color LOGOUT_RED_COLOR = new Color(214, 80, 80);

    // Business Services
    private final transient VehicleService fleetManagementService = new VehicleService(new VehicleRepository());

    // Dynamic Display Counters
    private JLabel activeFleetCountLbl;
    private JLabel rentedFleetCountLbl;
    private JLabel systemStatusStateLbl;

    // Navigation Triggers
    private JButton navVehiclesBtn;
    private JButton navRentBtn;
    private JButton navReturnBtn;
    private JButton navBillingBtn;
    private JButton navTypesBtn;
    private JButton navReminderBtn;
    private JButton executeLogoutBtn;

    public DashboardFrame() {
        configureWindowProperties();
        buildDashboardContent();
        registerInteractionHandlers();
        updateSystemOverviewMetrics();
        setVisible(true);
    }

    private void configureWindowProperties() {
        setTitle("SB Car Rental Dashboard");
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buildDashboardContent() {
        JPanel containerCanvas = new JPanel(new BorderLayout());
        containerCanvas.setBackground(BACKGROUND_CANVAS);

        containerCanvas.add(buildHeaderBanner(), BorderLayout.NORTH);
        containerCanvas.add(buildCentralWorkspace(), BorderLayout.CENTER);
        containerCanvas.add(buildFooterNotice(), BorderLayout.SOUTH);

        setContentPane(containerCanvas);
    }

    private JPanel buildHeaderBanner() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, DEEP_PRIMARY_NAVY, getWidth(), getHeight(), LIGHT_PRIMARY_NAVY));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(new EmptyBorder(24, 38, 24, 38));

        JPanel brandingStack = new JPanel();
        brandingStack.setOpaque(false);
        brandingStack.setLayout(new BoxLayout(brandingStack, BoxLayout.Y_AXIS));

        JLabel brandHeading = createLabelElement(BRAND_TITLE, Font.BOLD, 34, Color.WHITE);
        JLabel brandSubHeading = createLabelElement("Vehicle Rental Management System", Font.PLAIN, 15, new Color(220, 235, 245));

        brandingStack.add(brandHeading);
        brandingStack.add(Box.createVerticalStrut(5));
        brandingStack.add(brandSubHeading);

        JPanel accountControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        accountControls.setOpaque(false);

        JLabel userRoleTag = createLabelElement("Administrator", Font.BOLD, 17, Color.WHITE);
        userRoleTag.setAlignmentX(Component.CENTER_ALIGNMENT);

        executeLogoutBtn = createLogoutButton("Logout");

        accountControls.add(userRoleTag);
        accountControls.add(executeLogoutBtn);

        headerPanel.add(brandingStack, BorderLayout.WEST);
        headerPanel.add(accountControls, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel buildCentralWorkspace() {
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(BACKGROUND_CANVAS);
        mainArea.setBorder(new EmptyBorder(30, 42, 28, 42));

        JPanel heroSection = new JPanel();
        heroSection.setOpaque(false);
        heroSection.setLayout(new BoxLayout(heroSection, BoxLayout.Y_AXIS));

        JLabel welcomeText = createLabelElement("Welcome to SB Car Rental Dashboard", Font.BOLD, 30, BASE_DARK_TEXT);
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleText = createLabelElement("Manage vehicles, rentals, billing, reminders, and vehicle types.", Font.PLAIN, 16, BASE_MUTED_TEXT);
        subtitleText.setAlignmentX(Component.CENTER_ALIGNMENT);

        heroSection.add(welcomeText);
        heroSection.add(Box.createVerticalStrut(8));
        heroSection.add(subtitleText);
        heroSection.add(Box.createVerticalStrut(28));

        mainArea.add(heroSection, BorderLayout.NORTH);
        mainArea.add(buildFeatureGrid(), BorderLayout.CENTER);
        mainArea.add(buildSystemStatusPanel(), BorderLayout.SOUTH);

        return mainArea;
    }

    private JPanel buildFeatureGrid() {
        JPanel gridLayout = new JPanel(new GridLayout(2, 3, 22, 22));
        gridLayout.setOpaque(false);

        navVehiclesBtn = createNavigationTile("View Vehicles", "Browse all vehicles and availability", new Color(65, 130, 190));
        navRentBtn = createNavigationTile("Rent Vehicle", "Create a new rental transaction", new Color(78, 150, 120));
        navReturnBtn = createNavigationTile("Return Vehicle", "Return rented vehicles safely", new Color(210, 145, 80));
        navBillingBtn = createNavigationTile("Billing", "Calculate cost and late penalty", new Color(125, 105, 170));
        navTypesBtn = createNavigationTile("Vehicle Types", "View vehicles by category", new Color(60, 145, 145));
        navReminderBtn = createNavigationTile("Rental Reminder", "Send rental reminder messages", new Color(190, 90, 90));

        gridLayout.add(navVehiclesBtn);
        gridLayout.add(navRentBtn);
        gridLayout.add(navReturnBtn);
        gridLayout.add(navBillingBtn);
        gridLayout.add(navTypesBtn);
        gridLayout.add(navReminderBtn);

        return gridLayout;
    }

    private JPanel buildSystemStatusPanel() {
        JPanel summaryCard = new JPanel(new GridLayout(1, 3, 20, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 24, 24);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        summaryCard.setOpaque(false);
        summaryCard.setBorder(new EmptyBorder(20, 24, 20, 24));
        summaryCard.setPreferredSize(new Dimension(1000, 115));

        activeFleetCountLbl = createLabelElement("0", Font.BOLD, 28, new Color(65, 130, 190));
        activeFleetCountLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        rentedFleetCountLbl = createLabelElement("--", Font.BOLD, 28, new Color(210, 145, 80));
        rentedFleetCountLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        systemStatusStateLbl = createLabelElement("Online", Font.BOLD, 28, new Color(78, 150, 120));
        systemStatusStateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        summaryCard.add(createMetricDisplayCell("Available Vehicles", activeFleetCountLbl));
        summaryCard.add(createMetricDisplayCell("Rented Vehicles", rentedFleetCountLbl));
        summaryCard.add(createMetricDisplayCell("System Status", systemStatusStateLbl));

        return summaryCard;
    }

    private JPanel createMetricDisplayCell(String caption, JLabel valDisplay) {
        JPanel cell = new JPanel();
        cell.setOpaque(false);
        cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));

        JLabel title = createLabelElement(caption, Font.PLAIN, 15, BASE_MUTED_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        cell.add(Box.createVerticalGlue());
        cell.add(title);
        cell.add(Box.createVerticalStrut(8));
        cell.add(valDisplay);
        cell.add(Box.createVerticalGlue());

        return cell;
    }

    private JPanel buildFooterNotice() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(248, 249, 250));
        footer.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel copyrightText = createLabelElement("© 2026 SB Car Rental - Vehicle Rental Management System", Font.PLAIN, 13, BASE_MUTED_TEXT);
        copyrightText.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.add(copyrightText);

        return footer;
    }

    // --- Component Generators ---

    private JLabel createLabelElement(String text, int style, int size, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font(MAIN_FONT_FAMILY, style, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton createNavigationTile(String headerText, String bodyText, Color themeAccent) {
        JButton tileBtn = new JButton() {
            private boolean isHovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int x = 4;
                int y = 4;
                int w = getWidth() - 10;
                int h = getHeight() - 10;

                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(x + 4, y + 5, w, h, 24, 24);

                g2.setColor(isHovered ? new Color(250, 252, 255) : Color.WHITE);
                g2.fillRoundRect(x, y, w, h, 24, 24);

                g2.setColor(new Color(220, 230, 240));
                g2.drawRoundRect(x, y, w, h, 24, 24);

                g2.setColor(themeAccent);
                g2.fillRoundRect(x, y, w, 8, 24, 24);
                g2.fillRect(x, y + 5, w, 5);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        tileBtn.setText(String.format("<html><center><span style='font-size:20px;'><b>%s</b></span><br><br><span style='font-size:12px; color:#6c757d;'>%s</span></center></html>", headerText, bodyText));
        tileBtn.setFont(new Font(MAIN_FONT_FAMILY, Font.PLAIN, 16));
        tileBtn.setForeground(BASE_DARK_TEXT);
        tileBtn.setFocusPainted(false);
        tileBtn.setOpaque(false);
        tileBtn.setContentAreaFilled(false);
        tileBtn.setBorderPainted(false);
        tileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tileBtn.setBorder(new EmptyBorder(20, 20, 20, 20));

        return tileBtn;
    }

    private JButton createLogoutButton(String label) {
        JButton btn = new JButton(label);
        btn.setPreferredSize(new Dimension(115, 42));
        btn.setBackground(LOGOUT_RED_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font(MAIN_FONT_FAMILY, Font.BOLD, 15));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(LOGOUT_RED_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(LOGOUT_RED_COLOR);
            }
        });

        return btn;
    }

    // --- State & Event Management ---

    private void updateSystemOverviewMetrics() {
        int count = fleetManagementService.getAvailableVehicles().size();
        activeFleetCountLbl.setText(String.valueOf(count));
        rentedFleetCountLbl.setText("--");
        systemStatusStateLbl.setText("Online");
    }

    private void registerInteractionHandlers() {
        executeLogoutBtn.addActionListener(e -> {
            int userChoice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (userChoice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        navVehiclesBtn.addActionListener(e -> navigateToScreen(new ViewVehiclesFrame()));
        navRentBtn.addActionListener(e -> navigateToScreen(new RentVehicleFrame()));
        navReturnBtn.addActionListener(e -> navigateToScreen(new ReturnVehicleFrame()));
        navBillingBtn.addActionListener(e -> navigateToScreen(new BillingFrame()));
        navTypesBtn.addActionListener(e -> navigateToScreen(new VehicleTypesFrame()));
        navReminderBtn.addActionListener(e -> navigateToScreen(new RentalReminderFrame()));
    }

    private void navigateToScreen(JFrame frame) {
        dispose();
        frame.setVisible(true);
    }
}