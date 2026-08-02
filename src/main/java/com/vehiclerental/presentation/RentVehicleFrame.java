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
import java.util.List;

/**
 * RentVehicleFrame manages the GUI and interaction logic for renting vehicles.
 */
public class RentVehicleFrame extends JFrame {

    // Style Configuration Constants
    private static final String INTERFACE_FONT = "Segoe UI";
    private static final String SYSTEM_FOOTER_LABEL = "SB Car Rental Management System";

    private static final Color BANNER_TOP_DARK = new Color(18, 54, 82);
    private static final Color BANNER_TOP_LIGHT = new Color(35, 92, 132);
    private static final Color SURFACE_BG = new Color(244, 248, 252);
    private static final Color MAIN_TEXT_COLOR = new Color(33, 37, 41);
    private static final Color SUB_TEXT_COLOR = new Color(108, 117, 125);
    private static final Color STATUS_GREEN_COLOR = new Color(78, 150, 120);
    private static final Color INTERACTIVE_BLUE = new Color(65, 130, 190);
    private static final Color ERROR_RED_COLOR = new Color(214, 80, 80);

    // Interactive UI Controls
    private JComboBox<Vehicle> availableFleetCombo;
    private JTextField durationDaysField;
    private JButton confirmCheckoutBtn;
    private JButton clearFieldsBtn;
    private JButton exitFrameBtn;
    private JLabel statusFeedbackLabel;
    private JLabel selectedVehicleDetailsLabel;

    // Operational Services
    private final VehicleService fleetDataService = new VehicleService(new VehicleRepository());
    private final RentalService checkoutProcessingService = new RentalService(new EmailNotificationService());

    public RentVehicleFrame() {
        initWindowFrameProperties();
        renderMainLayoutStructure();
        connectComponentListeners();
        populateAvailableVehiclesList();
        setVisible(true);
    }

    private void initWindowFrameProperties() {
        setTitle("Rent Vehicle");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void renderMainLayoutStructure() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(SURFACE_BG);

        rootPanel.add(assembleHeaderView(), BorderLayout.NORTH);
        rootPanel.add(assembleCenterWorkspaceView(), BorderLayout.CENTER);
        rootPanel.add(assembleFooterView(), BorderLayout.SOUTH);

        setContentPane(rootPanel);
    }

    private JPanel assembleHeaderView() {
        JPanel headerContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, BANNER_TOP_DARK, getWidth(), getHeight(), BANNER_TOP_LIGHT));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerContainer.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel headlineWrapper = new JPanel();
        headlineWrapper.setOpaque(false);
        headlineWrapper.setLayout(new BoxLayout(headlineWrapper, BoxLayout.Y_AXIS));

        JLabel titleLbl = makeTextLabel("Rent Vehicle", Font.BOLD, 32, Color.WHITE);
        JLabel subtitleLbl = makeTextLabel("Create a new rental transaction in a simple and organized way", Font.PLAIN, 15, new Color(220, 235, 245));

        headlineWrapper.add(titleLbl);
        headlineWrapper.add(Box.createVerticalStrut(5));
        headlineWrapper.add(subtitleLbl);

        exitFrameBtn = makeStyledButton("Back", ERROR_RED_COLOR, 110, 42);

        headerContainer.add(headlineWrapper, BorderLayout.WEST);
        headerContainer.add(exitFrameBtn, BorderLayout.EAST);

        return headerContainer;
    }

    private JPanel assembleCenterWorkspaceView() {
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(SURFACE_BG);
        centerContainer.setBorder(new EmptyBorder(32, 42, 32, 42));

        JPanel contentCard = new JPanel(new BorderLayout()) {
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
        contentCard.setOpaque(false);
        contentCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentCard.setPreferredSize(new Dimension(820, 410));

        JPanel gridColumns = new JPanel(new GridLayout(1, 2, 28, 0));
        gridColumns.setOpaque(false);

        gridColumns.add(assembleInfoBox());
        gridColumns.add(assembleFormBox());

        contentCard.add(gridColumns, BorderLayout.CENTER);
        centerContainer.add(contentCard);

        return centerContainer;
    }

    private JPanel assembleInfoBox() {
        JPanel infoPanel = new JPanel() {
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
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(30, 28, 30, 28));

        infoPanel.add(makeTextLabel("Rental Summary", Font.BOLD, 24, BANNER_TOP_DARK));
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(makeTextLabel("Select a vehicle and enter valid rental days.", Font.PLAIN, 14, SUB_TEXT_COLOR));
        infoPanel.add(Box.createVerticalStrut(28));

        selectedVehicleDetailsLabel = makeTextLabel("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>", Font.PLAIN, 15, MAIN_TEXT_COLOR);
        infoPanel.add(selectedVehicleDetailsLabel);
        infoPanel.add(Box.createVerticalStrut(28));

        String bulletPoints = "<html><body style='width: 280px; color: #212529; font-family: Segoe UI; font-size: 11px;'>"
                + "<p>• Rental days must be greater than zero.</p><br>"
                + "<p>• Only available vehicles can be rented.</p><br>"
                + "<p>• After renting, the vehicle is removed from the list.</p>"
                + "</body></html>";
        infoPanel.add(new JLabel(bulletPoints));

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(makeTextLabel("SB Car Rental", Font.BOLD, 15, BANNER_TOP_DARK));

        return infoPanel;
    }

    private JPanel assembleFormBox() {
        JPanel formStack = new JPanel();
        formStack.setOpaque(false);
        formStack.setLayout(new BoxLayout(formStack, BoxLayout.Y_AXIS));

        formStack.add(makeTextLabel("Rental Details", Font.BOLD, 24, MAIN_TEXT_COLOR));
        formStack.add(Box.createVerticalStrut(8));
        formStack.add(makeTextLabel("Fill the information below to rent a vehicle.", Font.PLAIN, 14, SUB_TEXT_COLOR));
        formStack.add(Box.createVerticalStrut(28));

        formStack.add(makeTextLabel("Select Vehicle", Font.BOLD, 15, MAIN_TEXT_COLOR));
        formStack.add(Box.createVerticalStrut(8));

        availableFleetCombo = new JComboBox<>();
        availableFleetCombo.setMaximumSize(new Dimension(360, 44));
        availableFleetCombo.setFont(new Font(INTERFACE_FONT, Font.PLAIN, 15));
        availableFleetCombo.setBackground(Color.WHITE);
        availableFleetCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        availableFleetCombo.setRenderer((list, item, index, isSelected, cellHasFocus) -> {
            DefaultListCellRenderer renderer = new DefaultListCellRenderer();
            Component c = renderer.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
            if (item instanceof Vehicle) {
                Vehicle v = (Vehicle) item;
                String display = String.format("%s - %s %s (%s)", v.getId(), v.getBrand(), v.getModel(), v.getClass().getSimpleName());
                ((JLabel) c).setText(display);
            }
            c.setFont(new Font(INTERFACE_FONT, Font.PLAIN, 15));
            return c;
        });

        formStack.add(availableFleetCombo);
        formStack.add(Box.createVerticalStrut(22));

        formStack.add(makeTextLabel("Rental Days", Font.BOLD, 15, MAIN_TEXT_COLOR));
        formStack.add(Box.createVerticalStrut(8));

        durationDaysField = new JTextField();
        durationDaysField.setMaximumSize(new Dimension(360, 44));
        durationDaysField.setFont(new Font(INTERFACE_FONT, Font.PLAIN, 15));
        durationDaysField.setForeground(MAIN_TEXT_COLOR);
        durationDaysField.setCaretColor(INTERACTIVE_BLUE);
        durationDaysField.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 210, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        durationDaysField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formStack.add(durationDaysField);
        formStack.add(Box.createVerticalStrut(18));

        statusFeedbackLabel = makeTextLabel(" ", Font.BOLD, 14, ERROR_RED_COLOR);
        formStack.add(statusFeedbackLabel);
        formStack.add(Box.createVerticalStrut(16));

        JPanel actionsRow = new JPanel(new GridLayout(1, 2, 14, 0));
        actionsRow.setOpaque(false);
        actionsRow.setMaximumSize(new Dimension(360, 44));
        actionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        confirmCheckoutBtn = makeStyledButton("Rent Vehicle", STATUS_GREEN_COLOR, 160, 44);
        clearFieldsBtn = makeStyledButton("Clear", INTERACTIVE_BLUE, 160, 44);

        actionsRow.add(confirmCheckoutBtn);
        actionsRow.add(clearFieldsBtn);

        formStack.add(actionsRow);

        return formStack;
    }

    private JPanel assembleFooterView() {
        JPanel footerBar = new JPanel();
        footerBar.setBackground(new Color(248, 249, 250));
        footerBar.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel footerText = makeTextLabel(SYSTEM_FOOTER_LABEL, Font.PLAIN, 13, SUB_TEXT_COLOR);
        footerText.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerBar.add(footerText);

        return footerBar;
    }

    // --- Component Helpers ---

    private JLabel makeTextLabel(String textContent, int fontStyle, int fontSize, Color color) {
        JLabel lbl = new JLabel(textContent);
        lbl.setFont(new Font(INTERFACE_FONT, fontStyle, fontSize));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton makeStyledButton(String text, Color baseColor, int w, int h) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font(INTERFACE_FONT, Font.BOLD, 15));
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

    private void populateAvailableVehiclesList() {
        availableFleetCombo.removeAllItems();
        List<Vehicle> availableVehicles = fleetDataService.getAvailableVehicles();
        for (Vehicle v : availableVehicles) {
            availableFleetCombo.addItem(v);
        }

        refreshSelectedVehicleInfo();

        if (availableFleetCombo.getItemCount() == 0) {
            updateStatusAlert("No available vehicles found.", false);
        }
    }

    private void connectComponentListeners() {
        confirmCheckoutBtn.addActionListener(e -> executeRentalTransaction());
        clearFieldsBtn.addActionListener(e -> resetFormInputs());
        exitFrameBtn.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        availableFleetCombo.addActionListener(e -> refreshSelectedVehicleInfo());
    }

    private void refreshSelectedVehicleInfo() {
        Vehicle target = (Vehicle) availableFleetCombo.getSelectedItem();
        if (target == null) {
            selectedVehicleDetailsLabel.setText("<html><b>Selected Vehicle:</b><br>No vehicle selected yet</html>");
        } else {
            selectedVehicleDetailsLabel.setText(String.format("<html><b>Selected Vehicle:</b><br>%s - %s %s</html>",
                    target.getId(), target.getBrand(), target.getModel()));
        }
    }

    private void executeRentalTransaction() {
        Vehicle targetVehicle = (Vehicle) availableFleetCombo.getSelectedItem();
        if (targetVehicle == null) {
            updateStatusAlert("Please select a vehicle.", false);
            return;
        }

        String inputDays = durationDaysField.getText().trim();
        if (inputDays.isEmpty()) {
            updateStatusAlert("Please enter rental days.", false);
            return;
        }

        try {
            int duration = Integer.parseInt(inputDays);
            if (duration <= 0) {
                updateStatusAlert("Rental days must be greater than zero.", false);
                return;
            }

            if (checkoutProcessingService.rentVehicle(targetVehicle, duration)) {
                updateStatusAlert("Vehicle rented successfully.", true);
                durationDaysField.setText("");
                populateAvailableVehiclesList();
            } else {
                updateStatusAlert("Vehicle cannot be rented.", false);
            }
        } catch (NumberFormatException ex) {
            updateStatusAlert("Rental days must be a valid number.", false);
        }
    }

    private void resetFormInputs() {
        if (availableFleetCombo.getItemCount() > 0) {
            availableFleetCombo.setSelectedIndex(0);
        }
        durationDaysField.setText("");
        statusFeedbackLabel.setText(" ");
        refreshSelectedVehicleInfo();
    }

    private void updateStatusAlert(String message, boolean isSuccess) {
        statusFeedbackLabel.setForeground(isSuccess ? STATUS_GREEN_COLOR : ERROR_RED_COLOR);
        statusFeedbackLabel.setText(message);
    }
}